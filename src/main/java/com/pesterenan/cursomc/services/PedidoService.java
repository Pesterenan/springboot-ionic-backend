package com.pesterenan.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Pedido;
import com.pesterenan.cursomc.repositories.PedidoRepository;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRepo;
	
	public Pedido buscar(Long id) throws ObjectNotFoundException {
		Optional<Pedido> obj = pedRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Pedido.class.getName()));
	}
}
