package com.cefetmg.hgmanager.Model;

import com.cefetmg.hgmanager.Model.Enum.Estado;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String marca;
    private String descricao;
    private String codigo;

    @Enumerated(EnumType.STRING)
  //  @Type(PostgreSQLEnumType.class)
    private Estado estado;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    // constructors
    public Recurso() {}

    public Recurso(String marca, String descricao, Estado estado, Departamento departamento) {

        setMarca(marca);
        setDescricao(descricao);
        setEstado(estado);
        setDepartamento(departamento);

    }

    // getters
    public Departamento getDepartamento() {
        return departamento;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public Long getId() {
        return Id;
    }

    // setters
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public void setEstado(String estado) {
        if(estado.equals("NOVO"))
            this.estado = Estado.NOVO;
        if(estado.equals("CONSERVADO"))
            this.estado = Estado.CONSERVADO;
        if(estado.equals("VELHO"))
            this.estado = Estado.VELHO;
        if(estado.equals("ESTRAGADO"))
            this.estado = Estado.ESTRAGADO;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}