package com.cefetmg.hgmanager.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Reclamacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;
    private Date data;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "reclamacao")
    List<Resposta> respostas;

    // constructors
    public Reclamacao(){}

    public Reclamacao(String comentario, Date data, Reserva reserva) {

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

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }
}
