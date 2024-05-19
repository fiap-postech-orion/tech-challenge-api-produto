package br.com.postech.software.architecture.techchallenge.producao.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

	private Long id;
	private String nome;
	@Email(message = "Email inválido, digite novamente", regexp = ".+[@].+[\\.].+")
	private String email;
	@CPF(message="CPF inválido, digite novamente")
	private String cpf;
	@Size(min = 6, max = 20)
	@NotNull
	private String senha;
	private Boolean status;

	public void update(Cliente cliente) {
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.cpf = cliente.getCpf();
		this.status = cliente.getStatus();
	}
}
