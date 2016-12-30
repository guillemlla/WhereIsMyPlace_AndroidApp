package com.example.guillemllados.bibliotecamaterial;

import android.text.style.TtsSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guillemllados on 1/12/16.
 */

public class Log {

    private String idLog;
    private Date date;
    private int dia,mes,any;

    public int getDay() {
        return dia;
    }

    public int getMonth() {
        return mes;
    }

    public int getYear() {
        return any;
    }

    public Log(String idLog, String dateTimeString) {
        this.idLog = idLog;

        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = null;
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        if (dateTimeString != null) {
            try {
                date = iso8601Format.parse(dateTimeString);
            } catch (ParseException e) {
                date = null;
            }
        }

        dia = Integer.parseInt(dayFormat.format(date));
        mes = Integer.parseInt(monthFormat.format(date));
        any = Integer.parseInt(yearFormat.format(date));

    }

    public String getIdLog() {
        return idLog;
    }

    public Date getDate() {
        return date;
    }
}
