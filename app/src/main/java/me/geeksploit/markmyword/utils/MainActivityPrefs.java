package me.geeksploit.markmyword.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import me.geeksploit.markmyword.model.entity.prefs.MainPrefsEntity;
import me.geeksploit.markmyword.model.entity.prefs.PrefsMarker;

public class MainActivityPrefs implements IPrefsController {
    private Context context;
    private SharedPreferences prefs;
    private Gson gson;

    public MainActivityPrefs(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PreferenceFileds.PREFS, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    @Override
    public PrefsMarker restoreUI() {
        String json = "{}";
        if (prefs.contains(PreferenceFileds.MAIN_ACTIVITY_STATE))
            json = prefs.getString(PreferenceFileds.MAIN_ACTIVITY_STATE, "{}");
        return gson.fromJson(json, MainPrefsEntity.class);
    }

    @Override
    public void saveUIState(PrefsMarker prefsEntity) {
        if (prefsEntity instanceof MainPrefsEntity) {
            MainPrefsEntity mainPrefs = (MainPrefsEntity) prefsEntity;
            String json = gson.toJson(mainPrefs);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PreferenceFileds.MAIN_ACTIVITY_STATE, json);
            editor.apply();
        }
    }
}
