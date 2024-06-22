package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.Cartao;
import com.infnet.spotiat.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public void criarTransacao(UUID cartaoId, String merchant, double valor, String descricao) throws Exception {
        Cartao cartao = cartaoRepository.findById(cartaoId).orElse(null);
        if (cartao == null) {
            throw new Exception("Cartão não encontrado");
        }

        cartao.criarTransacao(merchant, valor, descricao);

        cartaoRepository.save(cartao); // Salva as alterações no cartão
    }

    public Cartao buscarPorId(UUID cartaoId) {
        return cartaoRepository.findById(cartaoId).orElse(null);
    }
}
