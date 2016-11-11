package com.viethoa.potdemo.domain.services.character;

import com.viethoa.potdemo.domain.responses.CharacterApiResponse;
import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.domain.services.BaseServices;
import com.viethoa.potdemo.models.Character;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.viethoa.potdemo.Constants.MARVEL_PRIVATE_KEY;
import static com.viethoa.potdemo.Constants.MARVEL_PUBLIC_KEY;

/**
 * Created by VietHoa on 05/11/2016.
 */

public class CharacterServiceImpl extends BaseServices implements CharacterService {

    @Inject
    CharacterAPIs characterAPIs;

    @Inject
    public CharacterServiceImpl() {
        // Requirement
    }

    @Override
    public Observable<CharacterApiResponse<List<Character>>> getCharacters(int page) {
        long timestamp = System.currentTimeMillis();
        String hash = MD5(timestamp + MARVEL_PRIVATE_KEY + MARVEL_PUBLIC_KEY);
        return Observable.create(subscriber -> {
            handleResponse(characterAPIs.getCharacters(timestamp, MARVEL_PUBLIC_KEY, hash), null, subscriber);
        });
    }

    private String MD5(String hash) {
        final MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(hash.getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, resultByte);
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
