package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Utilisateur
import io.realm.Realm
import io.realm.RealmResults
import java.util.ArrayList

object UsersToUIList{
    fun getListUsersFromDBSended(realm: Realm): ArrayList<UserToUI> {
        val theList = ArrayList<UserToUI>()
        val users = realm.where(Utilisateur::class.java)
                .equalTo("status", "sended")
                .findAll()
        if (users.size > 0) {
            for (user in users) {
                val offerToUI = UserToUI(user)
                theList.add(offerToUI)
            }
        }
        return theList

    }

    fun getListFromDBNotSended(realm: Realm): ArrayList<UserToUI> {
        val theList = ArrayList<UserToUI>()
        val users = realm.where(Utilisateur::class.java)
                .equalTo("status", "created")
                .findAll()
        if (users.size > 0) {
            for (user in users) {
                val userToUI = UserToUI(user)
                theList.add(userToUI)
            }
        }
        return theList

    }

    fun getRealmUsersNotSended(realm: Realm): RealmResults<Utilisateur> {
        return realm.where(Utilisateur::class.java)
                .equalTo("status", "created")
                .findAll()

    }

    fun getRealmUserById(realm: Realm, id: Int): Utilisateur? {
        return realm.where(Utilisateur::class.java)
                .equalTo("id", id)
                .findFirst()

    }


}