package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import com.example.guillemllados.bibliotecamaterial.Log;
import com.example.guillemllados.bibliotecamaterial.Sensor;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by guillemllados on 20/11/16.
 */

public class XMLtoArrayConverterLogs {

    public ArrayList<Log> convert(String string){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {

            builder = factory.newDocumentBuilder();
            Document document = builder.parse( new InputSource( new StringReader(string)));
            NodeList sensors = document.getElementsByTagName("Log");
            Node node;
            NamedNodeMap atributes;
            Log l;

            ArrayList<Log> llistaLog = new ArrayList<Log>();

            for(int i=0;i<sensors.getLength();i++){
                node = sensors.item(i);
                atributes = node.getAttributes();
                l = new Log(value(atributes,"idLog"),value(atributes,"loginTimme"));
                llistaLog.add(l);
            }

            return  llistaLog;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String value(NamedNodeMap NodeMap, String atribute){
        return NodeMap.getNamedItem(atribute).getNodeValue();
    }
}
