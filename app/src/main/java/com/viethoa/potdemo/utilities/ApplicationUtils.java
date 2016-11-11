package com.viethoa.potdemo.utilities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by VietHoa on 06/08/2016.
 */

public class ApplicationUtils {

    public static String getAppPackageName(Context context) {
        Context applicationText = context.getApplicationContext();
        return applicationText.getPackageName();
    }

    public static int getAppVersionCode(Context context) {
        Context applicationContext = context.getApplicationContext();

        try {
            PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(
                    applicationContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        } catch (Exception e) {
            // should never happen
            throw new RuntimeException("Unknown expected exception in getAppVersion: " + e);
        }
    }

    public static String getAppVersionName(Context context) {
        Context applicationContext = context.getApplicationContext();

        try {
            PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(
                    applicationContext.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        } catch (Exception e) {
            // should never happen
            throw new RuntimeException("Unknown expected exception in getAppVersion: " + e);
        }
    }
}
