package com.tiramakan.simabf.core.models.savers

import com.tiramakan.simabf.core.modelView.StockForUI
import com.tiramakan.simabf.core.modelView.StockToUI

import java.util.Date

/**
 * Created by tiramakan on 23/01/2016.
 */
interface ISaverStock {
    fun save(stocks: List<StockForUI>, date: Date)
    fun saveRow(stockToUI: StockToUI)
}
