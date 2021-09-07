package com.pesterenan.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Categoria;
import com.pesterenan.cursomc.repositories.CategoriaRepository;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRepo;
	
	public Categoria buscar(Long id) throws ObjectNotFoundException {
		Optional<Categoria> obj = catRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Categoria.class.getName()));
	}
}
