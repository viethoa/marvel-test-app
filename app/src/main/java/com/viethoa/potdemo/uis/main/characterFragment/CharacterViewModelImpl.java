package com.viethoa.potdemo.uis.main.characterFragment;

import com.viethoa.potdemo.base.BaseViewModel;
import com.viethoa.potdemo.base.BriefObserver;
import com.viethoa.potdemo.domain.responses.CharacterApiResponse;
import com.viethoa.potdemo.domain.services.character.CharacterService;
import com.viethoa.potdemo.models.Character;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VietHoa on 09/11/2016.
 */

public class CharacterViewModelImpl extends BaseViewModel<CharacterViewModel.Listener> implements CharacterViewModel {

    private final CharacterService characterService;

    public CharacterViewModelImpl(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Override
    public void getCharacters(int page) {
        if (listener == null) {
            return;
        }

        manageSubscription(characterService.getCharacters(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BriefObserver<CharacterApiResponse<List<Character>>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onAPIErrorResponse(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(CharacterApiResponse<List<Character>> response) {
                        if (listener != null) {
                            listener.onGetCharactersSuccess(response.getData().getResults());
                        }
                    }
                }));
    }
}
