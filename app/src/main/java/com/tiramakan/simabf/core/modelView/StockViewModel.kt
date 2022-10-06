package com.tiramakan.simabf.core.modelView

import android.app.Activity
import android.widget.ArrayAdapter
import java.util.*
import com.tiramakan.simabf.ui.view.utils.Binding.*
/**
 * Created by tiramakan on 14/02/2016.
 */
abstract class StockViewModel ( var date:BindableDate = BindableDate(),
                                var depot:String = "",
                                var longitude:String = "",
                                var latitude:String = "") : ViewModelBase() {

    open var listStocks: ArrayList<StockForUI>? = null
        protected set
    open val isNotEmpty: Boolean?
        get() = false

    open fun getListStockClone(): ArrayList<StockForUI>? {
        return listStocks
    }

    open fun updateStocks() {

    }

    open fun save(): Boolean {
        return false
    }

    open fun getDepotAdapter(activity: Activity): ArrayAdapter<String>? {
        return null
    }
    abstract fun getMesures(): List<String>?
}
