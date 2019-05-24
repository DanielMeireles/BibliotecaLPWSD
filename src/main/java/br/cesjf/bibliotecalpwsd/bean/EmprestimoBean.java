/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;


import br.cesjf.bibliotecalpwsd.dao.EmprestimoDAO;
import br.cesjf.bibliotecalpwsd.dao.ExemplarDAO;
import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.ProcessReport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class EmprestimoBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Emprestimo emprestimo;
    private List emprestimos;
    private List<Exemplar> exemplaresPermitidos;
    private List<Usuario> usuariosPermitidos;

    //construtor
    public EmprestimoBean() {
        emprestimos = new EmprestimoDAO().buscarTodas();
        emprestimo = new Emprestimo();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new EmprestimoDAO().persistir(emprestimo));
        emprestimos = new EmprestimoDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        msgScreen(new EmprestimoDAO().remover(emprestimo));
        emprestimos = new EmprestimoDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        emprestimo = new Emprestimo();
    }

    //getters and setters
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public List getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List emprestimos) {
        this.emprestimos = emprestimos;
    }

    public List<Exemplar> getExemplaresPermitidos() {
        exemplaresPermitidos();
        return exemplaresPermitidos;
    }

    public void setExemplaresPermitidos(List<Exemplar> exemplaresPermitidos) {
        this.exemplaresPermitidos = exemplaresPermitidos;
    }

    public List<Usuario> getUsuariosPermitidos() {
        usuariosPermitidos();
        return usuariosPermitidos;
    }

    public void setUsuariosPermitidos(List<Usuario> usuariosPermitidos) {
        this.usuariosPermitidos = usuariosPermitidos;
    }
    
    //Métodos
    private void exemplaresPermitidos() {
        List<Exemplar> exemplares = new ExemplarDAO().buscarTodas();
        List<Reserva> reservas = new ReservaDAO().buscarTodas();
        exemplaresPermitidos = new ArrayList<>();
        exemplaresPermitidos.addAll(exemplares);
        Date hoje = new Date();
        for(Exemplar e: exemplares) {
            for(Emprestimo emp: e.getEmprestimoList()) {
                if(emp.getDataDevolucao() == null){
                    exemplaresPermitidos.remove(e);
                }
            }
            for(Reserva r: reservas) {
                if((r.getDataReserva().equals(hoje) || r.getDataReserva().after(hoje)) &&  (r.getDataDevolucaoPrevista().equals(hoje) || r.getDataDevolucaoPrevista().after(hoje))) {
                    exemplaresPermitidos.remove(e);
                }
            }
        }
        exemplaresPermitidos.add(emprestimo.getIdExemplar());
    }
    
    private void usuariosPermitidos() {
        List<Usuario> usuarios = new UsuarioDAO().buscarTodas();
        usuariosPermitidos = new ArrayList<>();
        for(Usuario u: usuarios) {
            List<Emprestimo> emp = new ArrayList<>();
            for(Emprestimo e: u.getEmprestimoList()) {
                if(e.getDataDevolucao() == null) {
                    emp.add(e);
                }
            }
            if(u.getTipo().equals('C') && emp.size() < 3) {
                usuariosPermitidos.add(u);
            } else if(!u.getTipo().equals('C') && emp.size() < 5) {
                usuariosPermitidos.add(u);
            }
        }
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }
    
}