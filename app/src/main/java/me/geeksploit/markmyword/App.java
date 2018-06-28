package me.geeksploit.markmyword;

import android.app.Application;

import me.geeksploit.markmyword.di.AppComponent;
import me.geeksploit.markmyword.di.DaggerAppComponent;
import me.geeksploit.markmyword.di.modules.AppModule;

public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
