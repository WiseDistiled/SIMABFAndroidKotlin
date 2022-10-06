package com.tiramakan.simabf.core.models.realm


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open  class Marche : RealmObject() {
    @PrimaryKey
    var code: String= ""
    var nom: String = ""
    var typologie: String = ""
    var nomCommune: String = ""
    var nomProvince: String = ""
    var nomRegion: String = ""
    var periodicite: String? = ""
    var longitude: Double? = 0.0
    var latitude: Double? = 0.0
}
