package com.infnet.spotiat.controller;

import com.infnet.spotiat.domain.Musica;
import com.infnet.spotiat.domain.Usuario;
import com.infnet.spotiat.mapper.UsuarioMapper;
import com.infnet.spotiat.service.dto.PlaylistDto;
import com.infnet.spotiat.serviceimpl.MusicaService;
import com.infnet.spotiat.serviceimpl.UsuarioService;
import com.infnet.spotiat.service.dto.FavoritarMusicaDto;
import com.infnet.spotiat.service.dto.UsuarioDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MusicaService musicaService;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping
    public ResponseEntity<UsuarioDto> criar(@RequestBody @Valid UsuarioDto dto) throws Exception {
        UsuarioDto response = usuarioService.criar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/playlist")
    public ResponseEntity<Void> criarPlaylist(@PathVariable UUID id, @RequestBody PlaylistDto playlistDto) {
        try {
            usuarioService.criarPlaylist(id, playlistDto.getNome());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception
            logger.error("Erro ao criar playlist", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/favoritar")
    public ResponseEntity<Void> favoritarMusica(@PathVariable UUID id, @RequestBody FavoritarMusicaDto dto) throws Exception {
        Usuario usuario = usuarioService.buscarPorId(id);
        Musica musica = musicaService.buscarPorId(dto.getIdMusica());
        usuario.favoritarMusica(dto.getIdPlaylist(), musica);
        usuarioService.salvar(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/desfavoritar")
    public ResponseEntity<Void> desfavoritarMusica(@PathVariable UUID id, @RequestBody FavoritarMusicaDto dto) throws Exception {
        Usuario usuario = usuarioService.buscarPorId(id);
        Musica musica = musicaService.buscarPorId(dto.getIdMusica());
        usuario.desfavoritarMusica(dto.getIdPlaylist(), musica);
        usuarioService.salvar(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> buscarTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        List<UsuarioDto> usuariosDto = usuarios.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(usuariosDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable UUID id) throws Exception {
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioDto usuarioDto = UsuarioMapper.toDto(usuario);
        return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable UUID id, @RequestBody @Valid UsuarioDto dto) throws Exception {
        UsuarioDto atualizado = usuarioService.atualizarUsuario(id, dto);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) throws Exception {
        usuarioService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
