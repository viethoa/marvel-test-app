package com.viethoa.potdemo.uis.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.uis.main.characterFragment.CharacterFragment;
import com.viethoa.potdemo.uis.main.characterfavoritefragment.CharacterFavoriteFragment;
import com.viethoa.potdemo.uis.main.moviefavouritefragment.MovieFavouriteFragment;
import com.viethoa.potdemo.uis.main.moviefragment.MovieFragment;

/**
 * Created by VietHoa on 05/11/2016.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_OF_PAGE = 4;
    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return NUM_OF_PAGE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new MovieFavouriteFragment();
                break;
            case 2:
                fragment = new CharacterFragment();
                break;
            default:
                fragment = new CharacterFavoriteFragment();
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.main_now_playing);
            case 1:
                return context.getResources().getString(R.string.main_movies_favourite);
            case 2:
                return context.getResources().getString(R.string.main_characters);
            default:
                return context.getResources().getString(R.string.main_characters_favorite);
        }
    }
}
