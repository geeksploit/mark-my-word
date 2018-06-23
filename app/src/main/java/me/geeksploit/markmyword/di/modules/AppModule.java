package geekgram.supernacho.ru.di.modules;

import dagger.Module;
import dagger.Provides;
import geekgram.supernacho.ru.App;

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
