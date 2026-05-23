package com.api.usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id_usuario;

    @Column(name = "run", nullable = false, unique = true, length = 12)
    private String run;

    @Column(name = "p_nombre",nullable = false, length = 25)
    private String p_nombre;

    @Column(name = "s_nombre", length = 25)
    private String s_nombre;

    @Column(name = "a_paterno", nullable = false, length = 25)
    private String a_paterno;

    @Column(name = "a_materno", nullable = false, length = 25)
    private String a_materno;

    @Column(name = "mail", nullable = false, unique = true, length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "Direccion_id_direccion", nullable = false)
    private Direccion direccion_usuario;
}