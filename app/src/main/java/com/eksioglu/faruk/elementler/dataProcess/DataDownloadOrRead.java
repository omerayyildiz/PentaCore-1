package com.eksioglu.faruk.elementler.dataProcess;

import android.content.Context;
import android.content.SharedPreferences;

import static com.eksioglu.faruk.elementler.MainActivity.isConnected;
import static com.eksioglu.faruk.elementler.MainActivity.isDownloaded;

public class DataDownloadOrRead {

    public boolean isNull;

    private String data;
    private SharedPreferences sharedPreferences;
    private HTTPDataHandler dataHandler;

    public DataDownloadOrRead(Context context){
        dataHandler = new HTTPDataHandler();
        sharedPreferences = context.getSharedPreferences("com.eksioglu.faruk.elementler", Context.MODE_PRIVATE);
        isNull = false;

        data = sharedPreferences.getString("data", "null");
        if (data.equals("null")){
            isNull = true;
        }

        System.out.println("data: "+ data);
        System.out.println("data size: "+ data.length());
    }

    public void downloadData(){
        int versionCode = sharedPreferences.getInt("version", 0);

        String dataUrl = "https://raw.githubusercontent.com/femrek/elements/master/data.txt";
        String versionCodeUrl = "https://raw.githubusercontent.com/femrek/elements/master/versionCode.txt";

        int currentVersionCode;
        String versionCodeString = dataHandler.getHTTPData(versionCodeUrl);
        System.out.println(versionCodeString);
        try {
            currentVersionCode = Integer.parseInt(versionCodeString);
            isConnected = true;
        }catch (NumberFormatException e){
            System.out.println("bağlantı hatası");
            currentVersionCode = -1;
            isConnected = false;
        }

        if ((versionCode == 0 && currentVersionCode != -1) || isNull){
            data = dataHandler.getHTTPData(dataUrl);
            if (!data.equalsIgnoreCase("null"))
                isDownloaded = true;
            versionCode = currentVersionCode;
            sharedPreferences.edit().putInt("version", versionCode).apply();
            sharedPreferences.edit().putString("data", data).apply();
        }
        else if (versionCode != 0 && currentVersionCode == -1){
            data = sharedPreferences.getString("data", "null");
        }
        else if (versionCode != currentVersionCode){
            data = dataHandler.getHTTPData(dataUrl);
            if (!data.equalsIgnoreCase("null"))
                isDownloaded = true;
            if (data.equals("null")){
                data = dataHandler.getHTTPData(dataUrl);
                if (data == null || data.equals("")){
                    System.out.println("have a problem while downloading data");
                }else {
                    versionCode = currentVersionCode;
                    sharedPreferences.edit().putInt("version", versionCode).apply();
                    sharedPreferences.edit().putString("data", data).apply();
                }
            }else {
                versionCode = currentVersionCode;
                sharedPreferences.edit().putInt("version", versionCode).apply();
                sharedPreferences.edit().putString("data", data).apply();
            }
        }
    }

    public String getData(){
        return data;
    }
}
