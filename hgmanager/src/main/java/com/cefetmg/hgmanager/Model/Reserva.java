package com.cefetmg.hgmanager.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario professor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_recurso", nullable = false)
    private Recurso recurso;

    private Date incio;
    private Date fim;

    // constructors
    public Reserva() {}

    public Reserva(Long id, Usuario professor, Recurso recurso, Date inicio, Date fim) {

        setId(id);
        setProfessor(professor);
        setRecurso(recurso);
        setIncio(inicio);
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

    public Date getIncio() {
        return incio;
    }

    public Date getFim() {
        return fim;
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

    public void setIncio(Date incio) {
        this.incio = incio;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

}
