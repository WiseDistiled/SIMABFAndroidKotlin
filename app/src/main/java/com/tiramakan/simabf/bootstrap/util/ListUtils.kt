package com.tiramakan.simabf.bootstrap.util

/**
 * Created by tiramakan on 24/06/2017.
 */

object ListUtils {
    interface Filter<T> {
        fun keepItem(item: T): Boolean
    }

    fun <T> filter(items: MutableList<T>, filter: Filter<T>) {
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            if (!filter.keepItem(iterator.next())) {
                iterator.remove()
            }
        }
    }
}