package com.infnet.spotiat.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Getter
@Setter
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID codigoAutorizacao;

    @Column
    private double valor;

    @Column
    private LocalDateTime dtTransacao;

    @Column
    private String merchant;

    @Column
    private String descricao;

    @ManyToOne
    private Cartao Cartao;
}
