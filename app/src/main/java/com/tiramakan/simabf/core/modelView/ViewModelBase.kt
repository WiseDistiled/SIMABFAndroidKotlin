package com.tiramakan.simabf.core.modelView

import android.content.Context
import android.widget.ArrayAdapter

import java.util.ArrayList

/**
 * Created by tiramakan on 14/02/2016.
 */
abstract class ViewModelBase {
    internal fun getStringAdapter(context: Context, items: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(context,
                android.R.layout.simple_list_item_1, items)
    }

    companion object {
        fun <T> copyList(source: List<T>): List<T> {
            val dest = ArrayList<T>()
            for (item in source) {
                dest.add(item)
            }
            return dest
        }

        fun <T> copyList(source: ArrayList<T>): ArrayList<T> {
            val dest = ArrayList<T>()
            for (item in source) {
                dest.add(item)
            }
            return dest
        }
    }
}
