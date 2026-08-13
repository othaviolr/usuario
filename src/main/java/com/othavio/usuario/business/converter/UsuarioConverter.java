package com.othavio.usuario.business.converter;

import com.othavio.usuario.business.dto.EnderecoDto;
import com.othavio.usuario.business.dto.TelefoneDto;
import com.othavio.usuario.business.dto.UsuarioDto;
import com.othavio.usuario.infrastructure.entity.Endereco;
import com.othavio.usuario.infrastructure.entity.Telefone;
import com.othavio.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDto usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ?
                        paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ?
                        paraListaTelefones(usuarioDTO.getTelefones()) : null)
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecoDTOS) {
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDto enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDto enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDto> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDto telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDto paraUsuarioDTO(Usuario usuarioDTO) {
        return UsuarioDto.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ?
                        paraListaEnderecoDTO(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ?
                        paraListaTelefonesDTO(usuarioDTO.getTelefones()) : null)
                .build();
    }

    public List<EnderecoDto> paraListaEnderecoDTO(List<Endereco> enderecoDTOS) {
        List<EnderecoDto> enderecos = new ArrayList<>();
        for (Endereco enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return enderecos;
    }

    public EnderecoDto paraEnderecoDTO(Endereco endereco) {
        return EnderecoDto.builder()
                //.id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDto> paraListaTelefonesDTO(List<Telefone> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDto paraTelefoneDTO(Telefone telefone) {
        return TelefoneDto.builder()
                //.id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDto usuario, Usuario entity) {
        return Usuario.builder()
                .nome(usuario.getNome() != null ? usuario.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuario.getSenha() != null ? usuario.getSenha() : entity.getSenha())
                .email(usuario.getEmail() != null ? usuario.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }
}