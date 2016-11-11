package com.viethoa.potdemo.caches;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by VietHoa on 17/01/16.
 */
public class CacheManager {
    private static final String TAG = CacheManager.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public CacheManager(Context applicationContext) {
        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void put(String key, Object object) {
        if (object == null) {
            mEditor.putString(key, "");
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(object, object.getClass());
            mEditor.putString(key, json);
        }

        mEditor.apply();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public <T> T get(String key, Class<T> clazz) {
        String json = mSharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(json))
            return null;

        try {
            Gson gson = new Gson();
            T object = gson.fromJson(json, clazz);
            return object;
        } catch (Exception ex) {
            return null;
        }
    }

    public <T> T get(String key, Type type) {
        String json = mSharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(json))
            return null;

        try {
            Gson gson = new Gson();
            T object = gson.fromJson(json, type);
            return object;
        } catch (Exception ex) {
            return null;
        }
    }
}
