package com.infnet.spotiat.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class FavoritarMusicaDto {
    private UUID idPlaylist;
    private UUID idMusica;
}
