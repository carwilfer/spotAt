package com.infnet.spotiat.domain;

import com.infnet.spotiat.repository.PlanoRepository;
import com.infnet.spotiat.repository.UsuarioRepository;
import com.infnet.spotiat.serviceimpl.UsuarioService;
import com.infnet.spotiat.service.dto.UsuarioDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PlanoRepository planoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioDto usuarioDto;
    private Plano plano;
    private Cartao cartao;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Test User");
        usuario.setEmail("test@example.com");
        usuario.setSenha("password");
        usuario.setCpf("12345678901");

        plano = new Plano();
        plano.setId(UUID.randomUUID());
        plano.setNome("Gold Plan");
        plano.setTipoAssinatura(TipoAssinatura.GOLD_PREMIUM);
        plano.setValor(100.0);

        cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setNumero("1234 5678 9012 3456");
        cartao.setAtivo(true);
        cartao.setLimite(5000.0);

        usuarioDto = new UsuarioDto();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setSenha(usuario.getSenha());
        usuarioDto.setCpf(usuario.getCpf());
        usuarioDto.setNumeroCartao(cartao.getNumero());
        usuarioDto.setLimite(cartao.getLimite());
        usuarioDto.setCartaoAtivo(cartao.getAtivo());
        usuarioDto.setIdPlano(plano.getId());
        usuarioDto.setTipoAssinatura(plano.getTipoAssinatura());
    }

    @Test
    public void testCriarUsuario() throws Exception {
        when(planoRepository.findById(any(UUID.class))).thenReturn(Optional.of(plano));
        when(usuarioRepository.findUsuarioByCpf(any(String.class))).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDto createdUsuario = usuarioService.criar(usuarioDto);

        assertNotNull(createdUsuario);
        assertEquals(usuario.getNome(), createdUsuario.getNome());
        assertEquals(usuario.getEmail(), createdUsuario.getEmail());
        assertEquals(usuario.getCpf(), createdUsuario.getCpf());
    }

    @Test
    public void testCriarUsuarioPlanoNotFound() {
        when(planoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.criar(usuarioDto);
        });

        assertEquals("Plano não encontrado", exception.getMessage());
    }

    @Test
    public void testCriarUsuarioCpfAlreadyExists() {
        when(planoRepository.findById(any(UUID.class))).thenReturn(Optional.of(plano));
        when(usuarioRepository.findUsuarioByCpf(any(String.class))).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.criar(usuarioDto);
        });

        assertEquals("CPF do usuário já cadastrado", exception.getMessage());
    }

    @Test
    public void testBuscarPorIdUsuarioNotFound() {
        when(usuarioRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.buscarPorId(UUID.randomUUID());
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
    @Test
    public void testAtualizarUsuario() throws Exception {
        // Simulando um usuário existente no banco
        UUID usuarioIdExistente = UUID.randomUUID();
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioIdExistente);
        usuarioExistente.setNome("Existing User");
        usuarioExistente.setEmail("existing@example.com");
        usuarioExistente.setSenha("oldpassword");
        usuarioExistente.setCpf("98765432109");

        // Configurando os mocks necessários
        when(usuarioRepository.findById(usuarioExistente.getId())).thenReturn(Optional.of(usuarioExistente));
        when(planoRepository.findById(any(UUID.class))).thenReturn(Optional.of(plano));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        // Atualizando dados do usuário
        UsuarioDto usuarioDtoAtualizado = new UsuarioDto();
        usuarioDtoAtualizado.setId(usuarioExistente.getId());
        usuarioDtoAtualizado.setNome("Updated User");
        usuarioDtoAtualizado.setEmail("updated@example.com");
        usuarioDtoAtualizado.setSenha("newpassword");
        usuarioDtoAtualizado.setCpf(usuarioExistente.getCpf());
        usuarioDtoAtualizado.setNumeroCartao(cartao.getNumero());
        usuarioDtoAtualizado.setLimite(cartao.getLimite());
        usuarioDtoAtualizado.setCartaoAtivo(cartao.getAtivo());
        usuarioDtoAtualizado.setIdPlano(plano.getId());
        usuarioDtoAtualizado.setTipoAssinatura(plano.getTipoAssinatura());

        // Chamando o método a ser testado
        UsuarioDto updatedUsuario = usuarioService.atualizarUsuario(usuarioIdExistente, usuarioDtoAtualizado);

        // Verificando os resultados
        assertNotNull(updatedUsuario);
        assertEquals(usuarioDtoAtualizado.getNome(), updatedUsuario.getNome());
        assertEquals(usuarioDtoAtualizado.getEmail(), updatedUsuario.getEmail());
    }
    @Test
    void testCriarPlaylist() throws Exception {
        // Mocking behavior of repository and service
        UUID usuarioId = UUID.randomUUID();
        String playlistNome = "Minhas Músicas";
        usuario.setId(usuarioId);
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        // Calling service method
        usuarioService.criarPlaylist(usuarioId, playlistNome);

        // Verifying playlist creation
        assertEquals(1, usuario.getPlaylists().size());
        assertEquals(playlistNome, usuario.getPlaylists().get(0).getNome());
    }
}
