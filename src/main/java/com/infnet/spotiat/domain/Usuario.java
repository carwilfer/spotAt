package com.infnet.spotiat.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cartao> cartoes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Assinatura> assinaturas = new ArrayList<>();

    private static final String DEFAULT_PLAYLIST_NAME = "Musicas Favoritas";

    public void criar(String nome, String email, String senha, String cpf, Cartao cartao, Plano plano) throws Exception {
        if (!isValidoCpf(cpf)) {
            throw new Exception("CPF está invalido");
        }

        for (Assinatura assinatura : this.assinaturas) {
            if (assinatura.isAtivo()) {
                throw new Exception("Usuário já possui um plano ativo");
            }
        }

        this.setCpf(cpf);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(this.encodeSenha(senha));

        // Transacionar no cartao
        cartao.criarTransacao(plano.getNome(), plano.getValor(), plano.getDescricao());

        // Adicionar o cartao na lista do usuário
        this.cartoes.add(cartao);

        // Criar uma assinatura
        Assinatura assinatura = new Assinatura();
        assinatura.setPlano(plano);
        assinatura.setAtivo(true);
        assinatura.setDtAssinatura(LocalDateTime.now());

        this.assinaturas.add(assinatura);

        // Criar uma playlist default
        Playlist playlist = new Playlist();
        playlist.setNome(DEFAULT_PLAYLIST_NAME);
        this.playlists.add(playlist);
    }

    public void criarPlaylist(String nome) throws Exception {
        // Verificar se o usuário possui uma assinatura GoldPremium ativa
        boolean hasGoldPremium = this.assinaturas.stream()
                .anyMatch(a -> a.isAtivo() && a.getPlano().getTipoAssinatura() == TipoAssinatura.GOLD_PREMIUM);

        if (!hasGoldPremium) {
            throw new Exception("Usuário não possui assinatura GoldPremium para criar playlists");
        }

        Playlist playlist = new Playlist();
        playlist.setNome(nome);
        playlist.setUsuario(this);
        this.playlists.add(playlist);
    }

    public void favoritarMusica(UUID idPlaylist, Musica musica) throws Exception {
        // Verificar se o usuário possui uma assinatura GoldPremium ativa
        boolean hasGoldPremium = this.assinaturas.stream()
                .anyMatch(a -> a.isAtivo() && a.getPlano().getTipoAssinatura() == TipoAssinatura.GOLD_PREMIUM);

        if (!hasGoldPremium) {
            throw new Exception("Usuário não possui assinatura GoldPremium para favoritar músicas");
        }

        Playlist playlist = this.playlists.stream()
                .filter(p -> p.getId().equals(idPlaylist))
                .findFirst()
                .orElseThrow(() -> new Exception("Playlist não encontrada"));

        playlist.getMusicas().add(musica);
    }

    public void desfavoritarMusica(UUID idPlaylist, Musica musica) throws Exception {
        Playlist playlist = this.playlists.stream()
                .filter(p -> p.getId().equals(idPlaylist))
                .findFirst()
                .orElseThrow(() -> new Exception("Playlist não encontrada"));

        playlist.getMusicas().remove(musica);
    }

    private String encodeSenha(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    private boolean isValidoCpf(String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
    public boolean hasGoldPremium() {
        return this.assinaturas.stream()
                .anyMatch(a -> a.isAtivo() && a.getPlano().getTipoAssinatura() == TipoAssinatura.GOLD_PREMIUM);
    }

    public Playlist findPlaylistById(UUID idPlaylist) throws Exception {
        return this.playlists.stream()
                .filter(p -> p.getId().equals(idPlaylist))
                .findFirst()
                .orElseThrow(() -> new Exception("Playlist não encontrada"));
    }
}
