package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.graphics.Bitmap;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by guillemllados on 11/12/16.
 */

public class AsyncResponseAdaptadorGridGetImage implements AsyncResponse{

    private RoundedImageView userImage;

    public AsyncResponseAdaptadorGridGetImage(RoundedImageView userImage){
        this.userImage = userImage;
    }
    @Override
    public void processFinish(Object output) {
        Bitmap image = (Bitmap) output;
        userImage.setImageBitmap(image);
    }
}
