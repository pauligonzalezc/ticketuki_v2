package com.api.usuario.dto;

import com.api.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioResponseDTO {

    private Integer id_usuario;
    private String run;
    private String p_nombre;
    private String s_nombre;
    private String a_paterno;
    private String a_materno;
    private String email;
    private DireccionDTO direccion;

    public static UsuarioResponseDTO mapUsuario(Usuario usuario){
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId_usuario(usuario.getId_usuario());
        dto.setRun(usuario.getRun());
        dto.setP_nombre(usuario.getP_nombre());
        dto.setS_nombre(usuario.getS_nombre());
        dto.setA_paterno(usuario.getA_paterno());
        dto.setA_materno(usuario.getA_materno());
        dto.setEmail(usuario.getEmail());

        if (usuario.getDireccion_usuario() != null) {
            DireccionDTO direDto = new DireccionDTO();
            direDto.setCalle(usuario.getDireccion_usuario().getCalle());
            direDto.setNum_calle(usuario.getDireccion_usuario().getNum_calle());
            direDto.setComuna(usuario.getDireccion_usuario().getComuna());
            direDto.setRegion(usuario.getDireccion_usuario().getRegion());
            dto.setDireccion(direDto);
        }
        return dto;
    }
}
