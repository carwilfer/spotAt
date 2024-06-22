package com.infnet.spotiat.serviceimpl;

import com.infnet.spotiat.domain.*;
import com.infnet.spotiat.mapper.UsuarioMapper;
import com.infnet.spotiat.repository.*;
import com.infnet.spotiat.service.IUsuarioService;
import com.infnet.spotiat.service.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    public UsuarioDto criar(UsuarioDto dto) throws Exception {
        Optional<Plano> optionalPlano = planoRepository.findById(dto.getIdPlano());
        Plano plano = optionalPlano.orElseThrow(() -> new Exception("Plano não encontrado"));

        if (usuarioRepository.findUsuarioByCpf(dto.getCpf()).isPresent()) {
            throw new Exception("CPF do usuário já cadastrado");
        }

        Cartao cartao = new Cartao();
        cartao.setNumero(dto.getNumeroCartao());
        cartao.setAtivo(dto.getCartaoAtivo());
        cartao.setLimite(dto.getLimite());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(encodeSenha(dto.getSenha()));
        usuario.setCpf(dto.getCpf());
        usuario.setPlano(plano);
        usuario.getCartoes().add(cartao);
        cartao.setUsuario(usuario);

        Assinatura assinatura = new Assinatura();
        assinatura.setPlano(plano);
        assinatura.setAtivo(true);
        assinatura.setDtAssinatura(LocalDateTime.now());
        assinatura.setUsuario(usuario);

        usuario.getAssinaturas().add(assinatura);

        Playlist playlist = new Playlist();
        playlist.setNome("Musicas Favoritas");
        playlist.setUsuario(usuario);

        usuario.getPlaylists().add(playlist);

        usuarioRepository.save(usuario);

        return UsuarioMapper.toDto(usuario, cartao, plano);
    }

    public void criarPlaylist(UUID usuarioId, String nomePlaylist) throws Exception {
        Usuario usuario = buscarPorId(usuarioId);

        if (!usuario.hasGoldPremium()) {
            throw new Exception("Usuário não possui assinatura GoldPremium para criar playlists");
        }

        Playlist playlist = new Playlist();
        playlist.setNome(nomePlaylist);
        playlist.setUsuario(usuario);

        usuario.getPlaylists().add(playlist);
        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(UUID id) throws Exception {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    private String encodeSenha(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    public UsuarioDto atualizarUsuario(UUID id, UsuarioDto dto) throws Exception {
        Usuario usuario = buscarPorId(id);
        usuario.setNome(dto.getNome());
        usuario.setSenha(encodeSenha(dto.getSenha()));
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        // Atualize outros campos conforme necessário
        salvar(usuario);
        return UsuarioMapper.toDto(usuario);
    }

    public void deletarUsuario(UUID id) throws Exception {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}
