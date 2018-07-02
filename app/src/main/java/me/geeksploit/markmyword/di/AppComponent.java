package me.geeksploit.markmyword.di;

import android.arch.persistence.room.RoomDatabase;

import javax.inject.Singleton;

import dagger.Component;
import me.geeksploit.markmyword.MainActivity;
import me.geeksploit.markmyword.di.modules.AppModule;
import me.geeksploit.markmyword.di.modules.ImageGlideModule;
import me.geeksploit.markmyword.di.modules.RoomModule;
import me.geeksploit.markmyword.model.db.WordsDb;
import me.geeksploit.markmyword.presenter.MainPresenter;
import me.geeksploit.markmyword.view.adapters.WordRvAdapter;

@Singleton
@Component(modules = {AppModule.class, ImageGlideModule.class, RoomModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(MainPresenter presenter);
    void inject(WordRvAdapter adapter);
}
