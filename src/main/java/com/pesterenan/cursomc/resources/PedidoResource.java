package com.pesterenan.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesterenan.cursomc.domain.Pedido;
import com.pesterenan.cursomc.services.PedidoService;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedService;

	@GetMapping(value= "/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Long id) throws ObjectNotFoundException {
		Pedido obj = pedService.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
}
