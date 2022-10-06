package com.tiramakan.simabf.bootstrap.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService



/**
 * Created by tiramakan on 17/01/2016.
 */
class MyNetworkDetectorImpl(private val context: Context) : MyNetworkDetector {

    override val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}
