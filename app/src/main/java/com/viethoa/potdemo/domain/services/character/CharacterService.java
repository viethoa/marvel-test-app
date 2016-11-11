package com.viethoa.potdemo.domain.services.character;

import com.viethoa.potdemo.domain.responses.CharacterApiResponse;
import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.models.Character;

import java.util.List;

import rx.Observable;

/**
 * Created by VietHoa on 05/11/2016.
 */

public interface CharacterService {

    Observable<CharacterApiResponse<List<Character>>> getCharacters(int page);
}
