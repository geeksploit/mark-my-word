package me.geeksploit.markmyword.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.geeksploit.markmyword.model.api.YandexApiService;
import me.geeksploit.markmyword.model.db.WordsDb;
import me.geeksploit.markmyword.model.repository.WordsRepository;

@Singleton
@Module(includes = {RoomModule.class, ApiModule.class})
public class WordRepoModule {
    @Provides
    WordsRepository wordsRepository(WordsDb dataBase, YandexApiService apiService){
        return new WordsRepository(dataBase, apiService);
    }
}
