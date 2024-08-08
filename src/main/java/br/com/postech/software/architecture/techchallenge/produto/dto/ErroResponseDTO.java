package br.com.postech.software.architecture.techchallenge.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true, setterPrefix = "set")
@AllArgsConstructor
@NoArgsConstructor
public class ErroResponseDTO {

    private Integer pedidoId;
    private boolean isValid;
    private String errorMessage;
}
