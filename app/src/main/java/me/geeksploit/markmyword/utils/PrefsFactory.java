package me.geeksploit.markmyword.utils;

import android.content.Context;

import me.geeksploit.markmyword.MainActivity;

public class PrefsFactory {
    public static IPrefsController getPrefs(Context context){
        IPrefsController controller;
        if (context instanceof MainActivity){
            controller =  new MainActivityPrefs(context);
        } else {
            throw new RuntimeException("No such instance on Activity, probably yo forgot add it here");
        }
        return controller;
    }
}
