package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Transacao;
import com.infnet.spotiat.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping
    public List<Transacao> listar() {
        return transacaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> obter(@PathVariable UUID id) {
        Optional<Transacao> transacao = transacaoRepository.findById(id);
        if (transacao.isPresent()) {
            return ResponseEntity.ok(transacao.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Transacao criar(@RequestBody Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> atualizar(@PathVariable UUID id, @RequestBody Transacao transacao) {
        if (transacaoRepository.existsById(id)) {
            transacao.setCodigoAutorizacao(id);
            return ResponseEntity.ok(transacaoRepository.save(transacao));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        if (transacaoRepository.existsById(id)) {
            transacaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
