package br.com.postech.software.architecture.techchallenge.producao.configuration;

import br.com.postech.software.architecture.techchallenge.producao.enums.StatusPedidoEnum;
import org.modelmapper.AbstractConverter;

public class InteiroParaStatusPedidoConverter extends AbstractConverter<Integer, StatusPedidoEnum> {

	@Override
	protected StatusPedidoEnum convert(Integer source) {
		return StatusPedidoEnum.get(source);
	}

}
