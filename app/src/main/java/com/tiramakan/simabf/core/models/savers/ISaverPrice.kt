package com.tiramakan.simabf.core.models.savers

import com.tiramakan.simabf.core.modelView.PriceToUI

import java.util.ArrayList
import java.util.Date

/**
 * Created by tiramakan on 23/01/2016.
 */
interface ISaverPrice {
    fun save(prices: ArrayList<PriceToUI>, datePrice: Date)
    fun saveRow(priceToUI: PriceToUI)

}
