package com.tiramakan.simabf.bootstrap.util

import java.util.ArrayList

/**
 * Created by tiramakan on 24/06/2017.
 */

object StrUtil {

    fun join(vector: ArrayList<String>, substr: String): String {
        if (vector.size > 0) {
            val buffer = StringBuilder(vector[0])
            for (i in 1 until vector.size) {
                buffer.append(substr)
                buffer.append(vector[i])
            }
            return buffer.toString()
        } else
            return ""
    }
}
