package br.com.postech.software.architecture.techchallenge.produto.repository;

import br.com.postech.software.architecture.techchallenge.produto.model.Produto;
import br.com.postech.software.architecture.techchallenge.produto.model.ProdutoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(ProdutoCategoria categoria);

}
