package me.geeksploit.markmyword.model.image;

import android.support.annotation.Nullable;

public interface IImageLoader<T> {
    void loadInto(@Nullable String url, T container);
}
