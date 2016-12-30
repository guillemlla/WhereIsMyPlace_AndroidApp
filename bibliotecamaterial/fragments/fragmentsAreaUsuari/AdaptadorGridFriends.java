package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponseAdaptadorGridGetImage;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.LoadBitMap;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by guillemllados on 5/12/16.
 */

public class AdaptadorGridFriends extends BaseAdapter {

    private Context context;

    public AdaptadorGridFriends(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return FichaFriend.ITEMS.size();
    }

    @Override
    public FichaFriend getItem(int position) {
        return FichaFriend.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ficha_friend, viewGroup, false);
        }

        RoundedImageView imagen = (RoundedImageView) view.findViewById(R.id.IVFicha);
        TextView nom = (TextView) view.findViewById(R.id.TVNomFicha);
        TextView lloc = (TextView) view.findViewById(R.id.TVLlocBiblio);
        TextView estat = (TextView) view.findViewById(R.id.TVEstatFicha);

        final FichaFriend item = getItem(position);

        if(item.isAceptado()){

            loadUserImage(item.getNombre(),imagen);
            String username = item.getNombre().substring(0, 1).toUpperCase() + item.getNombre().substring(1);
            nom.setText("Nom: "+username);
            estat.setText(item.isBiblio());
            if(item.isBiblio().equals("No esta a la biblioteca") || item.isBiblio().equals("Fa més de 5 hores del seu registre")){
                lloc.setText(" ");
            }else {
                lloc.setText("Planta biblioteca: Planta "+item.getLugar());
            }

        }else {
            imagen.setImageResource(R.drawable.logo_birrete);
            nom.setText("Nom: "+item.getNombre());
            estat.setText("Solicitud encara no acceptada");
            lloc.setText(" ");
        }

        view.setClickable(false);
        return view;


    }

    public void loadUserImage(String username, RoundedImageView userImage){

        LoadBitMap load = new LoadBitMap(new AsyncResponseAdaptadorGridGetImage(userImage));
        load.execute(username);


    }
}
