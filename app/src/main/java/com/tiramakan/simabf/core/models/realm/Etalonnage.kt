package com.tiramakan.simabf.core.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by tiramakan on 22/12/2015.
 */
open class Etalonnage : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var date: Date = Date()
    var uml: String = "" //Unité de Mesure Locale
    var ul: String = "" //Unité Legale
    var umlId: Int = 0 //Unité de Mesure Locale
    var ulId: Int = 0 //Unité Legale
    var valeur:Double = 0.0
    var status: String = "created"
    var marche: String = ""
}
