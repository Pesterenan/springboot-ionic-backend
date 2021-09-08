package com.pesterenan.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesterenan.cursomc.domain.Cliente;
import com.pesterenan.cursomc.services.ClienteService;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService cliService;

	@GetMapping(value= "/{id}")
	public ResponseEntity<?> listar(@PathVariable Long id) throws ObjectNotFoundException {
		Cliente obj = cliService.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
}
