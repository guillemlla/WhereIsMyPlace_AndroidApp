package com.example.guillemllados.bibliotecamaterial;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by guillemllados on 17/11/16.
 */

public class Charts {

    private boolean isFirstuse;

    static Charts charts;

    private PieChartView chartGeneral;
    private PieChartView chartP1;
    private PieChartView chartP2;


    public void setChartGeneral(PieChartView chartGeneral) {this.chartGeneral = chartGeneral;}
    public void setChartP1(PieChartView chartP1) {this.chartP1 = chartP1;}
    public void setChartP2(PieChartView chartP2) {this.chartP2 = chartP2;}

    public Charts(){
        isFirstuse=true;
    }

    public static Charts getChart(){
        if(charts== null){
            charts = new Charts();
        }
        return charts;

    }

    public void animChartGeneral(){

        if(isFirstuse){
            chartGeneral.setAlpha(0f);
            chartGeneral.animate()
                    .rotation(360)
                    .setDuration(2000)
                    .setStartDelay(2000);
            chartGeneral.animate()
                    .alpha(1)
                    .setDuration(1000)
                    .setStartDelay(2000);

        }else{
            animChart(chartGeneral);
        }
        isFirstuse = false;


    }

    public void animChart(PieChartView chart){

        if(chart.getRotation()==0){
            chart.setAlpha(0f);
            chart.animate()
                    .rotation(360)
                    .setDuration(2000)
                    .setStartDelay(0);

            chart.animate()
                    .alpha(1)
                    .setDuration(1000)
                    .setStartDelay(0);

        }else{
            chart.setAlpha(0f);
            chart.animate()
                    .rotation(0)
                    .setDuration(2000)
                    .setStartDelay(0);

            chart.animate()
                    .alpha(1)
                    .setDuration(1000)
                    .setStartDelay(0);
        }

    }

    public void animChartsP1(){

        animChart(chartP1);

    }
    public void animChartsP2(){
        animChart(chartP2);

    }

    public PieChartView getChartGeneral() {
        return chartGeneral;
    }
    public PieChartView getChartP1() {
        return chartP1;
    }
    public PieChartView getChartP2() {return chartP2;}

    public void UpdateChartGeneral(ArrayList<Sensor> llistaSensors){

        int lliures =0;
        int ocupats =0;

        for (Sensor s: llistaSensors) {
            if(s.getEstat().equals("0")){
                lliures++;
            }else {
                ocupats++;
            }

        }

        float perOcupats =0;
        float perLliures =0;
        if(lliures!=0 || ocupats!=0 ){
            perOcupats = ocupats*100/(ocupats+lliures);
            perLliures = lliures*100/(ocupats+lliures);
        }else{
            perLliures = 1;
            perOcupats = 1;
        }

        List<SliceValue> values = new ArrayList<SliceValue>();
        PieChartData data = new PieChartData();

        SliceValue sliceValue = new SliceValue(perOcupats , ChartUtils.COLOR_RED);
        sliceValue.setLabel((int)perOcupats+"%");
        values.add(sliceValue);
        sliceValue = new SliceValue(perLliures, ChartUtils.COLOR_GREEN);
        sliceValue.setLabel((int)perLliures+"%");
        values.add(sliceValue);


        data.setValues(values).setHasLabels(true);
        data.setSlicesSpacing(5);

        getChartGeneral().setPieChartData(data);

        animChartGeneral();
    }

    public void UpdateChartP1(int[] dades){

        int ocupats = dades[0];
        int lliures = dades[1];


        float perOcupats =0;
        float perLliures =0;
        if(lliures!=0 || ocupats!=0 ){
            perOcupats = ocupats*100/(ocupats+lliures);
            perLliures = lliures*100/(ocupats+lliures);
        }else{
            perLliures = 1;
            perOcupats = 1;
        }

        PieChartData data = new PieChartData();

        List<SliceValue> valuesTaules = new ArrayList<SliceValue>();

        SliceValue sliceValue1 = new SliceValue(perOcupats , ChartUtils.COLOR_RED);
        sliceValue1.setLabel((int)perOcupats+"%");
        valuesTaules.add(sliceValue1);
        sliceValue1 = new SliceValue(perLliures, ChartUtils.COLOR_GREEN);
        sliceValue1.setLabel((int)perLliures+"%");
        valuesTaules.add(sliceValue1);

        data.setValues(valuesTaules).setHasLabels(true);
        data.setSlicesSpacing(5);

        getChartP1().setPieChartData(data);

        animChartsP1();

    }

    public void UpdateChartP2(int[] dades){


        int ocupats = dades[0];
        int lliures = dades[1];


        float perOcupats =0;
        float perLliures =0;
        if(lliures!=0 || ocupats!=0 ){
            perOcupats = ocupats*100/(ocupats+lliures);
            perLliures = lliures*100/(ocupats+lliures);
        }else{
            perLliures = 1;
            perOcupats = 1;
        }

        PieChartData data = new PieChartData();

        List<SliceValue> valuesTaules = new ArrayList<SliceValue>();

        SliceValue sliceValue1 = new SliceValue(perOcupats , ChartUtils.COLOR_RED);
        sliceValue1.setLabel((int)perOcupats+"%");
        valuesTaules.add(sliceValue1);
        sliceValue1 = new SliceValue(perLliures, ChartUtils.COLOR_GREEN);
        sliceValue1.setLabel((int)perLliures+"%");
        valuesTaules.add(sliceValue1);

        data.setValues(valuesTaules).setHasLabels(true);
        data.setSlicesSpacing(5);

        getChartP2().setPieChartData(data);

        animChartsP2();

    }
}
