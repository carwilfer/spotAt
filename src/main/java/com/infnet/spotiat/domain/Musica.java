package com.infnet.spotiat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Getter
@Setter
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private int duracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banda_id", referencedColumnName = "id")
    @JsonBackReference
    private Banda banda;
}
