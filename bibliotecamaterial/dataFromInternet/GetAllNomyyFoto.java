package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.os.AsyncTask;

import com.example.guillemllados.bibliotecamaterial.Log;
import com.example.guillemllados.bibliotecamaterial.Principal;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by guillemllados on 11/12/16.
 */

public class GetAllNomyyFoto extends AsyncTask {

    public AsyncResponse delegate = null;//Call back interface

    public GetAllNomyyFoto(AsyncResponse asyncResponse){
        delegate = asyncResponse;

    }

    @Override
    protected Object doInBackground(Object[] params) {

        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            String Surl = "http://"+ Principal.ip+"/php/getAllNomyFoto.php";

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
            XMLtoArrayNomyFoto converter = new XMLtoArrayNomyFoto();
            ArrayList<String[]> llistaUsersNomiFoto = converter.convert(s);

            /*user[0] = "username"
            user[1] = v"idFoto"*/

            return llistaUsersNomiFoto;

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

class XMLtoArrayNomyFoto {

    public ArrayList<String[]> convert(String string){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {

            builder = factory.newDocumentBuilder();
            Document document = builder.parse( new InputSource( new StringReader(string)));
            NodeList sensors = document.getElementsByTagName("User");
            Node node;
            NamedNodeMap atributes;
            ArrayList<String[]> llistaUsersNomiFoto = new ArrayList<String[]>();
            String user [];

            for(int i=0;i<sensors.getLength();i++){
                node = sensors.item(i);
                atributes = node.getAttributes();
                user = new String[2];
                user[0] = value(atributes,"username");
                user[1] = value(atributes,"idFoto");
                llistaUsersNomiFoto.add(user);
            }

            return  llistaUsersNomiFoto;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String value(NamedNodeMap NodeMap, String atribute){
        return NodeMap.getNamedItem(atribute).getNodeValue();
    }
}