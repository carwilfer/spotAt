package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Banda;
import com.infnet.spotiat.repository.BandaRepository;
import com.infnet.spotiat.serviceimpl.BandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/banda")
public class BandaController {

    @Autowired
    private BandaService bandaService;

    @GetMapping
    public List<Banda> listar() {
        return bandaService.listarBandas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banda> obter(@PathVariable UUID id) {
        try {
            Banda banda = bandaService.buscarPorId(id);
            return ResponseEntity.ok(banda);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Banda criar(@RequestBody Banda banda) {
        return bandaService.criarBanda(banda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banda> atualizar(@PathVariable UUID id, @RequestBody Banda banda) {
        try {
            Banda updatedBanda = bandaService.atualizarBanda(id, banda);
            return ResponseEntity.ok(updatedBanda);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        try {
            bandaService.deletarBanda(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
