/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.EmprestimoDAO;
import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class DashboardBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<Reserva> reservas;
    private BarChartModel livrosReservados;
    private List<Emprestimo> emprestimos;
    private BarChartModel livrosEmprestados;

    //construtor
    public DashboardBean() {
        reservas = new ReservaDAO().buscarTodas();
        emprestimos = new EmprestimoDAO().buscarTodas();
    }
    
    @PostConstruct
    public void init() {
        criarLivrosReservados();
        criarLivrosEmprestados();
    }
    
    public void criarLivrosReservados() {
        livrosReservados = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        
        barDataSet.setLabel("Quantidade de Livros Reservados por Mês ");
        
        Calendar data1 = Calendar.getInstance();
        data1.setTime(new Date());
        data1.set(Calendar.DAY_OF_MONTH, 1);
        data1.set(Calendar.MONTH, data1.get(Calendar.MONTH)-3);
        
        Calendar data2 = Calendar.getInstance();
        data2.setTime(new Date());
        data2.set(Calendar.DAY_OF_MONTH, 1);
        data2.set(Calendar.MONTH, data2.get(Calendar.MONTH)-2);
        
        Calendar data3 = Calendar.getInstance();
        data3.setTime(new Date());
        data3.set(Calendar.DAY_OF_MONTH, 1);
        data3.set(Calendar.MONTH, data3.get(Calendar.MONTH)-1);
        
        Calendar data4 = Calendar.getInstance();
        data4.setTime(new Date());
        data4.set(Calendar.DAY_OF_MONTH, 1);
        
        int mes1 = 0;
        int mes2 = 0;
        int mes3 = 0;
        
        for(Reserva r: reservas){
            Calendar reserva = Calendar.getInstance();
            reserva.setTime(r.getDataReserva());
            if((reserva.equals(data1) || reserva.after(data1)) && reserva.before(data2)) {
                mes1++;
            } else if((reserva.equals(data2) || reserva.after(data2)) && reserva.before(data3)) {
                mes2++;
            } else if((reserva.equals(data3) || reserva.after(data3)) && reserva.before(data4)) {
                mes3++;
            }
        }
         
        List<Number> values = new ArrayList<>();
        values.add(mes1);
        values.add(mes2);
        values.add(mes3);
        barDataSet.setData(values);
         
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add(mes(data1.get(Calendar.MONTH)));
        labels.add(mes(data2.get(Calendar.MONTH)));
        labels.add(mes(data3.get(Calendar.MONTH)));
        data.setLabels(labels);
        livrosReservados.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gráfico de Livros Reservados nos 3 Últimos Meses");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        livrosReservados.setOptions(options);
    }
    
    public void criarLivrosEmprestados() {
        livrosEmprestados = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        
        barDataSet.setLabel("Quantidade de Livros Emprestados por Mês ");
        
        Calendar data1 = Calendar.getInstance();
        data1.setTime(new Date());
        data1.set(Calendar.DAY_OF_MONTH, 1);
        data1.set(Calendar.MONTH, data1.get(Calendar.MONTH)-3);
        
        Calendar data2 = Calendar.getInstance();
        data2.setTime(new Date());
        data2.set(Calendar.DAY_OF_MONTH, 1);
        data2.set(Calendar.MONTH, data2.get(Calendar.MONTH)-2);
        
        Calendar data3 = Calendar.getInstance();
        data3.setTime(new Date());
        data3.set(Calendar.DAY_OF_MONTH, 1);
        data3.set(Calendar.MONTH, data3.get(Calendar.MONTH)-1);
        
        Calendar data4 = Calendar.getInstance();
        data4.setTime(new Date());
        data4.set(Calendar.DAY_OF_MONTH, 1);
        
        int mes1 = 0;
        int mes2 = 0;
        int mes3 = 0;
        
        for(Emprestimo e: emprestimos){
            Calendar emprestimo = Calendar.getInstance();
            emprestimo.setTime(e.getDataEmprestimo());
            if((emprestimo.equals(data1) || emprestimo.after(data1)) && emprestimo.before(data2)) {
                mes1++;
            } else if((emprestimo.equals(data2) || emprestimo.after(data2)) && emprestimo.before(data3)) {
                mes2++;
            } else if((emprestimo.equals(data3) || emprestimo.after(data3)) && emprestimo.before(data4)) {
                mes3++;
            }
        }
         
        List<Number> values = new ArrayList<>();
        values.add(mes1);
        values.add(mes2);
        values.add(mes3);
        barDataSet.setData(values);
         
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add(mes(data1.get(Calendar.MONTH)));
        labels.add(mes(data2.get(Calendar.MONTH)));
        labels.add(mes(data3.get(Calendar.MONTH)));
        data.setLabels(labels);
        livrosEmprestados.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gráfico de Livros Emprestados nos 3 Últimos Meses");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        livrosEmprestados.setOptions(options);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public BarChartModel getLivrosReservados() {
        return livrosReservados;
    }

    public void setLivrosReservados(BarChartModel livrosReservados) {
        this.livrosReservados = livrosReservados;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public BarChartModel getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void setLivrosEmprestados(BarChartModel livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }
    
    public String mes(int m) {
        switch(m) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7: 
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
            default:
                return "";
        }
    }
    
}