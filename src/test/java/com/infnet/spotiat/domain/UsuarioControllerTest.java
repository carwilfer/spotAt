package com.infnet.spotiat.domain;

import com.infnet.spotiat.controller.UsuarioController;
import com.infnet.spotiat.service.dto.UsuarioDto;
import com.infnet.spotiat.serviceimpl.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioDto usuarioDto;

    @BeforeEach
    public void setUp() {
        usuarioDto = new UsuarioDto();
        usuarioDto.setId(UUID.randomUUID());
        usuarioDto.setNome("Test User");
        usuarioDto.setEmail("test@example.com");
        usuarioDto.setSenha("password");
        usuarioDto.setCpf("12345678901");
        usuarioDto.setNumeroCartao("1234 5678 9012 3456");
        usuarioDto.setLimite(5000.0);
        usuarioDto.setCartaoAtivo(true);
        usuarioDto.setIdPlano(UUID.randomUUID());
        usuarioDto.setTipoAssinatura(TipoAssinatura.GOLD_PREMIUM);
    }

    @Test
    public void testCriarUsuario() throws Exception {
        when(usuarioService.criar(any(UsuarioDto.class))).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> response = usuarioController.criar(usuarioDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioDto.getNome(), response.getBody().getNome());
    }

    @Test
    public void testBuscarUsuarioPorId() throws Exception {
        when(usuarioService.buscarPorId(any(UUID.class))).thenReturn(new Usuario());

        ResponseEntity<UsuarioDto> response = usuarioController.buscarPorId(usuarioDto.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAtualizarUsuario() throws Exception {
        when(usuarioService.atualizarUsuario(any(UUID.class), any(UsuarioDto.class))).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> response = usuarioController.atualizar(usuarioDto.getId(), usuarioDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioDto.getNome(), response.getBody().getNome());
    }

    @Test
    public void testDeletarUsuario() throws Exception {
        doNothing().when(usuarioService).deletarUsuario(any(UUID.class));

        ResponseEntity<Void> response = usuarioController.deletar(usuarioDto.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
