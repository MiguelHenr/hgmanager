package com.cefetmg.hgmanager.Model;

import com.cefetmg.hgmanager.Model.Enum.Cargo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String foto;

    @Enumerated(EnumType.STRING)
    private Cargo tipoUsuario;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    // constructors
    public Usuario() {}

    public Usuario(String nome, String email, String cpf, String foto, 
                   Cargo tipoUsuario, Departamento departamento) {

        setNome(nome);
        setEmail(email);
        setCpf(cpf);
        setFoto(foto);
        setTipoUsuario(tipoUsuario);
        setDepartamento(departamento);

    }

    // getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFoto() {
        return foto;
    }

    public Cargo getTipoUsuario() {
        return tipoUsuario;
    }

    public String getTipoUsuarioAsString(){return tipoUsuario.toString();}

    public Departamento getDepartamento() {
        return departamento;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTipoUsuario(Cargo tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
