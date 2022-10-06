package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.Depot

/**
 * Created by tiramakan on 17/01/2016.
 */
interface DepotProvider {
    val items: List<String>
    fun getId(valueName: String): Depot
}
