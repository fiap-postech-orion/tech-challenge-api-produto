package br.com.postech.software.architecture.techchallenge.produto.configuration;

import org.modelmapper.AbstractConverter;

public class StatusPedidoParaInteiroConverter extends AbstractConverter<StatusPedidoEnum , Integer> {

	@Override
	protected Integer convert(StatusPedidoEnum source) {
		return source == null ? null : source.getValue();
	}

}
