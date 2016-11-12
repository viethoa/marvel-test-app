package com.viethoa.potdemo.fakedatafactory;

import android.content.Context;

import com.viethoa.potdemo.domain.CustomInterceptor;

import java.io.IOException;

/**
 * Created by VietHoa on 12/11/2016.
 */

public class NetworkFakeDataFactory {

    private static void fakeBody(CustomInterceptor customInterceptor, Context applicationContext,
                                 String fileName) throws IOException {
        customInterceptor.setResponseString(StreamUtils.getStringFromAssetFile(
                applicationContext, "responses/" + fileName));
    }

    /**
     *  Fake data for Movie APIs
     */

    public static void getMoviesSuccessResponse(Context applicationContext, CustomInterceptor customInterceptor) throws IOException {
        fakeBody(customInterceptor, applicationContext, "get_movies_success");
    }

    public static void getEmptyMovieResponse(Context applicationContext, CustomInterceptor customInterceptor) throws IOException {
        fakeBody(customInterceptor, applicationContext, "get_empty_movie");
    }

    public static void getMoviesErrorResponse(Context applicationContext, CustomInterceptor customInterceptor) throws IOException {
        fakeBody(customInterceptor, applicationContext, "error_response");
    }

    /**
     *  Fake data for Character APIs
     */

    public static void getCharactersSuccessResponse(Context applicationContext, CustomInterceptor customInterceptor) throws IOException {
        fakeBody(customInterceptor, applicationContext, "get_characters_success");
    }

    public static void getEmptyCharacterResponse(Context applicationContext, CustomInterceptor customInterceptor) throws IOException {
        fakeBody(customInterceptor, applicationContext, "get_empty_character");
    }

    public static void getCharacterErrorResponse(Context applicationContext, CustomInterceptor customInterceptor) throws IOException {
        fakeBody(customInterceptor, applicationContext, "error_response");
    }
}
