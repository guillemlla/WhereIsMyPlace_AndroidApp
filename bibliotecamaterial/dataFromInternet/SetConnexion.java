package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.os.AsyncTask;

import com.example.guillemllados.bibliotecamaterial.Principal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by guillemllados on 12/12/16.
 */

public class SetConnexion extends AsyncTask<String[],Object,String> {



    public AsyncResponse delegate = null;//Call back interface

    public SetConnexion(AsyncResponse asyncResponse){
        delegate = asyncResponse;

    }

    @Override
    protected String doInBackground(String[]... strings) {
        //strings[0] = username
        URL myFileUrl = null;
        String username1 = strings[0][0];
        String username2 = strings[0][1];
        String urlString = "http://"+ Principal.ip + "/php/newUserConnection.php?username1="+username1+"&username2="+username2;
        try {
            myFileUrl = new URL(urlString);

            HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            return response.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onPostExecute(String result) {

        delegate.processFinish(result);


    }

}