package me.geeksploit.markmyword.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.geeksploit.markmyword.model.api.YandexApiConst;
import me.geeksploit.markmyword.model.api.YandexApiService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class ApiModule {
    @Provides
    YandexApiService apiService(Retrofit retrofit){
        return retrofit.create(YandexApiService.class);
    }

    @Provides
    Retrofit retrofit(GsonConverterFactory gson){
        return new Retrofit.Builder()
                .baseUrl(YandexApiConst.YANDEX_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gson)
                .build();
    }

    @Provides
    GsonConverterFactory gson(){
        return GsonConverterFactory.create();
    }
}
