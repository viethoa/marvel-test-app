package com.viethoa.potdemo.domain.services.character;

import com.viethoa.potdemo.domain.responses.CharacterApiResponse;
import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.models.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by VietHoa on 05/11/2016.
 */

public interface CharacterAPIs {

    @GET("public/characters")
    Call<CharacterApiResponse<List<Character>>> getCharacters(
            @Query("ts") long timestamp,
            @Query("apikey") String apiKey,
            @Query("hash") String hash
    );
}
