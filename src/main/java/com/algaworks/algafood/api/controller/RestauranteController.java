package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteModelAssembler assembler;
	
	@Autowired
	private RestauranteInputDisassembler disassembler;
	
	@GetMapping
	public List<RestauranteModel> listar() {
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		return assembler.toCollectionModel(restaurantes);
	}

	@GetMapping(value = "/{idRestaurante}")
	public RestauranteModel buscar(@PathVariable Long idRestaurante) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(idRestaurante);
		
		return assembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {		
		try {
			return assembler.toModel(cadastroRestaurante.salvar(disassembler.toDomainObject(restauranteInput)));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}	
	}

	@PutMapping(value = "/{idRestaurante}")
	public RestauranteModel atualizar(@PathVariable Long idRestaurante, @RequestBody @Valid RestauranteInput restauranteInput) {
		
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(idRestaurante);
		
		disassembler.copyToDomainObject(restauranteInput, restauranteAtual);

		
		try {
			return assembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}	
	}
	


}
