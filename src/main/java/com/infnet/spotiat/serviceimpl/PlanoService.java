package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.Plano;
import com.infnet.spotiat.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    public List<Plano> listarPlanosAtivos() {
        return planoRepository.findByAtivoTrue();
    }

    public Plano buscarPorId(UUID planoId) {
        return planoRepository.findById(planoId).orElse(null);
    }

    public Plano criarPlano(Plano plano) {
        // Implemente validações ou lógica de negócio antes de salvar
        return planoRepository.save(plano);
    }

    public Plano atualizarPlano(UUID planoId, Plano planoAtualizado) {
        Plano planoExistente = planoRepository.findById(planoId).orElse(null);
        if (planoExistente == null) {
            return null; // Ou lançar uma exceção
        }

        // Atualize os campos do planoExistente com os dados de planoAtualizado
        planoExistente.setNome(planoAtualizado.getNome());
        planoExistente.setDescricao(planoAtualizado.getDescricao());
        planoExistente.setAtivo(planoAtualizado.isAtivo());
        planoExistente.setTipoAssinatura(planoAtualizado.getTipoAssinatura());
        planoExistente.setValor(planoAtualizado.getValor());

        return planoRepository.save(planoExistente);
    }

    public void excluirPlano(UUID planoId) {
        planoRepository.deleteById(planoId);
    }
}
