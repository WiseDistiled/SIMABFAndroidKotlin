package com.tiramakan.simabf.bootstrap

import android.accounts.AccountManager
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.view.inputmethod.InputMethodManager

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Module for all Android related provisions
 */
@Module
class AndroidModule {

    @Provides
    @Singleton
    internal fun provideAppContext(): Context {
        return BootstrapApplication.instance.applicationContext
    }

    @Provides
    internal fun provideDefaultSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    internal fun providePackageInfo(context: Context): PackageInfo {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }

    }

    @Provides
    internal fun provideTelephonyManager(context: Context): TelephonyManager {
        return getSystemService(context, Context.TELEPHONY_SERVICE)
    }

    fun <T> getSystemService(context: Context, serviceConstant: String): T {
        return context.getSystemService(serviceConstant) as T
    }

    @Provides
    internal fun provideInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @Provides
    internal fun provideLocationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }


    @Provides
    internal fun provideApplicationInfo(context: Context): ApplicationInfo {
        return context.applicationInfo
    }

    @Provides
    internal fun provideAccountManager(context: Context): AccountManager {
        return AccountManager.get(context)
    }

    @Provides
    internal fun provideClassLoader(context: Context): ClassLoader {
        return context.classLoader
    }

    @Provides
    internal fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}
