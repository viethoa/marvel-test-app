package com.viethoa.potdemo.caches;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viethoa.potdemo.models.Character;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VietHoa on 10/11/2016.
 */

public class CharacterFavoriteMemoryCache {
    private static final String PREF_KEY_CACHE_CHARACTER = "characters-memory-cache";
    private static List<Character> cacheCharacters;
    private SharedPreferences sharedPreferences;

    public CharacterFavoriteMemoryCache(Context context) {
        sharedPreferences = context.getSharedPreferences("pref_characters_memory_data", Context.MODE_PRIVATE);
        cacheCharacters = get();
    }

    public synchronized void clear() {
        cacheCharacters = null;
        sharedPreferences.edit().putString(PREF_KEY_CACHE_CHARACTER, "").apply();
        sharedPreferences.edit().clear().apply();
    }

    //----------------------------------------------------------------------------------------------
    // Character
    //----------------------------------------------------------------------------------------------

    public synchronized void set(Character character) {
        if (character == null || cacheCharacters.contains(character)) {
            return;
        }

        // memory cache
        cacheCharacters.add(character);

        String json = (new Gson()).toJson(cacheCharacters);
        if (TextUtils.isEmpty(json)) {
            return;
        }

        sharedPreferences.edit().putString(PREF_KEY_CACHE_CHARACTER, json).apply();
    }

    public synchronized void set(List<Character> characters) {
        if (characters == null) {
            return;
        }

        // memory cache
        cacheCharacters = characters;

        String json = (new Gson()).toJson(cacheCharacters);
        if (TextUtils.isEmpty(json)) {
            return;
        }

        sharedPreferences.edit().putString(PREF_KEY_CACHE_CHARACTER, json).apply();
    }

    public synchronized List<Character> get() {
        if (cacheCharacters != null) {
            return cacheCharacters;
        }

        List<Character> characters = null;
        Type type = new TypeToken<List<Character>>() {
        }.getType();
        String jsonData = sharedPreferences.getString(PREF_KEY_CACHE_CHARACTER, "");

        //Convert back to User data model
        try {
            characters = (new Gson()).fromJson(jsonData, type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (characters == null) {
                characters = new ArrayList<>();
            }
        }

        cacheCharacters = characters;
        return cacheCharacters;
    }

    public synchronized boolean contains(Character character) {
        if (character == null) {
            return false;
        }

        List<Character> characters = get();
        if (characters == null || characters.size() == 0) {
            return false;
        }

        return characters.contains(character);
    }

    public synchronized boolean remove(Character character) {
        if (character == null) {
            return false;
        }
        List<Character> characters = get();
        if (characters == null || characters.size() == 0) {
            return false;
        }
        if (!characters.contains(character)) {
            return false;
        }

        characters.remove(character);
        set(characters);
        return true;
    }
}
