package com.viethoa.potdemo.uis.characterdetails;

import com.viethoa.potdemo.base.BaseView;
import com.viethoa.potdemo.models.Character;

/**
 * Created by VietHoa on 10/11/2016.
 */

public interface CharacterDetailViewModel {

    interface Listener extends BaseView {
        void setTitle(String title);

        void setDescription(String description);

        void setImages(String thumbnailUrl);

        void setFavoriteCharacter();

        void setNonFavoriteCharacter();
    }

    void initialize(Listener listener);

    void onViewCreated(Character character);

    void actionFavoriteClicked(Character character);

    void onDestroy();
}
