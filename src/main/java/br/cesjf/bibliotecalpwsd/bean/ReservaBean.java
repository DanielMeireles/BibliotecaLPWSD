/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

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
import org.primefaces.event.SelectEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class ReservaBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Reserva reserva;
    private List reservas;
    private List<Exemplar> exemplaresPermitidos;
    private List<Usuario> usuariosPermitidos;

    //construtor
    public ReservaBean() {
        reservas = new ReservaDAO().buscarTodas();
        reserva = new Reserva();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new ReservaDAO().persistir(reserva)));
        reservas = new ReservaDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new ReservaDAO().remover(reserva)));
        reservas = new ReservaDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        reserva = new Reserva();
        exemplaresPermitidos = new ArrayList<>();
    }

    //getters and setters
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public List getReservas() {
        return reservas;
    }

    public void setReservas(List reservas) {
        this.reservas = reservas;
    }

    public List<Exemplar> getExemplaresPermitidos() {
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
    
    public void calcularExemplaresPermitidos(SelectEvent event) {
        List<Exemplar> exemplares = new ExemplarDAO().buscarTodas();
        List<Reserva> reservas = new ReservaDAO().buscarTodas();
        exemplaresPermitidos = new ArrayList<>();
        exemplaresPermitidos.addAll(exemplares);
        Date dataReserva = reserva.getDataReserva();
        for(Exemplar e: exemplares) {
            for(Emprestimo emp: e.getEmprestimoList()) {
                if(emp.getDataDevolucao() == null && emp.getDataDevolucaoPrevista().after(dataReserva)){
                    exemplaresPermitidos.remove(e);
                }
            }
            for(Reserva r: reservas) {
                if((r.getDataReserva().equals(dataReserva) || r.getDataReserva().after(dataReserva)) &&  (r.getDataDevolucaoPrevista().equals(dataReserva) || r.getDataDevolucaoPrevista().after(dataReserva))) {
                    exemplaresPermitidos.remove(e);
                }
            }
        }
        exemplaresPermitidos.add(reserva.getIdExemplar());
    }
    
    public void geraEmprestimo(ActionEvent actionEvent) {
        Emprestimo emp = new Emprestimo();
        emp.setIdExemplar(reserva.getIdExemplar());
        emp.setIdUsuario(reserva.getIdUsuario());
        emp.setDataEmprestimo(new Date());
        reserva.setIdEmprestimo(emp);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new ReservaDAO().persistir(reserva)));
        reservas = new ReservaDAO().buscarTodas();
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
    
}