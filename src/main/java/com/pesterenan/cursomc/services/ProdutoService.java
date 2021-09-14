package com.pesterenan.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Categoria;
import com.pesterenan.cursomc.domain.Produto;
import com.pesterenan.cursomc.repositories.CategoriaRepository;
import com.pesterenan.cursomc.repositories.ProdutoRepository;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository pedRepo;
	
	@Autowired
	private CategoriaRepository catRepo;
	
	public Produto find(Long id) throws ObjectNotFoundException {
		Optional<Produto> obj = pedRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String name, List<Long> ids, Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageReq = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catRepo.findAllById(ids);
		return pedRepo.search(name, categorias, pageReq);
	}
}
