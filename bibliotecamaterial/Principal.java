package com.example.guillemllados.bibliotecamaterial;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.guillemllados.bibliotecamaterial.fragments.fragmentsPlantes.FragmentPlanta1;
import com.example.guillemllados.bibliotecamaterial.fragments.fragmentsPlantes.FragmentPlanta2;
import com.example.guillemllados.bibliotecamaterial.fragments.fragmentsPlantes.FragmentPlanta3;
import com.example.guillemllados.bibliotecamaterial.fragments.fragmentsPlantes.FragmentPrincipal;

public class Principal extends AppCompatActivity {

    private Charts charts;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private FragmentPrincipal principal;
    private FragmentPlanta1 planta1;
    private FragmentPlanta2 planta2;
    private FragmentPlanta3 planta3;
    private Toolbar toolbar;
    private int plantaActual; //-1 principal

    final static public String ip = "10.0.60.237";//"10.0.2.2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        charts = Charts.getChart();

        toolbar = (Toolbar)findViewById(R.id.toolBar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: homeLoad();break;
                    case 1: planta1Load();break;
                    case 2: planta2Load();break;
                    case 3: planta3Load();break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                homeLoad();
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: homeLoad();break;
                    case 1: planta1Load();break;
                    case 2: planta2Load();break;
                    case 3: planta3Load();break;
                }
            }
        });


        fragmentManager = getSupportFragmentManager();
        principal = new FragmentPrincipal();
        planta1 = new FragmentPlanta1();
        planta2 = new FragmentPlanta2();
        planta3 = new FragmentPlanta3();

        plantaActual = -1;

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.activity_principal, principal);
        transaction.commit();

    }

    public void homeLoad(){

        toolbar.setTitle("Biblioteca BRGF");

        plantaActual = -1;

        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.entrar_por_izquierda,R.anim.salir_por_derecha,R.anim.entrar_por_derecha,R.anim.salir_por_izquierda);
        transaction.replace(R.id.activity_principal, principal ).addToBackStack(null);

        transaction.commit();

    }
    public void planta1Load(){

        toolbar.setTitle("Planta 1");


        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(plantaActual>1){
            transaction.setCustomAnimations(R.anim.entrar_por_izquierda,R.anim.salir_por_derecha,R.anim.entrar_por_derecha,R.anim.salir_por_izquierda);
        }else{
            transaction.setCustomAnimations(R.anim.entrar_por_derecha,R.anim.salir_por_izquierda,R.anim.entrar_por_izquierda,R.anim.salir_por_derecha);
        }

        transaction.replace(R.id.activity_principal, planta1 ).addToBackStack(null);

        transaction.commit();

        plantaActual = 1;

    }
    public void planta2Load(){

        toolbar.setTitle("Planta 2");

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(plantaActual>2){
            transaction.setCustomAnimations(R.anim.entrar_por_izquierda,R.anim.salir_por_derecha,R.anim.entrar_por_derecha,R.anim.salir_por_izquierda);
        }else{
            transaction.setCustomAnimations(R.anim.entrar_por_derecha,R.anim.salir_por_izquierda,R.anim.entrar_por_izquierda,R.anim.salir_por_derecha);
        }
        transaction.replace(R.id.activity_principal, planta2 ).addToBackStack(null);

        transaction.commit();

        plantaActual = 2;

    }
    public void planta3Load(){

        toolbar.setTitle("Planta 3");

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(plantaActual>3){
            transaction.setCustomAnimations(R.anim.entrar_por_izquierda,R.anim.salir_por_derecha,R.anim.entrar_por_derecha,R.anim.salir_por_izquierda);
        }else{
            transaction.setCustomAnimations(R.anim.entrar_por_derecha,R.anim.salir_por_izquierda,R.anim.entrar_por_izquierda,R.anim.salir_por_derecha);
        }
        transaction.replace(R.id.activity_principal, planta3 ).addToBackStack(null);
        transaction.commit();

        plantaActual = 3;


    }
}
