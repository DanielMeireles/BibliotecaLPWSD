/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.util.Relatorio;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
    
    private String relatorio;
    
    private Map<String, String> tipos;

    public RelatorioBean() {
        tipos = new HashMap<String, String>();
        tipos.put("Relatório de Livros/Exemplares", "Relatorio_01");
        tipos.put("Relatório de Livros Emprestados", "Relatorio_02");
        tipos.put("Relatório de Livros Reservados", "Relatorio_03");
        tipos.put("Relatório de Usuários Cadastrados", "Relatorio_04");
        tipos.put("Relatório de Livros com Atraso na Devolução", "Relatorio_05");
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public void setTipos(Map<String, String> tipos) {
        this.tipos = tipos;
    }
    
    public void gerarRelatorio() {
        Relatorio relatorio = new Relatorio();
        relatorio.getRelatorio(this.relatorio);
    }
    
}