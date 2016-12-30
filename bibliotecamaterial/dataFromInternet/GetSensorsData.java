package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.os.AsyncTask;

import com.example.guillemllados.bibliotecamaterial.Principal;
import com.example.guillemllados.bibliotecamaterial.Sensor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by guillemllados on 15/11/16.
 */

public class GetSensorsData extends AsyncTask {

    public AsyncResponse delegate = null;//Call back interface

    public GetSensorsData(AsyncResponse asyncResponse){
        delegate = asyncResponse;

    }

    @Override
    protected Object doInBackground(Object[] params) {
            //param 1 = pis a buscar si -1 llavors tots

            URL url;
            HttpURLConnection connection = null;
            try {
                //Create connection
                String Surl;
                String opcion = (String) params[0];
                if(opcion.equals("-1")){
                    Surl = "http://"+ Principal.ip+"/php/requestAllDataXML.php";
                }else{
                    Surl = "http://"+ Principal.ip+"/php/requestDataXMLWithFloorChoosingAndroid.php?pis="+params[0];
                }

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
                XMLtoArrayConverterSensors converter = new XMLtoArrayConverterSensors();
                ArrayList<Sensor> llistaSensors = converter.convert(s);

                return llistaSensors;

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




