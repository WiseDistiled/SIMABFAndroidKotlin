package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.Region

/**
 * Created by tiramakan on 17/01/2016.
 */
interface RegionProvider {
    val items: List<String>
    fun getId(valueName: String): Region
}
