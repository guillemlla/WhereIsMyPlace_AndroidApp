package com.example.guillemllados.bibliotecamaterial;
/**
 * Created by guillemllados on 20/11/16.
 */

public class Sensor {

    private final String idSensor,idIntel,planta,taula,grupTaula;
    private String estat;

    public Sensor(String idIntel , String idSensor, String planta, String taula, String estat,String grupTaula) {
        this.idSensor = idSensor;

        this.idIntel = idIntel;
        this.planta = planta;
        this.taula = taula;
        this.estat = estat;
        this.grupTaula = grupTaula;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getIdSensor() {
        return idSensor;
    }

    public String getIdIntel() {
        return idIntel;
    }

    public String getPlanta() {
        return planta;
    }

    public String getTaula() {
        return taula;
    }

    public String getEstat() {
        return estat;
    }

    public String getGrupTaula() {
        return grupTaula;
    }
}
