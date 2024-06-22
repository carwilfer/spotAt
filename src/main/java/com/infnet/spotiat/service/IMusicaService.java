package com.infnet.spotiat.service;

import com.infnet.spotiat.domain.Musica;

import java.util.UUID;

public interface IMusicaService {
    Musica buscarPorId(UUID id) throws Exception;
}
