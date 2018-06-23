package geekgram.supernacho.ru.model.image;

import android.support.annotation.Nullable;

public interface IImageLoader<T> {
    void loadInto(@Nullable String url, T container);
    void loadIntoFromNet(@Nullable String url, T container);
}
