package com.example.subside;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class internetConnection {
    private Context context;
    Boolean closeDialog=true;
    public internetConnection(Context context){
        this.context=context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            if(networkInfo.isConnected()) return true;
            else return false;
        }
        else return false;

    }


    public void noInternet(){

        AlertDialog.Builder builder= new AlertDialog.Builder(this.context);

        builder.setTitle("No internet connection");

        builder.setMessage("Check your connection and try again");


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!isConnectingToInternet()) {
                    noInternet();
                }
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    public Boolean getCloseDialog() {
        noInternet();
        return closeDialog;
    }
}
