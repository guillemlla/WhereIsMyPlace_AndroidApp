package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsPlantes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.guillemllados.bibliotecamaterial.Charts;
import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.Sensor;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetSensorsData;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.PieChartView;


public class FragmentPlanta2 extends Fragment {

    private Charts charts;
    private boolean chartCreated;
    private View view;
    private boolean estatTaules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view =  inflater.inflate(R.layout.fragment_fragment_planta2, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0,41,0,0);
        params.addRule(RelativeLayout.BELOW,R.id.tabs);
        view.setLayoutParams(params);

        charts = Charts.getChart();
        charts.setChartP2( (PieChartView) view.findViewById(R.id.chartP2));

        actualitzaGrafic();
        int dummyDades[] = {0,0};
        charts.UpdateChartP2(dummyDades);
        updateTextView(dummyDades);
        //ocupats pos 0 lliures pos1

        chartCreated = true;


        return view;
    }
    //fa els updates dels textVews en funcio de quin boto esta en aquell moment
    public void updateTextView(int [] resultat){
        //ocupats pos 0 lliures pos1
        ((TextView) view.findViewById(R.id.TVP2Total)).setText("Bucs estudi totals: "+(resultat[0]+resultat[1]));
        ((TextView) view.findViewById(R.id.TVP2Lliures)).setText("Bucs estudi lliures: "+resultat[1]);
        ((TextView) view.findViewById(R.id.TVP2Ocupades)).setText("Bucs estudi ocupats: "+resultat[0]);
    }

    //analitza la llista de sensors donada i retorna un aray amb la 1a pos nombre de bucs ocupats i la 2a els lliures
    public int [] analitzaDades(ArrayList<Sensor> sensors){

        int resultat[] = new int[2];//ocupats pos 0 lliures pos1

            int bucsLliures = 0;
            int bucsOcupats = 0;
            for (Sensor s: sensors) {

                if(s.getEstat().equals("0")){
                    bucsLliures++;
                }else {
                    bucsOcupats++;
                }
                resultat[0] = bucsOcupats;
                resultat[1] = bucsLliures;

            }


        return resultat;

    }

    //instancia una nova classe GetSensorsData, executa la tasca actualitzant els grafics i els texts
    public void actualitzaGrafic(){
        Object[] parametres = new Object[1];
        String param1 = "2";
        parametres[0] = param1; //pis a buscar -1 vol dir en tots
        GetSensorsData netTask = new GetSensorsData(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                if(output!= null) {
                    ArrayList<Sensor> llistaSensors = (ArrayList) output;
                    int dadesAnalitzades[] = analitzaDades(llistaSensors);
                    while (!chartCreated) ;
                    charts.UpdateChartP2(dadesAnalitzades);
                    updateTextView(dadesAnalitzades);
                }
            }
        });
        netTask.execute(parametres);

    }

}