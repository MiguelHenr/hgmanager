package com.cefetmg.hgmanager.Model;

import jakarta.persistence.*;
import java.util.List;


@Entity
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Reserva> reservas;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProfessor", nullable = false)
    private Professor professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
