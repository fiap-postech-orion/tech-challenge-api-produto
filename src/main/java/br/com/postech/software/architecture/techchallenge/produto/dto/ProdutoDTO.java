package br.com.postech.software.architecture.techchallenge.produto.dto;

import br.com.postech.software.architecture.techchallenge.produto.enums.CategoriaEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private CategoriaEnum categoria;
    @NotNull
    @Min(1)
    private BigDecimal valor;
    @NotNull
    private String descricao;
    @NotEmpty
    private List<ProdutoImagesDTO> imagens;

    public void sanitiseDTO() {
        this.setNome(strip(this.getNome()));
        this.setDescricao(strip(this.getDescricao()));
    }

    private String strip(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
