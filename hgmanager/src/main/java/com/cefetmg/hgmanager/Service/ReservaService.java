package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Recurso;
import com.cefetmg.hgmanager.Model.Reserva;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.RecursoRepository;
import com.cefetmg.hgmanager.Repository.ReservaRepository;
import com.cefetmg.hgmanager.Repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public Reserva inserir(Reserva reserva) {
        try {
            repository.save(reserva);
            return reserva;
        }catch(Exception e){
                throw new NullPointerException();
            }
        }


    public boolean deletar(Reserva reserva) {
        try {
            repository.delete(reserva);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletarPorID(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Reserva atualizar(Reserva reserva) {
        return repository.save(reserva);
    }


    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);
    }

    public List<Reserva> listar() {
        try{
            return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }

}
