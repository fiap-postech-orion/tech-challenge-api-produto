package br.com.postech.software.architecture.techchallenge.producao.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class PedidoProduto {

	private Produto produto;
    @NotNull(message = "A quantidade é obrigatório.")
    private Integer quantidade;
}
