package com.infnet.spotiat.mapper;

import com.infnet.spotiat.domain.Cartao;
import com.infnet.spotiat.domain.Plano;
import com.infnet.spotiat.domain.Usuario;
import com.infnet.spotiat.service.dto.UsuarioDto;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        UsuarioDto response = new UsuarioDto();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setNome(usuario.getNome());
        response.setSenha("************");
        response.setNumeroCartao("***** ***** ***** *****");

        // Supondo que o usuário tenha pelo menos um cartão
        if (!usuario.getCartoes().isEmpty()) {
            Cartao cartao = usuario.getCartoes().get(0);
            response.setCartaoAtivo(cartao.getAtivo());
            response.setLimite(cartao.getLimite());
        }
        if (usuario.getPlano() != null) {
            response.setIdPlano(usuario.getPlano().getId());
            response.setTipoAssinatura(usuario.getPlano().getTipoAssinatura());
        }
        response.setCpf(usuario.getCpf());
        return response;
    }
    public static UsuarioDto toDto(Usuario usuario, Cartao cartao, Plano plano) {
        UsuarioDto response = new UsuarioDto();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setNome(usuario.getNome());
        response.setSenha("************");
        response.setNumeroCartao("***** ***** ***** *****");
        response.setCartaoAtivo(cartao.getAtivo());
        response.setLimite(cartao.getLimite());
        response.setIdPlano(plano.getId());
        response.setTipoAssinatura(plano.getTipoAssinatura());
        response.setCpf(usuario.getCpf());
        return response;
    }
}
