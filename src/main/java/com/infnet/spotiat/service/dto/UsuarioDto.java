package com.infnet.spotiat.service.dto;

import com.infnet.spotiat.domain.TipoAssinatura;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class UsuarioDto {
    private UUID id;
    @NotEmpty(message = "Campo nome é obrigatório")
    private String nome;
    @NotEmpty(message = "Campo senha é obrigatório")
    private String senha;
    @NotEmpty(message = "Campo cpf é obrigatório")
    private String cpf;
    @NotEmpty(message = "Campo email é obrigatório")
    @Email(message = "campo email não está em um formato válido")
    private String email;
    @NotEmpty(message = "Campo número do cartão é obrigatório")
    private String numeroCartao;
    @NotNull(message = "Campo limite do cartão é obrigatório")
    private Double limite;
    @NotNull(message = "Campo cartão ativo é obrigatório")
    private Boolean cartaoAtivo;
    @NotNull(message = "Campo plano é obrigatório")
    private UUID idPlano;
    private TipoAssinatura tipoAssinatura;
}
