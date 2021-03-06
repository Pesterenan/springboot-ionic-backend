package com.pesterenan.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Categoria;
import com.pesterenan.cursomc.domain.Cidade;
import com.pesterenan.cursomc.domain.Cliente;
import com.pesterenan.cursomc.domain.Endereco;
import com.pesterenan.cursomc.domain.Estado;
import com.pesterenan.cursomc.domain.ItemPedido;
import com.pesterenan.cursomc.domain.Pagamento;
import com.pesterenan.cursomc.domain.PagamentoComBoleto;
import com.pesterenan.cursomc.domain.PagamentoComCartao;
import com.pesterenan.cursomc.domain.Pedido;
import com.pesterenan.cursomc.domain.Produto;
import com.pesterenan.cursomc.domain.enums.EstadoPagamento;
import com.pesterenan.cursomc.domain.enums.Perfil;
import com.pesterenan.cursomc.domain.enums.TipoCliente;
import com.pesterenan.cursomc.repositories.CategoriaRepository;
import com.pesterenan.cursomc.repositories.CidadeRepository;
import com.pesterenan.cursomc.repositories.ClienteRepository;
import com.pesterenan.cursomc.repositories.EnderecoRepository;
import com.pesterenan.cursomc.repositories.EstadoRepository;
import com.pesterenan.cursomc.repositories.ItemPedidoRepository;
import com.pesterenan.cursomc.repositories.PagamentoRepository;
import com.pesterenan.cursomc.repositories.PedidoRepository;
import com.pesterenan.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository catRepo;
	@Autowired
	private ProdutoRepository prodRepo;
	@Autowired
	private EstadoRepository estRepo;
	@Autowired
	private CidadeRepository cidRepo;
	@Autowired
	private ClienteRepository cliRepo;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private PedidoRepository pedRepo;
	@Autowired
	private PagamentoRepository pagRepo;
	@Autowired
	private ItemPedidoRepository ipRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instantiateTestDatabase() throws ParseException {

		// Categorias e Produtos
		Categoria cat1 = new Categoria(null, "Inform??tica");
		Categoria cat2 = new Categoria(null, "Escrit??rio");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletr??nicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decora????o");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		Produto p1 = new Produto(null, "Computador", 2000);
		Produto p2 = new Produto(null, "Impressora", 800);
		Produto p3 = new Produto(null, "Mouse", 80);
		Produto p4 = new Produto(null, "Mesa de escrit??rio", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV True color", 1200.00);
		Produto p8 = new Produto(null, "Ro??adeira", 800.00);
		Produto p9 = new Produto(null, "Abajur", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategoria().addAll(Arrays.asList(cat1, cat4));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategoria().addAll(Arrays.asList(cat1, cat4));
		p4.getCategoria().addAll(Arrays.asList(cat2));
		p5.getCategoria().addAll(Arrays.asList(cat3));
		p6.getCategoria().addAll(Arrays.asList(cat3));
		p7.getCategoria().addAll(Arrays.asList(cat4));
		p8.getCategoria().addAll(Arrays.asList(cat5));
		p9.getCategoria().addAll(Arrays.asList(cat6));
		p10.getCategoria().addAll(Arrays.asList(cat6));
		p11.getCategoria().addAll(Arrays.asList(cat7));

		catRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		prodRepo.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		// Cidades e Estados
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "S??o Paulo");

		Cidade c1 = new Cidade(null, "Uberl??ndia", est1);
		Cidade c2 = new Cidade(null, "S??o Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estRepo.saveAll(Arrays.asList(est1, est2));
		cidRepo.saveAll(Arrays.asList(c1, c2, c3));

		// Cliente e Endere??os

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Cliente cli2 = new Cliente(null, "Ana Costa", "pesterenan@gmail.com", "31628382740", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("49866578", "94654788"));
		cli2.addPerfil(Perfil.ADMIN) ;

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco(null, "Avenida Floriano", "2106", null, "Centro", "281777012", cli2, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));

		cliRepo.saveAll(Arrays.asList(cli1, cli2));
		endRepo.saveAll(Arrays.asList(e1, e2, e3));

		// Pedidos
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, null,
				sdf.parse("20/10/2017 00:00"));
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedRepo.saveAll(Arrays.asList(ped1, ped2));
		pagRepo.saveAll(Arrays.asList(pagto1, pagto2));

		// Itens e Pedidos

		ItemPedido ip1 = new ItemPedido(0.0, 1, 2000.0, ped1, p1);
		ItemPedido ip2 = new ItemPedido(0.0, 2, 80.0, ped1, p3);
		ItemPedido ip3 = new ItemPedido(100.0, 1, 800.0, ped2, p2);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().add(ip3);

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		ipRepo.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
