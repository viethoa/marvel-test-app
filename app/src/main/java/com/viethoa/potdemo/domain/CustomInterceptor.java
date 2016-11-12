package com.viethoa.potdemo.domain;

import android.content.Context;
import android.text.TextUtils;

import com.viethoa.potdemo.utilities.ApplicationUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by VietHoa on 05/11/2016.
 */
public class CustomInterceptor implements Interceptor {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String AUTHORIZATION_KEY = "Auth-Token";
    private static final String APP_VERSION = "App-Version";
    private static final String APP_PLATFORM = "Platform";
    private static final String NO_CONTENT = "{}";

    private String responseString;
    private Context applicationContext;

    public CustomInterceptor(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private Response mockResponse(Chain chain) {
        return new Response.Builder()
                .code(200)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
    }

    public void setResponseString(String reponseString) {
        responseString = reponseString;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Only for unit test
        if (!TextUtils.isEmpty(responseString)) {
            return mockResponse(chain);
        }

        // real working flow come from here
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .addHeader(CONTENT_TYPE, "application/json")
                .addHeader(APP_VERSION, ApplicationUtils.getAppVersionName(applicationContext))
                .addHeader(APP_PLATFORM, "android")
                .addHeader(AUTHORIZATION_KEY, "");
        request = builder.build();
        Response response = chain.proceed(request);

        // Authentication problem
        if (response.code() == 403) {
            //TODO: handle kick-out user
        }

        // Error from server
        if (response.code() == 400) {
            Response.Builder errorBuilder = response.newBuilder();
            errorBuilder.code(200);  //handle by BaseResponse
            return errorBuilder.build();
        }

        // No content response
        if (response.code() == 204) {
            Response.Builder noContentBuilder = response.newBuilder();
            noContentBuilder.code(200);
            noContentBuilder.body(ResponseBody.create(MediaType.parse("application/json"), NO_CONTENT));
            return noContentBuilder.build();
        }

        return response;
    }
}