package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.Musica;
import com.infnet.spotiat.repository.MusicaRepository;
import com.infnet.spotiat.service.IMusicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MusicaService implements IMusicaService {

    @Autowired
    private MusicaRepository musicaRepository;

    public Musica buscarPorId(UUID id) throws Exception {
        return musicaRepository.findById(id)
                .orElseThrow(() -> new Exception("Música não encontrada"));
    }
    public List<Musica> listarMusicas() {
        return musicaRepository.findAll();
    }
    public Musica criarMusica(Musica musica) {
        return musicaRepository.save(musica);
    }

    public Musica atualizarMusica(UUID id, Musica musica) {
        musica.setId(id);
        return musicaRepository.save(musica);
    }

    public void deletarMusica(UUID id) {
        musicaRepository.deleteById(id);
    }
}
