package com.example.subside;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class internetConnection {
    private Context context;
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
}
