package br.com.postech.software.architecture.techchallenge.produto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCategoriaDTO {

    private Long id;
    @NotNull
    private String sigla;
    @NotNull
    private String descricao;
}
