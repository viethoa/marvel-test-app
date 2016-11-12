package com.viethoa.potdemo.domain;

import android.content.Context;

import com.viethoa.potdemo.BuildConfig;
import com.viethoa.potdemo.domain.responses.CharacterApiResponse;
import com.viethoa.potdemo.domain.services.character.CharacterAPIs;
import com.viethoa.potdemo.fakedatafactory.NetworkFakeDataFactory;
import com.viethoa.potdemo.models.Character;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;

/**
 * Created by VietHoa on 12/11/2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CharacterAPIsTest extends BaseAPIsTest {

    @Inject
    Context applicationContext;
    @Inject
    CharacterAPIs characterAPIs;
    @Inject
    CustomInterceptor customInterceptor;

    @Override
    protected void init(NetworkComponentTest component) {
        component.inject(this);
    }

    @Test
    public void testGetCharactersSuccess() throws Exception {
        NetworkFakeDataFactory.getCharactersSuccessResponse(applicationContext, customInterceptor);
        CharacterApiResponse<List<Character>> response = characterAPIs.getCharacters(
                any(Long.class),
                any(String.class),
                any(String.class)
        ).execute().body();

        assertThat(response.getData().getResults().isEmpty(), is(false));
        assertThat(response.getData().getResults().size(), equalTo(3));
    }

    @Test
    public void testGetEmptyCharacter() throws Exception {
        NetworkFakeDataFactory.getEmptyCharacterResponse(applicationContext, customInterceptor);
        CharacterApiResponse<List<Character>> response = characterAPIs.getCharacters(
                any(Long.class),
                any(String.class),
                any(String.class)
        ).execute().body();

        assertThat(response.getData().getResults().isEmpty(), is(true));
    }

    @Test
    public void testGetCharacterError() throws Exception {
        NetworkFakeDataFactory.getCharacterErrorResponse(applicationContext, customInterceptor);
        CharacterApiResponse<List<Character>> response = characterAPIs.getCharacters(
                any(Long.class),
                any(String.class),
                any(String.class)
        ).execute().body();

        assertThat(response.getException(), notNullValue(Throwable.class));
    }
}
