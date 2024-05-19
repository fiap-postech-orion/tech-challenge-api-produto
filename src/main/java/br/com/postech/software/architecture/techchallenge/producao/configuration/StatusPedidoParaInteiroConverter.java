package br.com.postech.software.architecture.techchallenge.producao.configuration;

import br.com.postech.software.architecture.techchallenge.producao.enums.StatusPedidoEnum;
import org.modelmapper.AbstractConverter;

public class StatusPedidoParaInteiroConverter extends AbstractConverter<StatusPedidoEnum , Integer> {

	@Override
	protected Integer convert(StatusPedidoEnum source) {
		return source == null ? null : source.getValue();
	}

}
