package br.com.postech.software.architecture.techchallenge.produto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "produto_categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoCategoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Descricao da categoria")
    @Column(nullable = false, length = 500)
    private String descricao;

    @NotNull(message = "Sigla da categoria")
    @Column(nullable = false, length = 500)
    private String sigla;
}
