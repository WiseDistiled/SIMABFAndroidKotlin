package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.Reseau

/**
 * Created by tiramakan on 17/01/2016.
 */
interface ReseauProvider {
    val items: List<String>
    fun getId(valueName: String): Reseau
}
