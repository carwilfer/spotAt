package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.Assinatura;
import com.infnet.spotiat.repository.AssinaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    public List<Assinatura> listarAssinaturas() {
        return assinaturaRepository.findAll();
    }

    public Assinatura obterAssinatura(UUID id) {
        return assinaturaRepository.findById(id).orElse(null);
    }

    public Assinatura criarAssinatura(Assinatura assinatura) {
        return assinaturaRepository.save(assinatura);
    }

    public Assinatura atualizarAssinatura(UUID id, Assinatura assinatura) {
        assinatura.setId(id);
        return assinaturaRepository.save(assinatura);
    }

    public void deletarAssinatura(UUID id) {
        assinaturaRepository.deleteById(id);
    }
}
