package com.example.test.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

/**
 * Created by Android.Developer.
 *
 * @version 1.0
 */
public class LocaleHelper {

    public static Context onAttach(Context context) {
        String locale = getPersistedLocale(context);
        return setLocale(context, locale);
    }

    private static String getPersistedLocale(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("friesland_pref", Context.MODE_PRIVATE);
        return preferences.getString("PREF_KEY_LOCALE", "en");
    }

    /**
     * Set the app's locale to the one specified by the given String.
     *
     * @param context    context of current view
     * @param localeSpec a locale specification as used for Android resources (NOTE: does not
     *                   support country and variant codes so far); the special string "system" sets
     *                   the locale to the locale specified in system settings
     */
    public static Context setLocale(Context context, String localeSpec) {
        Locale locale;
        if (localeSpec.equals("system")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = Resources.getSystem().getConfiguration().getLocales().get(0);
            } else {
                locale = Resources.getSystem().getConfiguration().locale;
            }
        } else {
            locale = new Locale(localeSpec);
        }
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, locale);
        } else {
            return updateResourcesLegacy(context, locale);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    private static Context updateResourcesLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();

        Configuration configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}
