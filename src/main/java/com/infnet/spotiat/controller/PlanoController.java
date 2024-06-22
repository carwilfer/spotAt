package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Plano;
import com.infnet.spotiat.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/plano")
public class PlanoController {

    @Autowired
    private PlanoRepository planoRepository;

    @GetMapping
    public List<Plano> listar() {
        return planoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> obter(@PathVariable UUID id) {
        Optional<Plano> plano = planoRepository.findById(id);
        if (plano.isPresent()) {
            return ResponseEntity.ok(plano.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Plano criar(@RequestBody Plano plano) {
        return planoRepository.save(plano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plano> atualizar(@PathVariable UUID id, @RequestBody Plano plano) {
        if (planoRepository.existsById(id)) {
            plano.setId(id);
            return ResponseEntity.ok(planoRepository.save(plano));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        if (planoRepository.existsById(id)) {
            planoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
