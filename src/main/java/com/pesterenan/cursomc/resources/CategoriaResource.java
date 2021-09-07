package com.pesterenan.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesterenan.cursomc.domain.Categoria;
import com.pesterenan.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService catService;

	@GetMapping(value= "/{id}")
	public ResponseEntity<?> listar(@PathVariable Long id) {
		Categoria obj = catService.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	
}
