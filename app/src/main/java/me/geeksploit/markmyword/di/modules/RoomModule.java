package me.geeksploit.markmyword.di.modules;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.geeksploit.markmyword.App;
import me.geeksploit.markmyword.model.db.WordsDb;

@Singleton
@Module (includes = AppModule.class)
public class RoomModule {
    @Provides
    WordsDb wordsDb(App app){
        return Room.databaseBuilder(app.getApplicationContext(), WordsDb.class, "wordsDB").build();
    }
}
