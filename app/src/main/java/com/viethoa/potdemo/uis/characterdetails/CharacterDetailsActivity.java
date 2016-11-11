package com.viethoa.potdemo.uis.characterdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseSnackBarActivity;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.di.characterdetails.CharacterDetailComponent;
import com.viethoa.potdemo.di.characterdetails.CharacterDetailModule;
import com.viethoa.potdemo.di.characterdetails.DaggerCharacterDetailComponent;
import com.viethoa.potdemo.models.Character;
import com.viethoa.potdemo.models.Movie;
import com.viethoa.potdemo.utilities.GlideUtils;

import javax.inject.Inject;

import butterknife.Bind;

public class CharacterDetailsActivity extends BaseSnackBarActivity implements CharacterDetailViewModel.Listener {
    private static final String EXTRACT_CHARACTER = "character-extract";
    private Character character;
    private MenuItem actionFavorite;
    private CharacterDetailComponent detailComponent;

    @Inject
    CharacterDetailViewModel characterDetailViewModel;

    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.img_thumbnail)
    ImageView imgThumbnail;

    public static Intent newInstance(Context context, Character character) {
        Intent intent = new Intent(context, CharacterDetailsActivity.class);
        intent.putExtra(EXTRACT_CHARACTER, character);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        showToolbarBackIcon();
        setToolBarTitle("");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }

        // Get bundle data
        character = (Character) bundle.getSerializable(EXTRACT_CHARACTER);

        // Init viewModel
        characterDetailViewModel.initialize(this);
        characterDetailViewModel.onViewCreated(character);
    }

    //----------------------------------------------------------------------------------------------
    // Menu items
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        actionFavorite = menu.findItem(R.id.actions_favorite);
        characterDetailViewModel.onViewCreated(character);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actions_favorite:
                characterDetailViewModel.actionFavoriteClicked(character);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------------------------------
    // Injection
    //----------------------------------------------------------------------------------------------

    protected void injectModule(ApplicationComponent appComponent) {
        detailComponent = DaggerCharacterDetailComponent.builder()
                .applicationComponent(appComponent)
                .characterDetailModule(new CharacterDetailModule())
                .build();
        detailComponent.inject(this);
    }

    public BaseComponent getComponent() {
        return detailComponent;
    }

    //----------------------------------------------------------------------------------------------
    // ViewModel events
    //----------------------------------------------------------------------------------------------

    @Override
    public void setTitle(String title) {
        tvName.setText(title);
    }

    @Override
    public void setDescription(String description) {
        tvDescription.setText(description);
    }

    @Override
    public void setImages(String thumbnailUrl) {
        GlideUtils.loadImage(this, thumbnailUrl, imgThumbnail, R.drawable.placeholder_image);
    }

    @Override
    public void setFavoriteCharacter() {
        if (actionFavorite != null) {
            actionFavorite.setIcon(getResources().getDrawable(R.mipmap.ic_favourite));
        }
    }

    @Override
    public void setNonFavoriteCharacter() {
        if (actionFavorite != null) {
            actionFavorite.setIcon(getResources().getDrawable(R.mipmap.ic_not_favorite));
        }
    }
}
