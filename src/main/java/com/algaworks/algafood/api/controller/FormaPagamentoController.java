package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/forma-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamento;
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler disassembler;
	
	@GetMapping
	public List<FormaPagamentoModel> list() {
		return assembler.toCollectionModel(formaPagamento.findAll());
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId) {
		return assembler.toModel(formaPagamentoService.buscarOuFalhar(formaPagamentoId));
	}
	
	@PostMapping
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput ) {
		FormaPagamento formaPagamento = disassembler.toDomainObject(formaPagamentoInput);		
		return assembler.toModel(formaPagamentoService.salvar(formaPagamento));
	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput ) {
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);		
		disassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);		
		return assembler.toModel(formaPagamentoService.salvar(formaPagamento));
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public void excluir(@PathVariable Long formaPagamentoId) {
		formaPagamentoService.deletar(formaPagamentoId);
	}
	
	
	
}
