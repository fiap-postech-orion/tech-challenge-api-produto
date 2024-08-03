package br.com.postech.software.architecture.techchallenge.produto.producer;

import br.com.postech.software.architecture.techchallenge.produto.dto.PedidoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class RabbitMQProducer {

    private static final String exchange = "";

    private static final String validaClienteRoutingKey = "callbackValidacao";

    private RabbitTemplate template;

    public void sendToCallbackValidacaoQueue(PedidoDTO pedidoDTO) {
        log.info("Message send: [{}]", pedidoDTO.toString());
        template.convertAndSend(exchange, validaClienteRoutingKey, pedidoDTO);
    }
}
