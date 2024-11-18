package com.cefetmg.hgmanager.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

import com.cefetmg.hgmanager.Model.Enum.Status;

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

    @Enumerated(EnumType.STRING)
    private Status status;


    public Usuario getProfessor() {
        return this.professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Date getIncio() {
        return incio;
    }

    public void setIncio(Date incio) {
        this.incio = incio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
