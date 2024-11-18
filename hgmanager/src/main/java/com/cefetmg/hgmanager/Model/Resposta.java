package com.cefetmg.hgmanager.Model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;
    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_reclamacao", nullable = false)
    private Reclamacao reclamacao;

    // constructors
    public Resposta() {}

    public Resposta(Long id, String comentario, Date data, Usuario usuario, Reclamacao reclamacao) {

        setId(id);
        setComentario(comentario);
        setData(data);
        setUsuario(usuario);
        setReclamacao(reclamacao);

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

    public Usuario getUsuario() {
        return usuario;
    }

    public Reclamacao getReclamacao() {
        return reclamacao;
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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setReclamacao(Reclamacao reclamacao) {
        this.reclamacao = reclamacao;
    }

}
