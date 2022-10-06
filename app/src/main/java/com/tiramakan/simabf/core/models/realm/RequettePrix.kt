package com.tiramakan.simabf.core.models.realm


import java.util.Date

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by tiramakan on 22/12/2015.
 */
open class RequettePrix : RealmObject() {
    var status: String = ""
    var produit: Produit? = null
    var marches: RealmList<Marche>? = null
    var date: Date = Date()
    var mesure: Mesure? = null
}
