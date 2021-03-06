package com.sergey.root.orderkkt;

import android.content.Context;
import android.preference.PreferenceManager;

public class Preferes {
    private static final String YANDEX="yandex";
    private static final String CURYER="curer";
    private static final String IP_adres="ip_adres";
    private static final String PORT="port";
    private static final String DAY="day";
    private static final String SELECT="select";
    private static final String onKKT="kkt";
    private static final String PORT_TYPE="port_type";
    private static final String PORT_CLASS="port_class";
    private static final String ATOL_SETTINGS = "atol_settings";

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
    public static void setDay(Context context){
        int day = getDay(context)+1;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(DAY,day).apply();
    }
    public static int getDay(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(DAY,1);
    }
    public static void setPortType(Context context, String porttype){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PORT_TYPE,porttype).apply();
    }
    public static String getPortType(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PORT_TYPE,"");
    }
    public static void setPortClass(Context context, String portclass){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PORT_CLASS,portclass).apply();
    }
    public static String getPortClass(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PORT_CLASS,"");
    }
    public static void setAtolSettings(Context context, String settings){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(ATOL_SETTINGS,settings).apply();
    }
    public static String getAtolSettings(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(ATOL_SETTINGS,"");
    }
}
