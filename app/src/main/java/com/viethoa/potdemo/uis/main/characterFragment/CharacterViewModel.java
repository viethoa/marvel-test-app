package com.viethoa.potdemo.uis.main.characterFragment;

import com.viethoa.potdemo.base.BaseView;
import com.viethoa.potdemo.models.Character;

import java.util.List;

/**
 * Created by VietHoa on 09/11/2016.
 */

public interface CharacterViewModel {

    interface Listener extends BaseView {
        void onAPIErrorResponse(String errorMessage);

        void onGetCharactersSuccess(List<Character> movies);
    }

    void initialize(Listener listener);

    void getCharacters(int page);

    void onDestroy();
}
