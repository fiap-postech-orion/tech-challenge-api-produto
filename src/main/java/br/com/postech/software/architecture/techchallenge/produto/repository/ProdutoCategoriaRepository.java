package br.com.postech.software.architecture.techchallenge.produto.repository;

import br.com.postech.software.architecture.techchallenge.produto.model.ProdutoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoCategoriaRepository extends JpaRepository<ProdutoCategoria, Long> {

    List<ProdutoCategoria> findAllByIdOrDescricaoOrSigla(Long id, String descricao, String sigla);
}
