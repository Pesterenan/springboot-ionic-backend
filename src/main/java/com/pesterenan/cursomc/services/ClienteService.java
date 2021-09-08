package com.pesterenan.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Cliente;
import com.pesterenan.cursomc.repositories.ClienteRepository;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepo;
	
	public Cliente buscar(Long id) throws ObjectNotFoundException {
		Optional<Cliente> obj = cliRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Cliente.class.getName()));
	}
}
