package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.Produit

/**
 * Created by tiramakan on 17/01/2016.
 */
interface ProduitProvider {
    val items: List<String>
    fun getId(valueName: String): Produit
}
