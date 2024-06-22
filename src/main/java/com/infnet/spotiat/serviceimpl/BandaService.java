package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.Banda;
import com.infnet.spotiat.domain.Musica;
import com.infnet.spotiat.repository.BandaRepository;
import com.infnet.spotiat.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BandaService {

    @Autowired
    private BandaRepository bandaRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    public Banda buscarPorId(UUID id) throws Exception {
        return bandaRepository.findById(id)
                .orElseThrow(() -> new Exception("Banda não encontrada"));
    }

    public List<Banda> listarBandas() {
        return bandaRepository.findAll();
    }

    public Banda criarBanda(Banda banda) {
        return bandaRepository.save(banda);
    }

    public Banda atualizarBanda(UUID id, Banda banda) {
        banda.setId(id);
        return bandaRepository.save(banda);
    }

    public void deletarBanda(UUID id) {
        bandaRepository.deleteById(id);
    }

    public List<Musica> listarMusicasDaBanda(UUID bandaId) {
        Banda banda = bandaRepository.findById(bandaId).orElse(null);
        if (banda != null) {
            return banda.getMusicas();
        } else {
            return new ArrayList<>(); // Retornar lista vazia em vez de null
        }
    }

    public void adicionarMusica(UUID bandaId, Musica musica) throws Exception {
        Banda banda = buscarPorId(bandaId);
        musica.setBanda(banda);
        banda.getMusicas().add(musica);
        bandaRepository.save(banda);
    }

    public void removerMusica(UUID bandaId, UUID musicaId) throws Exception {
        Banda banda = buscarPorId(bandaId);
        Musica musica = musicaRepository.findById(musicaId)
                .orElseThrow(() -> new Exception("Música não encontrada"));
        banda.getMusicas().remove(musica);
        bandaRepository.save(banda);
    }
}
