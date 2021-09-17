package com.pesterenan.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = pedService.insert(obj);
		// Boa prática de REST: Retornar URI do objeto criado após inserção
		// Pega o URI do request atual, e adiciona a nova id do novo objeto já no banco
		// de dados.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
