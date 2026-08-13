package com.othavio.usuario.business;

import com.othavio.usuario.business.converter.UsuarioConverter;
import com.othavio.usuario.business.dto.UsuarioDto;
import com.othavio.usuario.infrastructure.entity.Usuario;
import com.othavio.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
