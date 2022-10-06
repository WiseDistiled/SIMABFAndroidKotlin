package com.tiramakan.simabf.core.models.realm


import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open class Info : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var titre: String = ""
    var contenu: String = ""
    var date: Date = Date()
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var status: String = ""
    var url: String = ""
}
