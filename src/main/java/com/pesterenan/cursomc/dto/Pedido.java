package com.pesterenan.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.pesterenan.cursomc.domain.Categoria;

public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres.")
	private String nome;

	public Pedido() {
	}

	public Pedido(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
