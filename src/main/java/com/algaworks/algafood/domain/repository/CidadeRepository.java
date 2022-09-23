package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cidade;

public interface CidadeRepository {
	
	List<Cidade> listar();
	Cidade porId(Long id);
	Cidade salvar(Cidade cidade);
	void remover(Cidade cidade);
	
}
