package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.util.StrUtil
import com.tiramakan.simabf.core.models.realm.Prix

import java.util.ArrayList

import io.realm.RealmResults

/**
 * Created by tiramakan on 21/02/2016.
 */
class PriceListToSMS(prixes: RealmResults<Prix>, marche: String) {
    var messageSMS: String? = null


    init {
        val tokenSeparator = " "
        messageSMS = "setprix$tokenSeparator#"

        val produits = ArrayList<String>()
        var note = ""
        if (prixes.size > 0)
            if (prixes[0]?.note_contenu?.compareTo("-") != 0)
                note = "__" + prixes[0]?.note_contenu
        produits.clear()
        for (price in prixes) {
            if (price.marche?.code?.compareTo(marche) == 0) {
                produits.add(price.produit?.code + "=" + price.montant.toString() + "/" + price.mesure?.code)

            }

        }
        val textPrixProduits = StrUtil.join(produits, ",")
        messageSMS += "$textPrixProduits#$marche$note"

    }
}
