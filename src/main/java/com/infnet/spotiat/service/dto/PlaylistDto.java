package com.infnet.spotiat.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PlaylistDto {
    private UUID idUsuario;
    private String nome;
}
