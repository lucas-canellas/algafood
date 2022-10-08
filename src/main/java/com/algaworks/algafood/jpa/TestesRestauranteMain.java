package com.algaworks.algafood.jpa;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class TestesRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante1 =  new Restaurante();
		restaurante1.setNome("Bobs");
		restaurante1.setTaxaFrete(new BigDecimal(10));
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Habibs");
		restaurante2.setTaxaFrete(new BigDecimal(20));
		
		restaurante1 = restaurantes.adicionar(restaurante1);
		restaurantes.adicionar(restaurante2);
		
		restaurante1.setNome("Vasco");
		restaurantes.adicionar(restaurante1);
		
		var testeBusca = restaurantes.buscar(1L);
		System.out.println(testeBusca.getNome());
		
		List<Restaurante> todosRestaurantes = restaurantes.listar(); 
		
		for(Restaurante restaurante : todosRestaurantes) {
			System.out.println(restaurante.getNome());
		}
		
		restaurantes.remover(restaurante1);		
		
	}

}
