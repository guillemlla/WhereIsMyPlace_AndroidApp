package com.example.guillemllados.bibliotecamaterial;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.ComfirmaUserName;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.GetLogData;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.LoadBitMap;
import com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari.FragmentAddFriends;
import com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari.FragmentFriends;
import com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari.FragmentHistorial;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class AreaUsuari extends AppCompatActivity {

    private BottomNavigationView barraNav;
    private Toolbar toolbar;
    private String username;

    private FragmentHistorial historial;
    private FragmentFriends friends;
    private FragmentAddFriends addFriends;
    private RoundedImageView userImage;
    private TextView TVUsername;

    private FragmentManager fragmentManager;
    private int actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_usuari);

        username = getIntent().getStringExtra("username");
        userImage = (RoundedImageView ) findViewById(R.id.userImage);
        loadUserImage(username);


        TVUsername = (TextView)findViewById(R.id.TVusernameAreaUser);
        String usernameUppercase = username.substring(0, 1).toUpperCase() + username.substring(1);
        TVUsername.setText(usernameUppercase);

        toolbar = (Toolbar)findViewById(R.id.toolbarUser);
        toolbar.setNavigationIcon(R.drawable.home_grey);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHome();
            }
        });
        toolbar.setTitle(R.string.Area_usuari_toolbar_title);



        barraNav = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        barraNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.userMenu_item_historial:loadHistorial();return true;
                    case R.id.userMenu_item_AddFriends: loadAddFriedns();return true;
                    case R.id.userMenu_item_FindMyFriends: loadFindMyFriends();return true;
                    case R.id.userMenu_item_tools: loadTools();return true;
                }


                return false;
            }
        });

        actual = 0;

        fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString("username",username);

        historial = new FragmentHistorial();
        historial.setArguments(bundle);
        friends = new FragmentFriends();
        friends.setArguments(bundle);
        addFriends = new FragmentAddFriends();
        addFriends.setArguments(bundle);


        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.LayoutAreaUser, historial);
        transaction.commit();


    }

    public void loadHistorial(){

        actual = 0;
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.LayoutAreaUser,historial);
        transaction.setCustomAnimations(R.anim.entrar_por_izquierda,R.anim.salir_por_derecha);
        transaction.commit();


    }
    public void loadFindMyFriends(){

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(actual>1){
            transaction.setCustomAnimations(R.anim.entrar_por_izquierda,R.anim.salir_por_derecha,R.anim.entrar_por_derecha,R.anim.salir_por_izquierda);
        }else{
            transaction.setCustomAnimations(R.anim.entrar_por_derecha,R.anim.salir_por_izquierda,R.anim.entrar_por_izquierda,R.anim.salir_por_derecha);
        }
        transaction.replace(R.id.LayoutAreaUser, friends );
        transaction.commit();
        actual = 1;

    }

    public void loadAddFriedns(){

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(actual>2){
            transaction.setCustomAnimations(R.anim.entrar_por_izquierda,R.anim.salir_por_derecha,R.anim.entrar_por_derecha,R.anim.salir_por_izquierda);
        }else{
            transaction.setCustomAnimations(R.anim.entrar_por_derecha,R.anim.salir_por_izquierda,R.anim.entrar_por_izquierda,R.anim.salir_por_derecha);
        }
        transaction.replace(R.id.LayoutAreaUser, addFriends ).addToBackStack(null);
        transaction.commit();
        actual = 2;

    }

    public void loadTools(){
        actual = 3;

    }


    public void onClickHome(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Cerrar session");
        b.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        b.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        b.create().show();


    }

    public void loadUserImage(String username){

        LoadBitMap load = new LoadBitMap(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                Bitmap image = (Bitmap) output;
                userImage.setImageBitmap(image);
            }
        });
        load.execute(username);


    }



}
