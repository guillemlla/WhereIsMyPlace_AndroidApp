package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guillemllados.bibliotecamaterial.Log;
import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetLogData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class FragmentHistorial extends Fragment {

    private View view;
    private ColumnChartView chart;
    private ColumnChartData data;
    private int estatChart;//0=any 1=mes 2=semana
    private ImageButton btDerecha,btIzquierda;
    private ArrayList<Log> logs;
    private TextView tvEstat;
    private String username;

    public FragmentHistorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_fragment_historial, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 41, 0, 0);
        params.addRule(RelativeLayout.BELOW, R.id.tabs);
        view.setLayoutParams(params);
        estatChart = 0;

        username = getArguments().getString("username");

        btDerecha = (ImageButton)view.findViewById(R.id.btDerecha);
        btIzquierda = (ImageButton)view.findViewById(R.id.btIzquierda);
        tvEstat = (TextView)view.findViewById(R.id.tvEstat);

        btIzquierda.animate().alpha(0);

        butonListeners();

        chart = (ColumnChartView) view.findViewById(R.id.chartHistorial);
        chart.setHorizontalScrollBarEnabled(true);
        estatChart=0;
        logdata();
        return view;
    }

    public void setDefaultData(){
        int numSubcolumns = 1;
        int numColumns = 8;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (true) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (true) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);
    }
    public void setChartDataEstat0(ArrayList<Log> logs){
        ColumnChartData data;
        Date d;
        int exiteix=0;
        int[] j= null;

        ArrayList<int[]> anys = new ArrayList<>(); //0-> any 1->numLogs
        //Ordena els logs per any
        for (Log l: logs){
            d= l.getDate();
            for(int[] i :anys){
                if(i[0] == l.getYear()){
                    exiteix=1;
                    j = i;
                }
            }
            if(exiteix==1){
                j[1]++;
                exiteix=0;
            }else{
                int[] any = new int[2];
                any[0] = l.getYear();
                any[1] = 1;
                anys.add(any);
            }
        }
        //Afegeix Valors
        int numSubcolumns = 1;
        int numColumns = anys.size();
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<AxisValue> axisValues= new ArrayList<>();

        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            String s = String.valueOf(anys.get(i)[0]);
            axisValues.add(new AxisValue(i,s.toCharArray()));
            values = new ArrayList<SubcolumnValue>();
            for (int k = 0; k < numSubcolumns; ++k) {
                values.add(new SubcolumnValue(anys.get(i)[1], ChartUtils.pickColor()));

            }
            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
        }
        data = new ColumnChartData(columns);

        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);


        ArrayList<AxisValue> vacio = new ArrayList<>();
        vacio.add(new AxisValue(12));
        axisY.setValues(vacio);

        axisX.setName("Anys");
        axisY.setName("Accesos a la Biblioteca");

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        chart.setColumnChartData(data);
        chart.setHorizontalScrollBarEnabled(true);
        chart.setNestedScrollingEnabled(true);
        chart.setScrollEnabled(true);


    }
    public void setChartDataEstat1(ArrayList<Log> logs){

        ColumnChartData data;
        Date d;
        int exiteix=0;
        int[] j= null;
        List<Column> columns = new ArrayList<Column>();
        ArrayList<int[]/* 0->any 1->mes 2->numdeLogs */> mesos = new ArrayList<>();
        //Afegir Dades
        for(Log l: logs){
            d = l.getDate();

            for(int[] mes: mesos){
                if(mes[1]==l.getMonth() & mes[0]==l.getYear()){
                    exiteix = 1;
                    j = mes;

                }
            }
            if(exiteix==1){
                j[2]++;
                exiteix=0;
            }else{
                int[] mes = new int[3];
                mes[0] = l.getYear();
                mes[1] = l.getMonth();
                mes[2]= 1;
                mesos.add(mes);
            }
        }
        //Afegeix Valors
        int numSubcolumns = 1;
        int numColumns = mesos.size();
        ArrayList<AxisValue> axisValues = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {
            String s = mesos.get(i)[1] + "/"+mesos.get(i)[0];
            axisValues.add(new AxisValue(i,s.toCharArray()));

            values = new ArrayList<SubcolumnValue>();
            for (int k = 0; k < numSubcolumns; ++k) {
                values.add(new SubcolumnValue(mesos.get(i)[2], ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
        }
        data = new ColumnChartData(columns);
        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);
        axisY.setHasTiltedLabels(false);

        ArrayList<AxisValue> vacio = new ArrayList<>();
        vacio.add(new AxisValue(12));
        axisY.setValues(vacio);

        axisX.setName("Messos");
        axisY.setName("Numero d'accesos a la biblioteca");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setColumnChartData(data);

    }
    public void setChartDataEstat2(ArrayList<Log> logs){

        ColumnChartData data;
        Date d;
        int exiteix=0;
        int[] j= null;
        List<Column> columns = new ArrayList<Column>();
        ArrayList<int[]/* 0->any 1->mes 2->dia 3->numdeLogs */> dies = new ArrayList<>();
        //Afegir Dades
        for(Log l: logs){
            d = l.getDate();

            for(int[] dia: dies){
                if(dia[1]==l.getMonth() & dia[0]==l.getYear() & dia[2]==l.getDay()){
                    exiteix = 1;
                    j = dia;

                }
            }
            if(exiteix==1){
                j[3]++;
                exiteix=0;
            }else{
                int[] dia = new int[4];
                dia[0] = l.getYear();
                dia[1] = l.getMonth();
                dia[2]= l.getDay();
                dia[3]=1;
                dies.add(dia);
            }
        }
        //Afegeix Valors
        int numSubcolumns = 1;
        int numColumns = dies.size();

        List<SubcolumnValue> values;
        ArrayList<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < numColumns; ++i) {

            int anyInt = dies.get(i)[0];
            String any = String.valueOf(dies.get(i)[0]);
            String s = dies.get(i)[2] + "/" +dies.get(i)[1] + "/"+dies.get(i)[0];
            axisValues.add(new AxisValue(i,s.toCharArray()));
            values = new ArrayList<SubcolumnValue>();
            for (int k = 0; k < numSubcolumns; ++k) {
                values.add(new SubcolumnValue(dies.get(i)[3], ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(true);
            columns.add(column);
        }
        data = new ColumnChartData(columns);
        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);
        axisY.setHasTiltedLabels(false);

        ArrayList<AxisValue> vacio = new ArrayList<>();
        vacio.add(new AxisValue(12));
        axisY.setValues(vacio);

        axisX.setName("Dies");
        axisY.setName("Numero d'accesos a la biblioteca");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setColumnChartData(data);

    }

    public void logdata(){

        Object[] params = new Object[1];
        params[0] = username;

        GetLogData dateGetter = new GetLogData(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {

                logs = (ArrayList<Log>)output;
                switch (estatChart){
                    case 0: setChartDataEstat0(logs);break;
                    case 1: setChartDataEstat1(logs);break;
                    case 2: setChartDataEstat2(logs);break;
                    case -1: setDefaultData();break;
                }

            }
        });

        dateGetter.execute(params);

    }

    public void butonListeners(){

        btDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (estatChart){
                    case 0:{

                        setChartDataEstat1(logs);
                        tvEstat.setText("Mesos");
                        btIzquierda.animate().alpha(1).setDuration(100);
                        estatChart++;
                        break;
                    }
                    case 1:{
                        estatChart++;
                        setChartDataEstat2(logs);
                        tvEstat.setText("Dies");
                        btDerecha.animate().alpha(0).setDuration(100);
                        break;
                    }
                }
            }
        });
        btIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (estatChart){
                    case 1:{
                        estatChart--;
                        setChartDataEstat0(logs);
                        tvEstat.setText("Anys");
                        btIzquierda.animate().alpha(0).setDuration(100);
                        break;
                    }
                    case 2:{
                        estatChart--;
                        setChartDataEstat1(logs);
                        tvEstat.setText("Mesos");
                        btDerecha.animate().alpha(1).setDuration(100);
                        break;
                    }
                }
            }
        });


    }


}