package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.Transacao;
import com.infnet.spotiat.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public Transacao buscarPorCodigoAutorizacao(UUID codigoAutorizacao) {
        return transacaoRepository.findById(codigoAutorizacao).orElse(null);
    }
}
