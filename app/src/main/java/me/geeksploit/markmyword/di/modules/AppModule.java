package me.geeksploit.markmyword.di.modules;

import dagger.Module;
import dagger.Provides;
import me.geeksploit.markmyword.App;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public App app(){
        return app;
    }
}
