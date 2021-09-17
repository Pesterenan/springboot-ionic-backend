package com.pesterenan.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pesterenan.cursomc.domain.Categoria;
import com.pesterenan.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	// A anotação Query sobrepõe o nome do método que a implementa automaticamente se não
	// estiver comentada:
	// @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat
	// WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	// Page<Produto> search(@Param("nome") String nome, @Param("categorias")
	// List<Categoria> categorias, Pageable pageReq);
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriaIn(String nome, List<Categoria> categoria,
			Pageable pageReq);

}
