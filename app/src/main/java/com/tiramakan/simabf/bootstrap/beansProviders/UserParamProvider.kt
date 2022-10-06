package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.core.models.realm.UserParam

/**
 * Created by tiramakan on 17/01/2016.
 */
interface UserParamProvider {
    fun get(): UserParam
}
