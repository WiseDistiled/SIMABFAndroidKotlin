package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.util.StrUtil
import com.tiramakan.simabf.core.models.realm.Stock

import java.util.ArrayList

import io.realm.RealmResults

/**
 * Created by tiramakan on 21/02/2016.
 */
class StockListToSMS(stocks: RealmResults<Stock>, depot: String) {
    var message: String? = null

    init {
        val tokenSeparator = " "
        message = "setstock$tokenSeparator#$depot#"
        val produits = ArrayList<String>()
        for (stock in stocks) {
            val mesureCode = stock.mesure!!.code
            val produitCode = stock.produit!!.code
            val balance = stock.balance.toString()
            produits.add("$produitCode=$balance/$mesureCode")
        }
        message += StrUtil.join(produits, ",")


    }
}
