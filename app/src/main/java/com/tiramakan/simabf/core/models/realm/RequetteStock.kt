package com.tiramakan.simabf.core.models.realm


import java.util.Date

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by tiramakan on 22/12/2015.
 */
open class RequetteStock : RealmObject() {
    var mesure: Mesure? = null
    var produit: Produit? = null
    var date: Date = Date()
    var balance: Double? = null
    var depot: RealmList<Depot>? = null
    var comment: String = ""
    var status: String = ""
}
