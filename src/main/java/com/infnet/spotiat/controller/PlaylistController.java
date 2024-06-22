package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Playlist;
import com.infnet.spotiat.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @GetMapping
    public List<Playlist> listar() {
        return playlistRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> obter(@PathVariable UUID id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent()) {
            return ResponseEntity.ok(playlist.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Playlist criar(@RequestBody Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> atualizar(@PathVariable UUID id, @RequestBody Playlist playlist) {
        if (playlistRepository.existsById(id)) {
            playlist.setId(id);
            return ResponseEntity.ok(playlistRepository.save(playlist));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        if (playlistRepository.existsById(id)) {
            playlistRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
