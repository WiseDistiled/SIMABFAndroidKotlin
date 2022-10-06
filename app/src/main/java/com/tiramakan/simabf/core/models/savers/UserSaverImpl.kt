package com.tiramakan.simabf.core.models.savers

import com.squareup.otto.Bus
import com.tiramakan.simabf.core.modelView.UserToUI
import com.tiramakan.simabf.core.models.realm.Utilisateur

import io.realm.Realm

/**
 * Created by tiramakan on 23/01/2016.
 */
class UserSaverImpl(protected var realm: Realm, protected var bus: Bus) : ISaverUser {
    public val nextKey: Int
        get() {
            val maxNumber = realm.where(Utilisateur::class.java).max("id")
            return if (maxNumber != null)
                maxNumber.toInt() + 1
            else
                1
        }

    override fun save(userToUI: UserToUI) {
        realm.executeTransaction{

            if (userToUI.isValid) {

                val utilisateur: Utilisateur

                if (userToUI.id == 0) {
                    utilisateur = realm.createObject(Utilisateur::class.java, nextKey)

                } else {
                    utilisateur = realm.where(Utilisateur::class.java)
                            ?.equalTo("id", userToUI.id)
                            ?.findFirst()!!
                }

                utilisateur.prenom = userToUI.prenom.get()
                utilisateur.nom = userToUI.nom.get()
                utilisateur.genre = userToUI.genre
                utilisateur.mobilePhone = userToUI.mobilePhone.get()
                utilisateur.email = userToUI.email.get()
                utilisateur.secondPhone = userToUI.secondPhone.get()
                utilisateur.reseau = userToUI.reseau
                utilisateur.status = "created"

            }


        }


    }

    protected fun verification() {
        //    RealmResults<Stock> stocks=realm.allObjects(Stock.class);


    }
}
