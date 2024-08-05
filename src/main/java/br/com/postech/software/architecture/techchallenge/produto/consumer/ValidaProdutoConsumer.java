package br.com.postech.software.architecture.techchallenge.produto.consumer;

import br.com.postech.software.architecture.techchallenge.produto.dto.PedidoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ProdutoDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoRequestDTO;
import br.com.postech.software.architecture.techchallenge.produto.dto.ValidaProdutoResponseDTO;
import br.com.postech.software.architecture.techchallenge.produto.producer.RabbitMQProducer;
import br.com.postech.software.architecture.techchallenge.produto.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ValidaProdutoConsumer {

    private ProdutoService produtoService;
    private RabbitMQProducer rabbitMQProducer;

    @RabbitListener (queues = {"${validaProdutos}"})
    public void consume(PedidoDTO pedidoDTO) {
        List<ProdutoDTO> produtos = pedidoDTO.getProdutos().stream()
                .map(pedidoProduto -> pedidoProduto.getProduto()).collect(Collectors.toList());
        ValidaProdutoResponseDTO validaProdutoResponseDTO = produtoService.validateProduto(new ValidaProdutoRequestDTO(produtos));
        if(validaProdutoResponseDTO.isValid()){
            rabbitMQProducer.sendToCallbackValidacaoQueue(pedidoDTO);
        }
        else {
            rabbitMQProducer.sendToErroValidacaoQueue(validaProdutoResponseDTO, pedidoDTO.getNumeroPedido().intValue());
        }
    }
}
