package com.cefetmg.hgmanager.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Reserva {

    private Professor professor;


    @ManyToOne(fetch = FetchType.EAGER)
    private List<Recurso> recurso;

    private Date incio;
    private Date fim;


    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Recurso> getRecurso() {
        return recurso;
    }

    public void setRecurso(List<Recurso> recurso) {
        this.recurso = recurso;
    }

    public void AddRecurso(Recurso recurso) {
        this.recurso.add(recurso);
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


}
