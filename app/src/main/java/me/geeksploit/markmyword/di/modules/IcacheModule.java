package me.geeksploit.markmyword.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.geeksploit.markmyword.model.Icache;
import me.geeksploit.markmyword.model.ImageCache;

@Singleton
@Module
public class IcacheModule {
    @Provides
    Icache imageCache(){
        return new ImageCache();
    }
}
