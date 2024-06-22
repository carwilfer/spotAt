package com.infnet.spotiat.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Getter
@Setter
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;
    @Column
    private boolean ativo;
    @Column
    private String descricao;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoAssinatura tipoAssinatura;

    @Column
    private double valor;
}
