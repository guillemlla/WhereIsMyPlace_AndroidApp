package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.os.AsyncTask;

import com.example.guillemllados.bibliotecamaterial.Log;
import com.example.guillemllados.bibliotecamaterial.Principal;
import com.example.guillemllados.bibliotecamaterial.Sensor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by guillemllados on 1/12/16.
 */

public class GetLogData extends AsyncTask {

    public AsyncResponse delegate = null;//Call back interface

    public GetLogData(AsyncResponse asyncResponse){
        delegate = asyncResponse;

    }

    @Override
    protected Object doInBackground(Object[] params) {
        //param 0 = username

        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            String Surl = "http://"+ Principal.ip+"/php/getUserLogs.php?username="+params[0];

            url = new URL(Surl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            String s =  response.toString();
            XMLtoArrayConverterLogs converter = new XMLtoArrayConverterLogs();
            ArrayList<Log> llistaLogs = converter.convert(s);

            return llistaLogs;

        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            if(connection != null) {
                connection.disconnect();
            }

        }
        return  null;

    }

    @Override
    protected void onPostExecute(Object params){

        delegate.processFinish(params);





    }



}