package com.othavio.usuario.business;

import com.othavio.usuario.business.converter.UsuarioConverter;
import com.othavio.usuario.business.dto.UsuarioDto;
import com.othavio.usuario.infrastructure.entity.Usuario;
import com.othavio.usuario.infrastructure.exceptions.ConflictException;
import com.othavio.usuario.infrastructure.repository.UsuarioRepository;
import com.othavio.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto) {
        emailExiste(usuarioDto.getEmail());
        usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Email não encontrado " + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDto atualizaDadosUsuario(String token, UsuarioDto dto) {
       String email = jwtUtil.extrairEmaildoToken(token.substring(7));
       dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

       Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
               new UsernameNotFoundException("Email não localizado"));

       Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

       return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
