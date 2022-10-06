package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Stock

/**
 * Created by tiramakan on 21/02/2016.
 */
class StockToSMS(stock: Stock) {
    var message: String = ""

    init {
        val tokenSeparator = " "
        val mesureCode = stock.mesure!!.code
        val produitCode = stock.produit!!.code
        val depot = stock.depot!!.code
        val balance = stock.balance.toString()

        message = "setstock$tokenSeparator#$depot#$produitCode=$balance/$mesureCode"


    }
}
