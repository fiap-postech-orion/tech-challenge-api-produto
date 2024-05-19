package br.com.postech.software.architecture.techchallenge.producao.model;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document("PedidoProducao")
public class PedidoProducao {

    @Id
    private String uuid;
    private Long numeroPedido;
    private Cliente cliente;
    private String dataPedido;
    private String statusPedido;
    @NotEmpty
    private List<PedidoProduto> produtos;

    public void update(PedidoProducao pedidoProducao) {
        this.statusPedido = pedidoProducao.getStatusPedido();
        this.cliente.update(pedidoProducao.getCliente());
        this.produtos = pedidoProducao.getProdutos();
    }
}
