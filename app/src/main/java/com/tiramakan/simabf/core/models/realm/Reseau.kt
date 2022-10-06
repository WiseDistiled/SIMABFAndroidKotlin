package com.tiramakan.simabf.core.models.realm


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open class Reseau : RealmObject() {
    @PrimaryKey
    var code: String = ""
    var nom: String = ""
    var typeSIM: String = ""
}
