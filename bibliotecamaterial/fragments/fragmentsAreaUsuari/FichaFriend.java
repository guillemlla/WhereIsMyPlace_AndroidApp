package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari;

import java.util.ArrayList;

/**
 * Created by guillemllados on 5/12/16.
 */

public class FichaFriend {

    private static int count = 0;
    private int id;
    private String nombre,lugar,EstaBiblio;
    private boolean aceptado;
    public static ArrayList<FichaFriend> ITEMS;

    public FichaFriend(String nombre, String lugar, boolean aceptado, String EstaBiblio) {
        this.id = count;
        count++;
        this.nombre = nombre;
        this.lugar = lugar;
        this.aceptado = aceptado;
        this.EstaBiblio = EstaBiblio;
    }

    public String getNombre(){
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getLugar() {
        return lugar;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public String isBiblio() {
        return EstaBiblio;
    }


    public final static void setItems(ArrayList<FichaFriend> items){ ITEMS = items;}
    public final static void añadeItem(FichaFriend item){
        ITEMS.add(item);
    }

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Coche
     */

    public static FichaFriend getItem(int id) {
        for (FichaFriend item :ITEMS ) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
