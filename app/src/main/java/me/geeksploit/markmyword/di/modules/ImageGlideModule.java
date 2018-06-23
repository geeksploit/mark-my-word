package me.geeksploit.markmyword.di.modules;

import android.widget.ImageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.geeksploit.markmyword.model.Icache;
import me.geeksploit.markmyword.model.image.IImageLoader;
import me.geeksploit.markmyword.model.image.android.ImageLoaderGlider;

@Singleton
@Module (includes = IcacheModule.class)
public class ImageGlideModule {
    @Provides
    IImageLoader<ImageView> imageLoader(Icache cache){
        return new ImageLoaderGlider(cache);
    }
}
