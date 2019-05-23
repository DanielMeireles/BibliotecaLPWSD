/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.dao.AutorDAO;
import br.cesjf.bibliotecalpwsd.dao.EditoraDAO;
import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.model.Editora;
import br.cesjf.bibliotecalpwsd.model.Livro;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class LivroFormBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Livro livro;
    private List assuntos;
    private List autores;
    private List<Editora> editoras;
    private int id;

    //construtor
    public LivroFormBean() {
        assuntos = new AssuntoDAO().buscarTodas();
        autores = new AutorDAO().buscarTodas();
        editoras = new EditoraDAO().buscarTodas();
    }
    
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (id > 0) {
            livro = new LivroDAO().buscar(id);
        } else {
            livro = new Livro();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new LivroDAO().persistir(livro)));
    }
    
    public void exclude(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new LivroDAO().remover(livro)));
    }

    //getters and setters
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getAssuntos() {
        Collections.sort(assuntos);
        return assuntos;
    }

    public void setAssuntos(List assuntos) {
        this.assuntos = assuntos;
    }

    public List getAutores() {
        Collections.sort(autores);
        return autores;
    }

    public void setAutores(List autores) {
        this.autores = autores;
    }

    public List getEditoras() {
        Collections.sort(editoras);
        return editoras;
    }

    public void setEditoras(List editoras) {
        this.editoras = editoras;
    }
    
    public void clear() {
        livro = new Livro();
        id = 0;
    }
    
    public boolean isNew() {
        return livro == null || livro.getId() == null || livro.getId() == 0;
    }

}