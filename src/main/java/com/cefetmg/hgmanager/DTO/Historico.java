package com.cefetmg.hgmanager.DTO;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.*;
import java.util.List;

public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Reserva> reservas;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfessor", nullable = false)
    private Professor professor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Reserva> getReserva() {
        return reservas;
    }

    public void setReserva(List<Reserva> reserva) {
        this.reservas = reserva;
    }

    public void AddReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
