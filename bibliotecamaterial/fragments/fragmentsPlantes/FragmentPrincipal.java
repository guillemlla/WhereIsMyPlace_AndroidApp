package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsPlantes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.guillemllados.bibliotecamaterial.AreaUsuari;
import com.example.guillemllados.bibliotecamaterial.Charts;
import com.example.guillemllados.bibliotecamaterial.DadesInternet;
import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.Sensor;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetSensorsData;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.UserPassDialog;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.usersDatabaseTasks;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.PieChartView;

public class FragmentPrincipal extends Fragment {

    private boolean chartCreated;
    private Charts charts;
    private View view;
    private Button bttareaUser;
    private EditText ET_Password, ET_Username;
    private String resultat;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_fragment_principal, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0,41,0,0);
        params.addRule(RelativeLayout.BELOW,R.id.tabs);
        view.setLayoutParams(params);

        ET_Password = (EditText) view.findViewById(R.id.ETPassword);
        ET_Username = (EditText) view.findViewById(R.id.ETUsername);
        resultat = "-1";

        charts = Charts.getChart();
        charts.setChartGeneral( (PieChartView) view.findViewById(R.id.chartGeneral));

        ArrayList<ArrayList<Sensor>> llistesSensors = new ArrayList<ArrayList<Sensor>>();
        Object[] parametres = new Object[1];
        String param1 = "-1";
        parametres[0] = param1; //pis a buscar -1 vol dir en tots
        GetSensorsData netTask = new GetSensorsData(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                if(output!= null) {
                    ArrayList<Sensor> llistaSensors = (ArrayList) output;
                    DadesInternet.LlistaSensors = llistaSensors;
                    while (!chartCreated) ;
                    charts.UpdateChartGeneral(llistaSensors);
                }
            }
        });
        netTask.execute(parametres);

        charts.UpdateChartGeneral(new ArrayList<Sensor>());



        chartCreated = true;

        bttareaUser = (Button)view.findViewById(R.id.bttAreaUsuari);
        dialogAutentification();

        this.view = view;
        return view;

    }

    public void dialogAutentification(){

        bttareaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkUserPass();

            }
        });




    }

    private void checkUserPass(){

        String password = ET_Username.getText().toString();
        final String username = ET_Username.getText().toString();

        if(!password.isEmpty() && !username.isEmpty()){
            Object[] parametres = new Object[3];
            parametres[0] = 1;
            parametres[1] = username;
            parametres[2] = password;

            usersDatabaseTasks usersDatabaseTasks = new usersDatabaseTasks(new AsyncResponse() {
                @Override
                public void processFinish(Object output) {

                    resultat = (String)output;
                    if(resultat.equals("1")){

                        Intent i = new Intent(getActivity(),AreaUsuari.class);
                        i.putExtra("username",username);
                        startActivity(i);
                    }


                }
            });

            usersDatabaseTasks.execute(parametres);
        }


    }


}
