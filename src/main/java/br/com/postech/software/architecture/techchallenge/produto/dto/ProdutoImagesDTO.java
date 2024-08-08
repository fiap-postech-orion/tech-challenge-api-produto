package br.com.postech.software.architecture.techchallenge.produto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoImagesDTO {

	@Size(min = 1, max = 999)
	@NotNull(message = "É obrigatório informar o caminho da imagem.")
	private String path;
}
