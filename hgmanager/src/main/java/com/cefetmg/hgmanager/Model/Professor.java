package com.cefetmg.hgmanager.Model;

import java.util.List;

public class Professor extends Usuario{
    private List<Punicao> punicao;
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
