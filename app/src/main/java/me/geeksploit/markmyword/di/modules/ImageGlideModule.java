package me.geeksploit.markmyword.di.modules;

import android.widget.ImageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.geeksploit.markmyword.model.image.IImageLoader;
import me.geeksploit.markmyword.model.image.android.ImageLoaderGlider;

@Singleton
@Module
public class ImageGlideModule {
    @Provides
    IImageLoader<ImageView> imageLoader(){
        return new ImageLoaderGlider();
    }
}