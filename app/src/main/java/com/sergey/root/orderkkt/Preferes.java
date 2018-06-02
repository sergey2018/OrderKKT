package com.sergey.root.orderkkt;

import android.content.Context;
import android.preference.PreferenceManager;

public class Preferes {
    private static final String YANDEX="yandex";
    private static final String CURYER="curer";
    private static final String FIRST = "first";

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

    public static void setFirst(Context context, boolean f){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(FIRST,f).apply();
    }
    public static boolean getFlag(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(FIRST,false);
    }

}
