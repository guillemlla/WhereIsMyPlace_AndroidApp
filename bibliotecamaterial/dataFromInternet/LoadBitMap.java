package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.guillemllados.bibliotecamaterial.Log;
import com.example.guillemllados.bibliotecamaterial.Principal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by guillemllados on 11/12/16.
 */

public class LoadBitMap extends AsyncTask<String,Object,Bitmap> {



    public AsyncResponse delegate = null;//Call back interface

    public LoadBitMap(AsyncResponse asyncResponse){
        delegate = asyncResponse;

    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        //strings[0] = username
        String idFoto = null;
        URL myFileUrl = null;
        String urlString = "http://"+Principal.ip + "/php/getNomFoto.php?username="+strings[0];
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
            idFoto = response.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        Bitmap bmImg;
        myFileUrl = null;
        urlString = "http://"+Principal.ip + "/ProfilePictures/"+idFoto;

        try {
            myFileUrl = new URL(urlString);

            HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();

            BitmapFactory.Options options=new BitmapFactory.Options();


            bmImg = BitmapFactory.decodeStream(is, null, options);
            return bmImg;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){

        delegate.processFinish(bitmap);





    }



}
