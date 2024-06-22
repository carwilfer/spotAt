package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.Playlist;
import com.infnet.spotiat.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> listarPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist buscarPorId(UUID playlistId) {
        return playlistRepository.findById(playlistId).orElse(null);
    }
}
