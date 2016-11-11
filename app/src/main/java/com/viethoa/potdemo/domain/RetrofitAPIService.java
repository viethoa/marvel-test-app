package com.viethoa.potdemo.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viethoa.potdemo.BuildConfig;
import com.viethoa.potdemo.domain.services.character.CharacterAPIs;
import com.viethoa.potdemo.domain.services.movie.MovieAPIs;
import com.viethoa.potdemo.domain.services.movie.MovieService;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by VietHoa on 05/11/2016.
 */
public class RetrofitAPIService {

    private MovieAPIs movieAPIs;
    private CharacterAPIs characterAPIs;

    @Inject
    public RetrofitAPIService(CustomInterceptor customInterceptor) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson GSON = new GsonBuilder()
                .disableHtmlEscaping()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(customInterceptor)
                .addInterceptor(logger)
                .build();

        // Movie only
        Retrofit movieRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.MOVIE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        movieAPIs = movieRetrofit.create(MovieAPIs.class);

        // Character only
        Retrofit characterRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.CHARACTER_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        characterAPIs = characterRetrofit.create(CharacterAPIs.class);
    }

    public MovieAPIs getMovieAPIs() {
        return movieAPIs;
    }

    public CharacterAPIs getCharacterAPIs() {
        return characterAPIs;
    }
}
