package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.Marche

/**
 * Created by tiramakan on 17/01/2016.
 */
interface MarcheProvider {
    val items: List<String>
    val itemsForWriting: List<String>
    fun getId(valueName: String): Marche
}
