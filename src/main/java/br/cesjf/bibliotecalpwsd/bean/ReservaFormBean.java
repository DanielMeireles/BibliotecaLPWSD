/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import java.io.Serializable;
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
public class ReservaFormBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Reserva reserva;
    private int id;

    //construtor
    public ReservaFormBean() {
    }
    
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (id > 0) {
            reserva = new ReservaDAO().buscar(id);
        } else {
            reserva = new Reserva();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new ReservaDAO().persistir(reserva)));
    }
    
    public void exclude(ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new ReservaDAO().remover(reserva)));
    }

    //getters and setters
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void clear() {
        reserva = new Reserva();
        id = 0;
    }
    
    public boolean isNew() {
        return reserva == null || reserva.getId() == null || reserva.getId() == 0;
    }

}