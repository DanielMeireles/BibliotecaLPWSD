/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.reports.Relatorio;
import java.io.Serializable;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class RelatorioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public void gerarRelatorio01() {
        Relatorio relatorio = new Relatorio();
        relatorio.getRelatorio("Relatorio_01");
    }
    
}