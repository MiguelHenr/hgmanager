package com.cefetmg.hgmanager.Service;

import com.cefetmg.hgmanager.Model.Resposta;
import com.cefetmg.hgmanager.Model.Usuario;
import com.cefetmg.hgmanager.Repository.RespostaRepository;
import com.cefetmg.hgmanager.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;



    public Usuario inserir(Usuario usuario) {
        try {
            repository.save(usuario);
            return usuario;
        }catch(Exception e){
            throw new NullPointerException();
        }
    }


    public boolean deletar(Usuario usuario) {
        try {
            repository.delete(usuario);
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

    public Usuario atualizar(Usuario usuario) {
        return repository.save(usuario);
    }


    public boolean encontrarPorID(Long id) {
        return repository.existsById(id);
    }

    public List<Usuario> listar() {
        try{
        return repository.findAll();
        }catch(Exception e){
            throw new NullPointerException();
        }
    }
}
