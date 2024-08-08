package br.com.postech.software.architecture.techchallenge.produto.consumer;

import br.com.postech.software.architecture.techchallenge.produto.dto.PedidoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ProdutoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoRequestDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoResponseDTO;
import br.com.postech.software.architecture.techchallenge.produto.producer.RabbitMQProducer;
import br.com.postech.software.architecture.techchallenge.produto.service.ProdutoService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ValidaProdutoConsumer {

    private ProdutoService produtoService;
    private RabbitMQProducer rabbitMQProducer;

    @RabbitListener (queues = {"validaProdutos"}, ackMode = "MANUAL")
    public void consume(PedidoDTO pedidoDTO, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            List<ProdutoDTO> produtos = pedidoDTO.getProdutos().stream()
                    .map(pedidoProduto -> pedidoProduto.getProduto()).collect(Collectors.toList());
            ValidaProdutoResponseDTO validaProdutoResponseDTO = produtoService.validateProduto(new ValidaProdutoRequestDTO(produtos));
            if(validaProdutoResponseDTO.isValid()){
                rabbitMQProducer.sendToCallbackValidacaoQueue(pedidoDTO);
            }
            else {
                rabbitMQProducer.sendToErroValidacaoQueue(validaProdutoResponseDTO, pedidoDTO.getNumeroPedido().intValue());
            }
            channel.basicAck(tag, false);
        }
            catch(Exception e) {
            channel.basicReject(tag, false);
        }
    }
}
