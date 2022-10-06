package com.tiramakan.simabf.bootstrap.beansProviders

import io.realm.Realm

interface RealmServiceProvider {
    val realm: Realm
    val newRealm: Realm
    fun sendDatabase()
}
