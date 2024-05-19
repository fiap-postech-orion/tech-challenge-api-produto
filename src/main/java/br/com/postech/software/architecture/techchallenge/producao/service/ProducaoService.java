package br.com.postech.software.architecture.techchallenge.producao.service;

import br.com.postech.software.architecture.techchallenge.producao.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.producao.model.PedidoProducao;

import java.util.List;

public interface ProducaoService {

	List<PedidoProducao> findAllByStatusPedido(String status) throws BusinessException;

	public PedidoProducao findByNumeroPedido(Long numeroPedido) throws BusinessException;

	PedidoProducao salvaPedido(PedidoProducao producaoUpdateDTO);
}
