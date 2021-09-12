package com.pesterenan.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pesterenan.cursomc.domain.Cliente;
import com.pesterenan.cursomc.dto.ClienteDTO;
import com.pesterenan.cursomc.repositories.ClienteRepository;
import com.pesterenan.cursomc.services.exceptions.DataIntegrityException;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepo;

	public Cliente find(Long id) throws ObjectNotFoundException {
		Optional<Cliente> obj = cliRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + " Tipo: " + Cliente.class.getName()));
	}

	// Cria um novo objetoCliente e atualiza somente os dados necessários pra depois salvar no banco de dados.
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj);
		return cliRepo.save(newObj);
	}

	// Atualiza dados de nome e email do novo objeto cliente para poder manter outros dados imutáveis.
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void deleteById(Long id) {
		find(id);
		try {
			cliRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um porque há entidades relacionadas.");
		}
	}

	public List<Cliente> findAll() {
		List<Cliente> list = cliRepo.findAll();
		return list;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageReq = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cliRepo.findAll(pageReq);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
}
