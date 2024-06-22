package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Assinatura;
import com.infnet.spotiat.repository.AssinaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/assinatura")
public class AssinaturaController {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @GetMapping
    public List<Assinatura> listar() {
        return assinaturaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assinatura> obter(@PathVariable UUID id) {
        Optional<Assinatura> assinatura = assinaturaRepository.findById(id);
        if (assinatura.isPresent()) {
            return ResponseEntity.ok(assinatura.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Assinatura criar(@RequestBody Assinatura assinatura) {
        return assinaturaRepository.save(assinatura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assinatura> atualizar(@PathVariable UUID id, @RequestBody Assinatura assinatura) {
        if (assinaturaRepository.existsById(id)) {
            assinatura.setId(id);
            return ResponseEntity.ok(assinaturaRepository.save(assinatura));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        if (assinaturaRepository.existsById(id)) {
            assinaturaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
