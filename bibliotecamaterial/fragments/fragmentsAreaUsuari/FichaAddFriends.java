package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by guillemllados on 11/12/16.
 */

public class FichaAddFriends {

    private static int count = 0;
    private int id;
    private String nombre;
    private Button.OnClickListener listener;
    private Button boto;


    public Button getBoto() {
        return boto;
    }

    public void setBoto(Button boto) {

        this.boto = boto;
    }

    public static ArrayList<FichaAddFriends> ITEMS;

    public FichaAddFriends(String nombre, final FragmentAddFriends fragmentAddFriends) {
        this.id = count;
        count++;
        this.nombre = nombre;

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentAddFriends.FichaButtonClicked(id);
            }
        };




    }

    public String getNombre(){
        return nombre;
    }


    public int getId() {
        return id;
    }
    public Button.OnClickListener getListener(){

        return listener;

    };

    public final static void setItems(ArrayList<FichaAddFriends> items){ ITEMS = items;}
    public final static void añadeItem(FichaAddFriends item){
        ITEMS.add(item);
    }

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Coche
     */

    public static FichaAddFriends getItem(int id) {
        for (FichaAddFriends item :ITEMS ) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}