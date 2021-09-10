package com.pesterenan.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Categoria;
import com.pesterenan.cursomc.repositories.CategoriaRepository;
import com.pesterenan.cursomc.services.exceptions.DataIntegrityException;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRepo;
	
	public Categoria find(Long id) throws ObjectNotFoundException {
		Optional<Categoria> obj = catRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return catRepo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return catRepo.save(obj);
	}

	public void deleteById(Long id) {
		find(id);
		try {
		catRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma cateogria que possui produtos.");
		}
	}

	public List<Categoria> findAll() {
		List<Categoria> list = catRepo.findAll();
		return list;
	}
}
