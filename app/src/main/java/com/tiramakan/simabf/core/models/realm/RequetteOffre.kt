package com.tiramakan.simabf.core.models.realm

import java.util.Date

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by tiramakan on 22/12/2015.
 */
open class RequetteOffre : RealmObject() {
    var status: String = ""
    var offerType: String = ""
    var date: Date = Date()
    var produit: RealmList<Produit>? = null
}
