package com.cefetmg.hgmanager.Model;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Professor extends Usuario{

    @OneToMany(fetch = FetchType.EAGER)
    private List<Punicao> punicao;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Historico> historico;

    public List<Punicao> getPunicao() {
        return punicao;
    }

    public void setPunicao(List<Punicao> punicao) {
        this.punicao = punicao;
    }

    public void AddPunicao(Punicao punicao){
        this.punicao.add(punicao);
    }

    public List<Historico> getHistorico() {
        return historico;
    }

    public void setHistorico(List<Historico> historico) {
        this.historico = historico;
    }

    public void AddHistorico(Historico historico){
        this.historico.add(historico);
    }

}
