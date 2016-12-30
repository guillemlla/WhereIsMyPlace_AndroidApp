package com.example.guillemllados.bibliotecamaterial;

import android.os.CountDownTimer;

import com.example.guillemllados.bibliotecamaterial.dataFromInternet.AsyncResponse;

/**
 * Created by guillemllados on 30/11/16.
 */

public class refreshData {

    private CountDownTimer countDownTimer;
    private AsyncResponse delegate;

    public refreshData(AsyncResponse delegate){
        this.delegate = delegate;
    }


    public void start(){

        countDownTimer = new CountDownTimer(10000,10000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                delegate.processFinish(null);

            }
        }.start();

    }
}
