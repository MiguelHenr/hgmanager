package com.cefetmg.hgmanager.Model;

import jakarta.persistence.*;

@Entity
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_reclamacao", nullable = false)
    private Reclamacao relamacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Reclamacao getRelamacao() {
        return relamacao;
    }

    public void setRelamacao(Reclamacao relamacao) {
        this.relamacao = relamacao;
    }
}
