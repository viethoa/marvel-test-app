package com.viethoa.potdemo.domain.character;

import android.content.Context;

import com.viethoa.potdemo.BuildConfig;
import com.viethoa.potdemo.domain.BaseDomainTest;
import com.viethoa.potdemo.domain.CustomInterceptor;
import com.viethoa.potdemo.domain.NetworkComponentTest;
import com.viethoa.potdemo.domain.responses.CharacterApiResponse;
import com.viethoa.potdemo.domain.services.character.CharacterService;
import com.viethoa.potdemo.domain.services.movie.MovieService;
import com.viethoa.potdemo.fakedatafactory.NetworkFakeDataFactory;
import com.viethoa.potdemo.models.Character;
import com.viethoa.potdemo.models.Movie;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;

/**
 * Created by VietHoa on 12/11/2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CharacterServiceTest extends BaseDomainTest {

    @Inject
    Context applicationContext;
    @Inject
    CharacterService characterService;
    @Inject
    CustomInterceptor customInterceptor;

    @Override
    protected void init(NetworkComponentTest component) {
        component.inject(this);
    }

    @Test
    public void testGetCharactersSuccess() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getCharactersSuccessResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<CharacterApiResponse<List<Character>>> testSubscriber = new TestSubscriber<>();
        characterService.getCharacters(any(Integer.class)).subscribe(testSubscriber);

        // Check API
        testSubscriber.assertCompleted();
        assertThat(testSubscriber.getOnNextEvents().size(), IsEqual.equalTo(1));

        // Check response
        CharacterApiResponse<List<Character>> response = testSubscriber.getOnNextEvents().get(0);
        assertThat(response, notNullValue(CharacterApiResponse.class));
        assertThat(response.getData(), notNullValue(CharacterApiResponse.Data.class));
        assertThat(response.getData().getResults().isEmpty(), equalTo(false));
        assertThat(response.getData().getResults().size(), equalTo(3));
        assertThat(response.getData().getResults().get(0), notNullValue(Character.class));

        // Check data
        Character character = response.getData().getResults().get(0);
        assertThat(character.getId(), equalTo(1011334L));
        assertThat(character.getName(), equalTo("3-D Man"));
        assertThat(character.getDescription(), equalTo(""));
    }

    @Test
    public void testGetEmptyCharacter() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getEmptyCharacterResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<CharacterApiResponse<List<Character>>> testSubscriber = new TestSubscriber<>();
        characterService.getCharacters(any(Integer.class)).subscribe(testSubscriber);

        // Check API
        testSubscriber.assertCompleted();
        assertThat(testSubscriber.getOnNextEvents().size(), IsEqual.equalTo(1));

        // Check response
        CharacterApiResponse<List<Character>> response = testSubscriber.getOnNextEvents().get(0);
        assertThat(response, notNullValue(CharacterApiResponse.class));
        assertThat(response.getData(), notNullValue(CharacterApiResponse.Data.class));
        assertThat(response.getData().getResults().isEmpty(), equalTo(true));
    }

    @Test
    public void testGetCharacterError() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getCharacterErrorResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<CharacterApiResponse<List<Character>>> testSubscriber = new TestSubscriber<>();
        characterService.getCharacters(any(Integer.class)).subscribe(testSubscriber);

        // Check exception
        testSubscriber.assertError(Throwable.class);
    }
}
