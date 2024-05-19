package br.com.postech.software.architecture.techchallenge.produto.connector;

import org.springframework.stereotype.Component;

@Component
public class MercadoLivreConnector {

	private static final String MERCADO_PAGO_URI = "https://api.mercadopago.com";

	public MercadoPagoQrCodeResponseDTO generateMercadoPagoQrCode(MercadoPagoQrCodeRequestDTO dto) throws Exception {
		
		try {
			//RestTemplate restTemplate = new RestTemplate();
            //ResponseEntity<MercadoPagoQrCodeResponseDTO> responseEntity = restTemplate.postForEntity(
			//	    MERCADO_PAGO_URI, dto, MercadoPagoQrCodeResponseDTO.class);
            //return responseEntity.getBody();

			return new MercadoPagoQrCodeResponseDTO("staticQrCode");
        } catch (Exception exception) {
            throw new Exception("Erro ao gerar QR Code: " + exception.getMessage());
        }
    }
}
