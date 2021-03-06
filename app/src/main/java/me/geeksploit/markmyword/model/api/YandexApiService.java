package me.geeksploit.markmyword.model.api;

import io.reactivex.Observable;
import me.geeksploit.markmyword.model.entity.Translation;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface YandexApiService {
    @POST("api/v1.5/tr.json/translate?lang=en-ru&key=" + ApiConst.API_KEY)
    Observable<Translation> getTraslation(@Query(ApiConst.QUERY_TEXT) String text);
}
