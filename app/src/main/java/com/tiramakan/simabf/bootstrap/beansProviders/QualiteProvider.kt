package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.Qualite

/**
 * Created by tiramakan on 17/01/2016.
 */
interface QualiteProvider {
    val items: List<String>
    fun getId(valueName: String): Qualite
}
