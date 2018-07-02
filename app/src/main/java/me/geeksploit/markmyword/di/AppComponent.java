package me.geeksploit.markmyword.di;

import javax.inject.Singleton;

import dagger.Component;
import me.geeksploit.markmyword.MainActivity;
import me.geeksploit.markmyword.di.modules.AppModule;
import me.geeksploit.markmyword.di.modules.ImageGlideModule;
import me.geeksploit.markmyword.di.modules.RoomModule;
import me.geeksploit.markmyword.di.modules.WordRepoModule;
import me.geeksploit.markmyword.model.parser.TxtParser;
import me.geeksploit.markmyword.presenter.MainPresenter;
import me.geeksploit.markmyword.presenter.ParsePresenter;
import me.geeksploit.markmyword.view.adapters.WordRvAdapter;

@Singleton
@Component(modules = {AppModule.class, ImageGlideModule.class, RoomModule.class, WordRepoModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(MainPresenter presenter);
    void inject(TxtParser parser);
    void inject(WordRvAdapter adapter);
}
