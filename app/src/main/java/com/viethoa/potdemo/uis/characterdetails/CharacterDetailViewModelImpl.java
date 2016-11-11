package com.viethoa.potdemo.uis.characterdetails;

import android.content.Context;
import android.text.TextUtils;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseViewModel;
import com.viethoa.potdemo.caches.CharacterFavoriteMemoryCache;
import com.viethoa.potdemo.models.Character;
import com.viethoa.potdemo.models.Thumbnail;
import com.viethoa.potdemo.utilities.GlideUtils;

import java.text.NumberFormat;

/**
 * Created by VietHoa on 10/11/2016.
 */

public class CharacterDetailViewModelImpl extends BaseViewModel<CharacterDetailViewModel.Listener> implements CharacterDetailViewModel {

    private Context applicationContext;
    private CharacterFavoriteMemoryCache favoriteMemoryCache;

    public CharacterDetailViewModelImpl(Context applicationContext, CharacterFavoriteMemoryCache favoriteMemoryCache) {
        this.applicationContext = applicationContext;
        this.favoriteMemoryCache = favoriteMemoryCache;
    }

    @Override
    public void onViewCreated(Character character) {
        if (listener == null || character == null) {
            return;
        }

        // Name
        String name = character.getName();
        if (!TextUtils.isEmpty(name)) {
            listener.setTitle(name);
        }

        // Description
        String description = character.getDescription();
        if (!TextUtils.isEmpty(description)) {
            listener.setDescription(description);
        } else {
            listener.setDescription(applicationContext.getString(R.string.na));
        }

        // Favorite
        if (favoriteMemoryCache.contains(character)) {
            listener.setFavoriteCharacter();
        } else {
            listener.setNonFavoriteCharacter();
        }

        // Thumbnail
        Thumbnail thumbnail = character.getThumbnail();
        if (thumbnail != null) {
            listener.setImages(thumbnail.getThumbnailUrl());
        }
    }

    @Override
    public void actionFavoriteClicked(Character character) {
        if (listener == null || character == null) {
            return;
        }

        boolean isRemoved = favoriteMemoryCache.remove(character);
        if (isRemoved) {
            listener.setNonFavoriteCharacter();
        } else {
            favoriteMemoryCache.set(character);
            listener.setFavoriteCharacter();
        }
    }
}
