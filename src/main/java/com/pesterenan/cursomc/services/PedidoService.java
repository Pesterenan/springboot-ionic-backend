package com.pesterenan.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pesterenan.cursomc.domain.ItemPedido;
import com.pesterenan.cursomc.domain.PagamentoComBoleto;
import com.pesterenan.cursomc.domain.Pedido;
import com.pesterenan.cursomc.domain.enums.EstadoPagamento;
import com.pesterenan.cursomc.repositories.ItemPedidoRepository;
import com.pesterenan.cursomc.repositories.PagamentoRepository;
import com.pesterenan.cursomc.repositories.PedidoRepository;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedRepo;
	
	@Autowired
	private ProdutoService prodService;
	
	@Autowired
	private PagamentoRepository pagRepo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteService cliService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Long id) throws ObjectNotFoundException {
		Optional<Pedido> obj = pedRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional(readOnly=true)
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(cliService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) { 
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedRepo.save(obj);
		pagRepo.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens() ) {
			ip.setDesconto(0.0);
			ip.setProduto(prodService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedRepo.saveAllAndFlush(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
		
	}
}
