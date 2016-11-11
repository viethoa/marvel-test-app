package com.viethoa.potdemo.uis.main.characterfavoritefragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseFragment;
import com.viethoa.potdemo.caches.CharacterFavoriteMemoryCache;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.di.main.MainComponent;
import com.viethoa.potdemo.models.Character;
import com.viethoa.potdemo.uis.main.characterFragment.CharacterAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by VietHoa on 10/11/2016.
 */

public class CharacterFavoriteFragment extends BaseFragment {

    private CharacterAdapter characterAdapter;
    private List<Character> characterArrayList;

    @Inject
    CharacterFavoriteMemoryCache favoriteMemoryCache;

    @Bind(R.id.tv_no_data)
    TextView tvNoDataView;
    @Bind(R.id.grid_view)
    GridView gridViewFavoriteMovie;

    @Override
    protected void injectComponent(BaseComponent component) {
        if (component instanceof MainComponent) {
            ((MainComponent) component).inject(this);
        }
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_movie_favourites, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialiseView();
    }

    public void initialiseView() {
        characterArrayList = favoriteMemoryCache.get();
        characterAdapter = new CharacterAdapter(getContext(), characterArrayList);
        gridViewFavoriteMovie.setAdapter(characterAdapter);

        if (characterArrayList == null || characterArrayList.size() == 0) {
            tvNoDataView.setVisibility(View.VISIBLE);
        } else {
            tvNoDataView.setVisibility(View.GONE);
        }
    }
}
