package com.tiramakan.simabf.bootstrap.util

import android.content.Context
import android.os.Build
import java.util.*

/**
 * Created by tiramakan on 17/01/2016.
 */
class MyEmulatorDetectorImpl(private val context: Context) : MyEmulatorDetector {

    override val isEmulator: Boolean
        get() {
            var isEmulator: Boolean? = false
            val fing = Build.FINGERPRINT
            if (fing != null) {
                isEmulator = fing.contains("vbox") || fing.contains("generic")
            }
            return isEmulator?:false
        }
    override val isSamsung: Boolean
        get() {
            var isSamsungDevice: Boolean? = false
            val fing = Build.FINGERPRINT
            println(fing)
            if (fing != null) {
                isSamsungDevice = fing.toUpperCase(Locale.FRANCE).contains("SAMSUNG")
            }
            return isSamsungDevice?:false
        }
    override val isTecno: Boolean
        get() {
            var isTecnoDevice: Boolean? = false
            val fing = Build.FINGERPRINT
            println(fing)
            if (fing != null) {
                isTecnoDevice = fing.toUpperCase(Locale.FRANCE).contains("TECNO")
            }
            return isTecnoDevice?:false
        }
    override val isInfinix: Boolean
        get() {
            var isInfinixDevice: Boolean? = false
            val fing = Build.FINGERPRINT
            if (fing != null) {
                isInfinixDevice = fing.toUpperCase(Locale.FRANCE).contains("INFINIX")
            }
            return isInfinixDevice?:false
        }
}
