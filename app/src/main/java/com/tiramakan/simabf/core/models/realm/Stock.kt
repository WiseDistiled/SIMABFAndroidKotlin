package com.tiramakan.simabf.core.models.realm


import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open class Stock : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var mesure: Mesure? = null
    var produit: Produit? = null
    var date: Date = Date()
    var balance: Double = 0.0
    var depot: Depot? = null
    var comment: String = ""
    var status: String = ""
}
