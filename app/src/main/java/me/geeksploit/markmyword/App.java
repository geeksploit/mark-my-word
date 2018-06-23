import android.app.Application;

import me.geeksploit.markmyword.di.AppComponent;

public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
