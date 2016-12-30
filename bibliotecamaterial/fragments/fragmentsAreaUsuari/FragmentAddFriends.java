package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ExpandableHeightGridView;
import com.example.guillemllados.bibliotecamaterial.DadesInternet;
import com.example.guillemllados.bibliotecamaterial.Principal;
import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.ConfirmaConnexio;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetAllConectionsFromUsername;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetAllNomyyFoto;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetUsernameWithId;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.SetConnexion;

import java.util.ArrayList;


public class FragmentAddFriends extends Fragment {

    private View view;

    private ExpandableHeightGridView grid;
    private BaseAdapter adaptador;
    private ArrayList<FichaAddFriends> llistaFiches;
    private FichaAddFriends dummy;

    private int estat;//0 buscaAmics 1 Solicituds
    private Button botoCanviEstat;
    private FloatingActionButton BtSearch;
    private LinearLayout searcher;
    private int originalSearchHeigh;
    private EditText nomABuscar;
    private FragmentAddFriends thisFragment;

    private String username;

    public FragmentAddFriends() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_fragment_add_friends, container, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 41, 0, 0);
        params.addRule(RelativeLayout.BELOW, R.id.tabs);
        view.setLayoutParams(params);

        thisFragment = this;
        username = getArguments().getString("username");

        dummy = new FichaAddFriends("dummy",thisFragment);

        estat = 0;
        searcher = (LinearLayout) view.findViewById(R.id.Layout_search);
        originalSearchHeigh = searcher.getLayoutParams().height;

        grid = (ExpandableHeightGridView) view.findViewById(R.id.gridAddFriends);

        nomABuscar = (EditText) view.findViewById(R.id.ET_nomABuscar);
        nomABuscar.setText("");

        botoCanviEstat = (Button) view.findViewById(R.id.btt_addFriends);
        botoCanviEstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (estat == 1) {
                    estat = 0;
                    ViewGroup.LayoutParams layoutParams = searcher.getLayoutParams();
                    layoutParams.height = originalSearchHeigh;
                    searcher.setLayoutParams(layoutParams);
                    botoCanviEstat.setText("Solicituds Amistad");
                    ArrayList<FichaAddFriends> llista = DadesInternet.LlistaFichaAddFriend;
                    //llista.add(dummy);
                    llistaFiches = llista;
                    FichaAddFriends.setItems(llista);
                    AdaptadorGridAddFriends adapter = new AdaptadorGridAddFriends(view.getContext());
                    adaptador = adapter;
                    grid.setAdapter(adapter);
                    grid.setExpanded(true);
                    grid.refreshDrawableState();
                } else {
                    estat = 1;
                    ViewGroup.LayoutParams layoutParams = searcher.getLayoutParams();
                    layoutParams.height = 0;
                    searcher.setLayoutParams(layoutParams);
                    botoCanviEstat.setText("Buscar amics");
                    GetAllConectionsFromUsername task = new GetAllConectionsFromUsername(new AsyncResponse() {
                        @Override
                        public void processFinish(Object output) {

                            ArrayList<String[]> llistaUsuarisConnexio = (ArrayList<String[]>) output;
                            //String[] 0-> idUser 1->estat connexio
                            //Estat-> Retorna 0 si comfirmada, 1 si falta comfirmacio per part de username, 2
                            // si falta comfirmacio per l'altre part.
                            DadesInternet.LlistaFichaAddFriend = new ArrayList<FichaAddFriends>();
                            final ArrayList<FichaAddFriends> llista = DadesInternet.LlistaFichaAddFriend;
                            //llista.add(dummy);

                            for (String[] user : llistaUsuarisConnexio) {

                                if (user[1].equals("1")) {
                                    GetUsernameWithId task = new GetUsernameWithId(new AsyncResponse() {
                                        @Override
                                        public void processFinish(Object output) {
                                            FichaAddFriends addFriend = new FichaAddFriends((String) output, thisFragment);
                                            llista.add(addFriend);

                                            llistaFiches = llista;
                                            FichaAddFriends.setItems(llistaFiches);
                                            AdaptadorGridAddFriends adapter = new AdaptadorGridAddFriends(getContext());
                                            adaptador = adapter;
                                            grid.setAdapter(adapter);
                                            grid.setExpanded(true);
                                            grid.refreshDrawableState();

                                        }
                                    });
                                    task.execute(user[0]);


                                }
                            }



                        }
                    });
                    task.execute(username);

                }

            }
        });

        BtSearch = (FloatingActionButton) view.findViewById(R.id.FB_search);

        BtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (estat == 0) {
                    searchButonClick();
                }
            }
        });

        return view;
    }

    public void FichaButtonClicked(int idFicha) {

        if(estat==0){

            FichaAddFriends item = FichaAddFriends.getItem(idFicha);
            String[] strings = new String[2];
            strings[0] = username;
            strings[1] = item.getNombre();
            SetConnexion task = new SetConnexion(new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    if(output=="1"){

                    }
                }
            });

            task.execute(strings);

        }else{

            final FichaAddFriends item = FichaAddFriends.getItem(idFicha);
            String[] strings = new String[2];
            strings[0] = username;
            strings[1] = item.getNombre();
            ConfirmaConnexio task = new ConfirmaConnexio(new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    int i =1;
                    i++;
                    String numero = (String) output;
                    char result[] = numero.toCharArray();

                    if(result[0]=='1' && result[1]!='1') {

                        Button btt = item.getBoto();
                        btt.setEnabled(false);
                        btt.setText("Amic acceptat correctament");
                        btt.setTextColor(getResources().getColor(R.color.green));
                        btt.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                        item.setBoto(btt);

                    }

                }
            });
            task.execute(strings);

        }


    }

    public void searchButonClick() {

        if (nomABuscar.getText().toString().equals("")) return;
        final String nom = nomABuscar.getText().toString();
        GetAllNomyyFoto task = new GetAllNomyyFoto(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                final ArrayList<String[]> llistaUsersNomiFoto = (ArrayList<String[]>) output;
                final ArrayList<String[]> escogidos = new ArrayList<>();

                boolean añadido = false;
                for (String[] user : llistaUsersNomiFoto) {
                    String[] separado = user[0].split(" ");
                    for (String s : separado) {
                        if (s.equals(nom) && !añadido) {
                            añadido = true;
                            escogidos.add(user);
                        }
                    }
                    añadido = false;
                }
                ArrayList<FichaAddFriends> fichasAddFriend = new ArrayList<>();
                FichaAddFriends.setItems(fichasAddFriend);
                for (String[] user : escogidos) {
                    FichaAddFriends fichaUser = new FichaAddFriends(user[0], thisFragment);
                    FichaAddFriends.añadeItem(fichaUser);
                }
                AdaptadorGridAddFriends adapter = new AdaptadorGridAddFriends(view.getContext());
                grid.setAdapter(adapter);
                grid.setExpanded(true);
                grid.refreshDrawableState();

            }
        });
        task.execute();


    }

}

