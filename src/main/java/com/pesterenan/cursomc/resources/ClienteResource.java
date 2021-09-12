package com.pesterenan.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pesterenan.cursomc.domain.Categoria;
import com.pesterenan.cursomc.domain.Cliente;
import com.pesterenan.cursomc.dto.ClienteDTO;
import com.pesterenan.cursomc.dto.ClienteNewDTO;
import com.pesterenan.cursomc.services.ClienteService;
import com.pesterenan.cursomc.services.exceptions.DataIntegrityException;
import com.pesterenan.cursomc.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService cliService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> listar(@PathVariable Long id) throws ObjectNotFoundException {
		Cliente obj = cliService.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = cliService.fromDTO(objDto);
		obj = cliService.insert(obj);
		// Boa prática de REST: Retornar URI do objeto criado após inserção
		// Pega o URI do request atual, e adiciona a nova id do novo objeto já no banco
		// de dados.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Long id) {
		Cliente obj = cliService.fromDTO(objDto);
		obj.setId(id);
		obj = cliService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws DataIntegrityException {
		cliService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> findAll() throws ObjectNotFoundException {
		List<Cliente> list = cliService.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	// Requisição de objetos Cliente por Página, para evitar trazer todos os
	// registros de uma vez só.
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) throws ObjectNotFoundException {
		Page<Cliente> list = cliService.findPage(page, linesPerPage, direction, orderBy);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
