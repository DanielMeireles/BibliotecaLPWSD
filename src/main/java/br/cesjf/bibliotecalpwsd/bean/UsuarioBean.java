/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.ProcessReport;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class UsuarioBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private List usuarios;
    private Map<String,String> opcoes;

    //construtor
    public UsuarioBean() {
        usuarios = new UsuarioDAO().buscarTodas();
        usuario = new Usuario();
        opcoes = new HashMap<String, String>();
        opcoes.put("Administrador", "A");
        opcoes.put("Professor", "B");
        opcoes.put("Aluno", "C");
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new UsuarioDAO().persistir(usuario)));
        usuarios = new UsuarioDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new UsuarioDAO().remover(usuario)));
        usuarios = new UsuarioDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        usuario = new Usuario();
    }

    //getters and setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List usuarios) {
        this.usuarios = usuarios;
    }
    
    public Map<String, String> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(Map<String, String> opcoes) {
        this.opcoes = opcoes;
    }
    
}