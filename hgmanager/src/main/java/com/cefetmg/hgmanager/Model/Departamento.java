package com.cefetmg.hgmanager.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefone;
    private String nome;
    private String email;
    private String campus;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "departamento")
    private List<Usuario> usuarios;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "departamento")
    private List<Recurso> recursos;

    // constructors
    public Departamento() {}

    public Departamento(String telefone, String nome, String email, String campus) {

        setTelefone(telefone);
        setNome(nome);
        setEmail(email);
        setCampus(campus);
        setUsuarios(null);
        setRecursos(null);

    }

    public Departamento(String telefone, String nome, String email, String campus,
                        List<Usuario> usuarios, List<Recurso> recursos) {

        this(telefone, nome, email, campus);
        setUsuarios(usuarios);
        setRecursos(recursos);

    }

    // getters
    public Long getId() {
        return id;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCampus() {
        return campus;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    // adders
    public void AddUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void AddRecurso(Recurso recurso) {
        this.recursos.add(recurso);
    }

}
