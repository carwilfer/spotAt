package com.infnet.spotiat.service;

import com.infnet.spotiat.domain.Usuario;
import com.infnet.spotiat.service.dto.UsuarioDto;

import java.util.UUID;

public interface IUsuarioService {
    UsuarioDto criar(UsuarioDto dto) throws Exception;
    Usuario buscarPorId(UUID id) throws Exception;
    void salvar(Usuario usuario);
}
