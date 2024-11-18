package com.cefetmg.hgmanager.Model;

import com.cefetmg.hgmanager.Model.Enum.Estado;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Punicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date inicio;
    private Date fim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario professor;

    // constructors
    public Punicao() {}

    public Punicao(Long id, Date inicio, Date fim, Usuario professor) {
        setId(id);
        setInicio(inicio);
        setFim(fim);
        setProfessor(professor);
    }

    // getters
    public Long getId() {
        return id;
    }

    public Date getInicio() {
        return inicio;
    }

    public Date getFim() {
        return fim;
    }

    public Usuario getProfessor() {
        return professor;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

}
