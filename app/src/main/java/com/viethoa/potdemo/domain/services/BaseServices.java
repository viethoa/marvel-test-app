package com.viethoa.potdemo.domain.services;

import android.content.Context;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.domain.responses.BaseResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Subscriber;

/**
 * Created by VietHoa on 05/11/2016.
 */
public abstract class BaseServices {
    private static final String UNKNOWN_ERROR = "Unknown Error!";

    @Inject
    public Context mAppContext;

    public interface InternalProcess<T extends BaseResponse> {

        void processInternally(T response);
    }

    protected void handleResponse(Call<? extends BaseResponse> call, InternalProcess handler, Subscriber subscriber) {
        try {
            // UnSubscribed
            if (subscriber == null || subscriber.isUnsubscribed()) {
                return;
            }

            // Unknown error
            BaseResponse response = call.execute().body();
            if (response == null) {
                subscriber.onError(new Exception(UNKNOWN_ERROR));
                subscriber.onCompleted();
                return;
            }

            // Process internally
            if (handler != null) {
                handler.processInternally(response);
            }

            // Server response error
            if (response.isError()) {
                subscriber.onError(response.getException());
                subscriber.onCompleted();
                return;
            }

            subscriber.onNext(response);
            subscriber.onCompleted();
        } catch (Exception e) {
            if (subscriber.isUnsubscribed()) {
                return;
            }

            // Process internally
            if (handler != null) {
                handler.processInternally(null);
            }

            if (e instanceof IOException) {
                subscriber.onError(new Exception(mAppContext.getString(R.string.no_internet_connection)));
                subscriber.onCompleted();
            } else if (e.getCause() instanceof SocketTimeoutException) {
                subscriber.onError(new Exception(mAppContext.getString(R.string.request_time_out)));
                subscriber.onCompleted();
            } else {
                subscriber.onError(e);
                subscriber.onCompleted();
            }
        }
    }
}
