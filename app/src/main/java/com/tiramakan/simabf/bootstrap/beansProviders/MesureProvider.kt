package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.Mesure

/**
 * Created by tiramakan on 17/01/2016.
 */
interface MesureProvider {
    val items: List<String>
    fun getId(valueName: String): Mesure
}
