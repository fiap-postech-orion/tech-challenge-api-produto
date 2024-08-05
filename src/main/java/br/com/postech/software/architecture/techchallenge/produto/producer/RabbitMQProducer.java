package br.com.postech.software.architecture.techchallenge.produto.producer;

import br.com.postech.software.architecture.techchallenge.produto.dto.ErroResponseDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.PedidoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class RabbitMQProducer {

    private static final String exchange = "api";

    private static final String callbackValidacaoRoutingKey = "callbackValidacao";

    private static final String erroValidacaoRoutingKey = "erroValidacao";

    private RabbitTemplate template;

    public void sendToCallbackValidacaoQueue(PedidoDTO pedidoDTO) {
        log.info("Message send: [{}]", pedidoDTO.toString());
        template.convertAndSend(exchange, callbackValidacaoRoutingKey, pedidoDTO);
    }

    public void sendToErroValidacaoQueue(ValidaProdutoResponseDTO validaProdutoResponseDTO, Integer numeroPedido) {
        log.info("Error message: [{}]", validaProdutoResponseDTO.getErrorMessage());
        template.convertAndSend(exchange, erroValidacaoRoutingKey,
                new ErroResponseDTO(numeroPedido, false, validaProdutoResponseDTO.getErrorMessage()));
    }
}