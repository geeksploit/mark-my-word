package me.geeksploit.markmyword.model.image.android;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import me.geeksploit.markmyword.R;
import me.geeksploit.markmyword.model.Icache;
import me.geeksploit.markmyword.model.image.GlideApp;
import me.geeksploit.markmyword.model.image.IImageLoader;
import me.geeksploit.markmyword.utils.NetworkStatus;

public class ImageLoaderGlider implements IImageLoader<ImageView> {

    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        GlideApp.with(container.getContext())
                .load(url)
                .placeholder(R.drawable.ic_image_grey_48dp)
                .into(container);
    }
}
