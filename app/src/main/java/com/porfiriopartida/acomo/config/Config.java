package com.porfiriopartida.acomo.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Config {
    public static final String CALL_MUTE = "CALL_MUTE";

    public static boolean isCallMuteEnabled(Context ctx){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        return prefs.getBoolean(CALL_MUTE, false);
    }
    public static boolean setCallMute(Context ctx, boolean value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CALL_MUTE, value); // value to store
        return editor.commit();
    }
}
