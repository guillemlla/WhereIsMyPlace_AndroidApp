package com.example.guillemllados.bibliotecamaterial.fragments.fragmentsAreaUsuari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.guillemllados.bibliotecamaterial.R;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponseAdaptadorGridGetImage;
import com.example.guillemllados.bibliotecamaterial.dataFromInternet.LoadBitMap;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by guillemllados on 11/12/16.
 */

public class AdaptadorGridAddFriends extends BaseAdapter {

    private Context context;
    private boolean firstOne;

    public AdaptadorGridAddFriends(Context context) {
        this.context = context;
        firstOne = true;
    }

    @Override
    public int getCount() {
        return FichaAddFriends.ITEMS.size();
    }

    @Override
    public FichaAddFriends getItem(int position) {
        return FichaAddFriends.ITEMS.get(position);
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
            view = inflater.inflate(R.layout.ficha_add_friend, viewGroup, false);
        }

        RoundedImageView imagen = (RoundedImageView) view.findViewById(R.id.IVFicha2);
        TextView nom = (TextView) view.findViewById(R.id.TVFicha2);
        Button btt = (Button) view.findViewById(R.id.Btt_ficha2);
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.layoutAddFriend);

        final FichaAddFriends item = getItem(position);

        item.setBoto(btt);

        if(item.getNombre().equals("dummy")){
            FrameLayout dummyLayout = layout;
            ViewGroup.LayoutParams  layoutParams = new ViewGroup.LayoutParams(0,0);
            layoutParams.height=0;
            dummyLayout.setLayoutParams(layoutParams);
            firstOne=false;
        }else{
            String nombre = item.getNombre();
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
            btt.setOnClickListener(item.getListener());
            nom.setText(nombre);
            loadUserImage(item.getNombre(),imagen);

        }


        return view;
    }

    public void loadUserImage(String username, RoundedImageView userImage){

        LoadBitMap load = new LoadBitMap(new AsyncResponseAdaptadorGridGetImage(userImage));
        load.execute(username);

    }
}