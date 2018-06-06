package com.sergey.root.orderkkt;

import android.content.Context;
import android.preference.PreferenceManager;

public class Preferes {
    private static final String YANDEX="yandex";
    private static final String CURYER="curer";
    private static final String IP_adres="ip_adres";
    private static final String PORT="port";
    private static final String SELECT="select";
    private static final String onKKT="kkt";

    public static void setToken(Context context, String token){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(YANDEX,token).apply();
    }

    public static String getToken(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(YANDEX,null);
    }
    public static void setCuryer(Context context, String cur){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(CURYER,cur).apply();
    }
    public static String getCuryer(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(CURYER,null);
    }

    public static void setIP_adres(Context context,String ip){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(IP_adres,ip).apply();
    }
    public static String getIP_adres(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(IP_adres,"");
    }
    public static void  setPort(Context context, String port){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PORT,port).apply();
    }
    public static String getPort(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PORT,"");
    }

    public static void setSelect(Context context, int s){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(SELECT,s).apply();
    }
    public static int getSelect(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(SELECT, 0);
    }
}
