package br.com.postech.software.architecture.techchallenge.produto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


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
