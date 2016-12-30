package com.example.guillemllados.bibliotecamaterial.dataFromInternet;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.guillemllados.bibliotecamaterial.AreaUsuari;
import com.example.guillemllados.bibliotecamaterial.R;


public class UserPassDialog extends android.support.v4.app.DialogFragment {

    private EditText ET_username,ET_password;
    private String resultat;
    private Activity activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        activity = getActivity();

        resultat = "-1";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.autentificacion, null);

        ET_password =(EditText)  v.findViewById(R.id.ET_password);
        ET_username =(EditText) v.findViewById(R.id.ET_username);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Accedeix", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        checkUserPass();
                    }
                })
                .setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserPassDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void checkUserPass(){

        String password = ET_password.getText().toString();
        final String username = ET_username.getText().toString();

        if(!password.isEmpty() && !username.isEmpty()){
            Object[] parametres = new Object[3];
            parametres[0] = 1;
            parametres[1] = username;
            parametres[2] = password;

            usersDatabaseTasks usersDatabaseTasks = new usersDatabaseTasks(new AsyncResponse() {
                @Override
                public void processFinish(Object output) {

                    resultat = (String)output;
                    if(resultat.equals("1")){

                        Intent i = new Intent(activity,AreaUsuari.class);
                        i.putExtra("username",username);
                        startActivity(i);
                    }


                }
            });

            usersDatabaseTasks.execute(parametres);
        }


    }
}
