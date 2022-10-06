package com.tiramakan.simabf.core.models.savers

import com.squareup.otto.Bus
import com.tiramakan.simabf.core.modelView.NewsForUI
import com.tiramakan.simabf.core.models.realm.Info

import io.realm.Realm

/**
 * Created by tiramakan on 23/01/2016.
 */
class ISaverNewsImpl(protected var realm: Realm, protected var bus: Bus) : ISaverNews {
    public val nextKey: Int
        get() {
            val maxNumber = realm.where(Info::class.java).max("id")
            return if (maxNumber != null)
                maxNumber.toInt() + 1
            else
                1
        }

    override fun save(newsForUI: NewsForUI) {
        realm.beginTransaction()
        realm.delete(Info::class.java)
        try {
            if (newsForUI.isValid!!) {

                val ponews: Info
                if (newsForUI.id == null) {
                    ponews = realm.createObject(Info::class.java, nextKey)
                } else {
                    ponews = realm.where(Info::class.java)
                            ?.equalTo("id", newsForUI.id)
                            ?.findFirst()!!
                }
                ponews.status = "created"
                ponews.date=newsForUI.date.get()
                ponews.titre = newsForUI.titre.get()
                ponews.contenu = newsForUI.contenu.get()
                ponews.url = newsForUI.url.get()
                ponews.latitude = newsForUI.latitude.get()
                ponews.latitude = newsForUI.longitude.get()

            }

            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()

        }

    }

}
