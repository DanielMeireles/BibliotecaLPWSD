/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import java.io.Serializable;
import java.util.List;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class AcervoListBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List livros;

    //construtor
    public AcervoListBean() {
        livros = new LivroDAO().buscarTodas();
    }

    //getters and setters
    public List getLivros() {
        return livros;
    }

    public void setLivros(List livros) {
        this.livros = livros;
    }
    
}