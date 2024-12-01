package com.cefetmg.hgmanager.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import com.cefetmg.hgmanager.Model.Enum.Status;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario professor;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    private Date inicio;
    private Date fim;

    @Enumerated(EnumType.STRING)
    private Status status;
  
    // constructors
    public Reserva() {}

    public Reserva(Usuario professor, Recurso recurso, Date inicio, Date fim, Status status) {

        setProfessor(professor);
        setRecurso(recurso);
        setInicio(inicio);
        setFim(fim);
    }

    // getters
    public Long getId() {
        return id;
    }

    public Usuario getProfessor() {
        return this.professor;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public Date getInicio() {
        return inicio;
    }

    public Date getFim() {
        return fim;
    }

    public Status getStatus() {
        return status;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
  
}
