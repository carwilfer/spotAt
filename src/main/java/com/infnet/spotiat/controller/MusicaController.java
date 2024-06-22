package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Musica;
import com.infnet.spotiat.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaRepository musicaRepository;

    @GetMapping
    public List<Musica> listarMusicas() {
        return musicaRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Musica> obter(@PathVariable UUID id) {
        Optional<Musica> musica = musicaRepository.findById(id);
        if (musica.isPresent()) {
            return ResponseEntity.ok(musica.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Musica criar(@RequestBody Musica musica) {
        return musicaRepository.save(musica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musica> atualizar(@PathVariable UUID id, @RequestBody Musica musica) {
        if (musicaRepository.existsById(id)) {
            musica.setId(id);
            return ResponseEntity.ok(musicaRepository.save(musica));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        if (musicaRepository.existsById(id)) {
            musicaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
