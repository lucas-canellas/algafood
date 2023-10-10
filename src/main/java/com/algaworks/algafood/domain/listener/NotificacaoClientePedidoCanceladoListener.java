package com.algaworks.algafood.domain.listener;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoCanceladoEvent event) {

        Pedido pedido = event.getPedido();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado")
                .corpo("pedido-cancelado.html")
                .variaveis(Map.of("pedido", pedido))
                .destinatarios(Set.of(pedido.getCliente().getEmail(), "lucasdcaws@gmail.com"))
                .build();

        envioEmail.enviar(mensagem);
    }

}
