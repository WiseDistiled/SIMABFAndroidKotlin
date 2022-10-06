package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Info
import io.realm.Realm
import io.realm.RealmResults

object NewsForUIList{
    fun getListFromDBNotSended(realm: Realm): ArrayList<NewsForUI> {
        val theList = ArrayList<NewsForUI>()
        val thenews = realm.where(Info::class.java)
                .equalTo("status", "created")
                .findAll()
        if (thenews.size > 0) {
            for (ponews in thenews) {
                val newsForUI = NewsForUI(ponews)
                theList.add(newsForUI)
            }
        }
        return theList

    }

    fun getRealmNewsById(realm: Realm, id: Int): Info? {
        return realm.where(Info::class.java)
                .equalTo("id", id)
                .findFirst()

    }

    fun getRealmNewsNotSended(realm: Realm): RealmResults<Info> {

        return realm.where(Info::class.java)
                .equalTo("status", "created")
                .findAll()

    }

}