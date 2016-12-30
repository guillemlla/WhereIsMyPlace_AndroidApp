package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.util.Xml;

import com.example.guillemllados.bibliotecamaterial.Sensor;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by guillemllados on 20/11/16.
 */

public class XMLtoArrayConverterSensors {

    public ArrayList<Sensor> convert(String string){

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {

            builder = factory.newDocumentBuilder();
            Document document = builder.parse( new InputSource( new StringReader(string)));
            NodeList sensors = document.getElementsByTagName("Sensor");
            Node node;
            NamedNodeMap atributes;
            Sensor s;

            ArrayList<Sensor> llistaSensors = new ArrayList<Sensor>();

            for(int i=0;i<sensors.getLength();i++){
                node = sensors.item(i);
                atributes = node.getAttributes();
                s = new Sensor(value(atributes,"idIntel"),value(atributes,"idSensor"),value(atributes,"planta"),value(atributes,"taula"),value(atributes,"estat"),value(atributes,"grupTaula"));
                llistaSensors.add(s);
            }

            int i = 1;
            i =  i+1;

            return  llistaSensors;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String value(NamedNodeMap NodeMap, String atribute){
        return NodeMap.getNamedItem(atribute).getNodeValue();
    }
}
