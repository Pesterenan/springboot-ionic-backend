package com.pesterenan.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pesterenan.cursomc.domain.Produto;
import com.pesterenan.cursomc.dto.ProdutoDTO;
import com.pesterenan.cursomc.resources.utils.URL;
import com.pesterenan.cursomc.services.ProdutoService;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService prodService;

	@GetMapping(value= "/{id}")
	public ResponseEntity<Produto> find(@PathVariable Long id) throws ObjectNotFoundException {
		Produto obj = prodService.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue="ASC") String direction, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy) throws ObjectNotFoundException {
		Page<Produto> list = prodService.search(nome, URL.decodeLongList(categorias), page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
}
