package com.viethoa.potdemo.fakedatafactory;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by VietHoa on 12/11/2016.
 */

public class StreamUtils {

    private static final String TAG = StreamUtils.class.getSimpleName();

    private static final int BUFFER_SIZE = 1024;

    public static String convertStreamToString(InputStream in) throws IOException {
        try {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[BUFFER_SIZE];

            int n;
            while ((n = in.read(b)) != -1) {
                out.append(new String(b, 0, n));
            }

            String n1 = out.toString();
            return n1;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                Log.w(TAG, e);
            }

        }
    }

    public static String getStringFromAssetFile(Context applicationContext, String fileName) throws IOException {
        return convertStreamToString(applicationContext.getAssets().open(fileName));
    }
}
