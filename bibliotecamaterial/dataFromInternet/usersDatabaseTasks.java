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
 * Created by guillemllados on 30/11/16.
 */

public class usersDatabaseTasks extends AsyncTask<Object,Object,String> {

    public AsyncResponse delegate = null;//Call back interface

    public usersDatabaseTasks(AsyncResponse asyncResponse){
        delegate = asyncResponse;
    }

    @Override
    protected String doInBackground(Object[] params) {
        //param[0]= accion param[1] = user param[2] = password
        //accion -> si 0 new user si 1 check

        String result= "11"; //error conexion java


        if(params[0].equals(0)){

            result = newUser((String)params[1],(String)params[2]);


        }else if(params[0].equals(1)){

            result = passwordUserCheck((String)params[1],(String)params[2]);
            int i = 1;
            i = i+1;

        }

        return result;

        //result = xx = 11 java connection error / xx=0 php error / xx=10 user exists

    }

    private String newUser(String username,String password){

        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL("http://"+ Principal.ip+"/php/insereixUsuari.php?username="+username+"&password="+password);
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
            return s;

        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            if(connection != null) {
                connection.disconnect();
            }

        }

        return "11"; //Error de conexion en java

    }

    private String passwordUserCheck(String username, String password){

        URL url;
        HttpURLConnection connection = null;
        String s = null;
        try {
            //Create connection
            url = new URL("http://"+ Principal.ip+"/php/verificaContrassenyaUsuari.php?username="+username+"&password="+password);
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
            if((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            s =  response.toString();
            return s;

        } catch (Exception e) {

            e.printStackTrace();


        } finally {

            if(connection != null) {
                connection.disconnect();
            }

        }

        return "11"; //Error de conexion en java

        //return "11";

    }

    @Override
    protected void onPostExecute(String params){

        delegate.processFinish(params);
        //params = xx = 11 java connection error / xx=0 php error / xx=10 user exists


    }



}
