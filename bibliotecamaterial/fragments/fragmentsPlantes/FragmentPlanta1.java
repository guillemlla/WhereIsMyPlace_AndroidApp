package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsPlantes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.guillemllados.bibliotecamaterial.Charts;
import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.Sensor;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetSensorsData;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.PieChartView;


public class FragmentPlanta1 extends Fragment {

    private Charts charts;
    private boolean chartCreated;
    private View view;
    private boolean estatTaules;
    private ToggleButton tbTaules,tbCadires;
    private boolean butonChanging;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view =  inflater.inflate(R.layout.fragment_fragment_planta1, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0,41,0,0);
        params.addRule(RelativeLayout.BELOW,R.id.tabs);
        view.setLayoutParams(params);

        charts = Charts.getChart();
        charts.setChartP1( (PieChartView) view.findViewById(R.id.chartP1));

        togleButtons();



        //ArrayList<ArrayList<Sensor>> llistesSensors = new ArrayList<ArrayList<Sensor>>();

        actualitzaGrafic();
        int dummyDades[] = {0,0};
        charts.UpdateChartP1(dummyDades);
        updateTextView(dummyDades);
        //ocupats pos 0 lliures pos1

        chartCreated = true;


        return view;
    }
    //fa els updates dels textVews en funcio de quin boto esta en aquell moment
    public void updateTextView(int [] resultat){
        //ocupats pos 0 lliures pos1
        if(tbTaules.isChecked()){
            ((TextView) view.findViewById(R.id.TVP1Total)).setText("Taules totals: "+(resultat[0]+resultat[1]));
            ((TextView) view.findViewById(R.id.TVP1Lliures)).setText("Taules lliures: "+resultat[1]);
            ((TextView) view.findViewById(R.id.TVP1Ocupades)).setText("Taules ocupats: "+resultat[0]);
        }else{
            ((TextView) view.findViewById(R.id.TVP1Total)).setText("Llocs totals: "+(resultat[0]+resultat[1]));
            ((TextView) view.findViewById(R.id.TVP1Lliures)).setText("Llocs lliures: "+resultat[1]);
            ((TextView) view.findViewById(R.id.TVP1Ocupades)).setText("LLocs ocupats: "+resultat[0]);
        }
    }

    //rep les dades de tots els sensors i les analitza en funcio del togle button activat i el numTaula del sensor
    //retorna un array on la pos0 es el numero de taules/cadires ocupades i el segon les lliures
    public int [] analitzaDades(ArrayList<Sensor> sensors){

        int resultat[] = new int[2];//ocupats pos 0 lliures pos1


        if(tbCadires.isChecked()){
            int cadiresLliures = 0;
            int cadiresOcupades = 0;
            for (Sensor s: sensors) {

                if(s.getEstat().equals("0")){
                    cadiresLliures++;
                }else {
                    cadiresOcupades++;
                }
                resultat[0] = cadiresOcupades;
                resultat[1] = cadiresLliures;

            }
        }else{
            int numCadiresLliuresaTaula = 0;
            int numTaulesLliures = 0;
            int numTaulesOcupades = 0;
            String numTaula = sensors.get(0).getGrupTaula();
            for (Sensor s: sensors) {
                if(!s.getGrupTaula().equals(numTaula)){
                    if(numCadiresLliuresaTaula == 4){numTaulesLliures++;numCadiresLliuresaTaula =0;}
                    else if(!numTaula.equals("-1")){numTaulesOcupades++; numCadiresLliuresaTaula =0;}
                    numTaula = s.getGrupTaula();
                    if(s.getEstat().equals("0")){numCadiresLliuresaTaula++;}


                }else {
                    if (s.getEstat().equals("0")) {
                        numCadiresLliuresaTaula++;
                    }
                }
            }
            if(numCadiresLliuresaTaula == 4){ numTaulesLliures++; }
            else if(!numTaula.equals("-1")){ numTaulesOcupades++;}

            resultat[0] = numTaulesOcupades;
            resultat[1] = numTaulesLliures;

        }


        return resultat;

    }

    //implementa els listeners i el comportament dels toglebuttons
    public void togleButtons(){

        tbCadires = (ToggleButton)view.findViewById(R.id.tbCadires);
        tbTaules = (ToggleButton)view.findViewById(R.id.tbTaules);

        tbCadires.setChecked(false);
        tbTaules.setChecked(true);
        butonChanging=false;

        tbCadires.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



                if(butonChanging){
                    butonChanging=false;

                }else{
                    if(b){
                        butonChanging=true;
                        tbTaules.setChecked(false);


                    }else{
                        compoundButton.setChecked(true);

                    }
                }

                actualitzaGrafic();


            }
        });

        tbTaules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(butonChanging){
                    butonChanging=false;

                }else{
                    if(b){
                        butonChanging=true;
                        tbCadires.setChecked(false);


                    }else{
                        compoundButton.setChecked(true);
                    }
                }
            }
        });





    }

    //instancia una nova classe GetSensorsData, executa la tasca actualitzant els grafics i els texts
    public void actualitzaGrafic(){
        Object[] parametres = new Object[1];
        String param1 = "1";
        parametres[0] = param1; //pis a buscar -1 vol dir en tots
        GetSensorsData netTask = new GetSensorsData(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                if(output!= null) {
                    ArrayList<Sensor> llistaSensors = (ArrayList) output;
                    int dadesAnalitzades[] = analitzaDades(llistaSensors);
                    while (!chartCreated) ;
                    charts.UpdateChartP1(dadesAnalitzades);
                    updateTextView(dadesAnalitzades);
                }
            }
        });
        netTask.execute(parametres);

    }

}
