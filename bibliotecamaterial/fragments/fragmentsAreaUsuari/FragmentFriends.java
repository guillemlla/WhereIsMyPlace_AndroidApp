package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.example.ExpandableHeightGridView;
import com.example.guillemllados.bibliotecamaterial.DadesInternet;
import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetAllConectionsFromUsername;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetPlaceWithUserId;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetUsernameWithId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentFriends extends Fragment {

    private View view;

    private ExpandableHeightGridView grid;
    private BaseAdapter adaptador;
    private FragmentFriends thisFragment;
    private String username;

    public FragmentFriends() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_fragment_friends, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 41, 0, 0);
        params.addRule(RelativeLayout.BELOW, R.id.tabs);
        view.setLayoutParams(params);
        thisFragment = this;

        username = getArguments().getString("username");
        grid = (ExpandableHeightGridView) view.findViewById(R.id.gridFriends);

        GetAllConectionsFromUsername task = new GetAllConectionsFromUsername(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {

                ArrayList<String[]> llistaUsuarisConnexio = (ArrayList<String[]>) output;
                //String[] 0-> idUser 1->estat connexio
                //Estat-> Retorna 0 si comfirmada, 1 si falta comfirmacio per part de username, 2
                // si falta comfirmacio per l'altre part.
                DadesInternet.LlistaFichaFriend = new ArrayList<>();
                final ArrayList<FichaFriend> llista = DadesInternet.LlistaFichaFriend ;

                for (final String[] user : llistaUsuarisConnexio) {
                    //falta completar
                    GetUsernameWithId task = new GetUsernameWithId(new AsyncResponse() {
                        @Override
                        public void processFinish(Object output1) {
                            String idUser = user[0];
                            GetPlaceWithUserId subTask = new GetPlaceWithUserId(new AsyncResponse() {
                                @Override
                                public void processFinish(Object output) {

                                    String string = (String) output;
                                    String[] separado = string.split("%");
                                    String username = separado[0];
                                    String numPlanta = separado[1];
                                    String dateTime = separado[2];

                                    String estatLloc = convertDateString(dateTime);

                                    FichaFriend addFriend;

                                    if(user[1].equals("0")) {
                                         addFriend = new FichaFriend(username,numPlanta,true,estatLloc);
                                    }else {
                                         addFriend = new FichaFriend(username,null,false,null);
                                    }

                                    llista.add(addFriend);
                                    FichaFriend.setItems(llista);

                                    AdaptadorGridFriends adapter = new AdaptadorGridFriends(getContext());
                                    grid.setAdapter(adapter);
                                    grid.setExpanded(true);
                                    grid.refreshDrawableState();

                                }
                            });
                            subTask.execute(idUser);

                        }
                    });
                    task.execute(user[0]);
                    }
                }



        });
        task.execute(username);


        return view;
    }

    public String convertDateString(String dateString){

        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

        if (dateString != null) {
            try {
                date = iso8601Format.parse(dateString);
            } catch (ParseException e) {
                date = null;
            }
        }
        Date actual = new Date();
        String diaActual = (dateFormat.format(actual));

        int horaActual = Integer.parseInt(hourFormat.format(actual));
        int minActual = Integer.parseInt(minuteFormat.format(actual));

        String dia = (dateFormat.format(date));

        int hora = Integer.parseInt(hourFormat.format(date));
        int min = Integer.parseInt(minuteFormat.format(date));

        if(dia.equals(diaActual)){
            int difHores = horaActual-hora;
            if(difHores == 0){
                int difMin = minActual -min;
                if(difMin>30){
                    return "Fa més de 30 min que ha entrat";
                }else{
                    return "Fa menys de 30 min que ha arribat";
                }
            }else if(difHores==1){
                return "Fa més d'una hora que ha entrat";
            }else if(difHores==2){
                return "Fa més de dues hores que ha entrat";
            } else if (difHores == 3) {

                return "Fa més de tres hores que ha entrat";
            }else if(difHores>3 && difHores<5){
                return "Es poc provable que estigui a la biblioteca";
            }else{
                return "Fa més de 5 hores del seu registre";
            }


        }else{
            return "No esta a la biblioteca";
        }

    }


}
