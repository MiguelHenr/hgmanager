package com.cefetmg.hgmanager.Model;



import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String foto;
    private String tipoUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    // constructors
    public Usuario() {}

    public Usuario(Long id, String nome, String email, String senha, String cpf,
                   String foto, String tipoUsuario, Departamento departamento) {

        setId(id);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setCpf(cpf);
        setFoto(foto);
        setTipoUsuario(tipoUsuario);
        setDepartamento(departamento);

    }

    // getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFoto() {
        return foto;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

}
