package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Qualite
import com.tiramakan.simabf.core.models.realm.UserParam

import java.util.ArrayList

import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by tiramakan on 17/01/2016.
 */
class UserParamProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : UserParamProvider {

    override fun get(): UserParam {
        return realm.where(UserParam::class.java)
                ?.findFirst()!!
    }
}
