package com.viethoa.potdemo.utilities;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by VietHoa on 14/09/2016.
 */
public class SoundUtils {

    public static void makeNotificationSound(Context context) {
        try {
            Context applicationContext = context.getApplicationContext();
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(applicationContext, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
