package com.infnet.spotiat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Getter
@Setter
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String numero;

    @Column
    private Boolean ativo;

    @Column
    private LocalDate validade;

    @Column
    private double limite;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_transacao", referencedColumnName = "id")
    private List<Transacao> transacoes = new ArrayList<>();

    private int TRANSACAO_INTERVALO_TEMPO = 2;
    private int TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA = 3;
    private int TRANSACAO_MERCHANT_DUPLICADAS = 2;

    public void criarTransacao(String merchant, double valor, String descricao) throws Exception {

        //Validar Cartao
        if (this.ativo == false) {
            throw new Exception("Cartão não está ativo");
        }

        Transacao transacao = new Transacao();
        transacao.setMerchant(merchant);
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setCartao(this);

        //Verificar Limite
        if (this.validarLimite(transacao) == false) {
            throw new Exception("Cartão não possui limite para esta transação");
        }

        //Validar Transacao
        if (this.validarTransacao(transacao) == false) {
            throw new Exception("Transação invalida");
        }

        //Diminui o limite do cartao
        this.setLimite(this.getLimite() - transacao.getValor());


        //Adiciona uma nova transacao e cria o codigo de autorização
        transacao.setCodigoAutorizacao(UUID.randomUUID());
        this.transacoes.add(transacao);
    }
    private boolean validarTransacao(Transacao transacao) {
        List<Transacao> ultimasTransacoes = this.getTransacoes().stream()
                .filter((x) -> x.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(this.TRANSACAO_INTERVALO_TEMPO)))
                .toList();

        if (ultimasTransacoes.size() >= this.TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA)
            return false;

        List<Transacao> transacoesMerchantRepetidas = ultimasTransacoes.stream()
                .filter((x) -> x.getMerchant().equals(transacao.getMerchant())
                        && x.getValor() == transacao.getValor())
                .toList();

        if (transacoesMerchantRepetidas.size() >= this.TRANSACAO_MERCHANT_DUPLICADAS)
            return false;

        return true;
    }

    private boolean validarLimite(Transacao transacao) {
        return this.limite > transacao.getValor();
    }
}
