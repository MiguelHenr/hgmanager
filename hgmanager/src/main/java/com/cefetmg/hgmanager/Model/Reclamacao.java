package com.cefetmg.hgmanager.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Reclamacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;
    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    // constructors
    public Reclamacao(){}

    public Reclamacao(Long id, String comentario, Date data, Reserva reserva) {
        setId(id);
        setComentario(comentario);
        setData(data);
        setReserva(reserva);
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getComentario() {
        return comentario;
    }

    public Date getData() {
        return data;
    }

    public Reserva getReserva() {
        return reserva;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

}
