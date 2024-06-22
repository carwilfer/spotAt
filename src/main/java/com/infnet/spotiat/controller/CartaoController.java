package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Cartao;
import com.infnet.spotiat.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @GetMapping
    public List<Cartao> listar() {
        return cartaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> obter(@PathVariable UUID id) {
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        if (cartao.isPresent()) {
            return ResponseEntity.ok(cartao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Cartao criar(@RequestBody Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cartao> atualizar(@PathVariable UUID id, @RequestBody Cartao cartao) {
        if (cartaoRepository.existsById(id)) {
            cartao.setId(id);
            return ResponseEntity.ok(cartaoRepository.save(cartao));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        if (cartaoRepository.existsById(id)) {
            cartaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
