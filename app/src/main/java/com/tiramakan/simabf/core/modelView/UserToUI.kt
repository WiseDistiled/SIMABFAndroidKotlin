package com.tiramakan.simabf.core.modelView


import com.tiramakan.simabf.bootstrap.beansProviders.ReseauProvider

import com.tiramakan.simabf.core.models.realm.Utilisateur
import com.tiramakan.simabf.ui.view.utils.Binding.BindableString
import org.parceler.Parcel
import java.util.ArrayList

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(value = Parcel.Serialization.FIELD)
class UserToUI (var mobilePhone: BindableString = BindableString(),
                var secondPhone:BindableString = BindableString(),
                var prenom:BindableString = BindableString(),
                var nom:BindableString = BindableString(),
                var genre:String = "",
                var email:BindableString = BindableString(),
                var reseau:String = "",
                var id: Int = 0) {
    @Transient
    lateinit var reseauProvider: ReseauProvider
    val isValid: Boolean
        get() = mobilePhone.get() != "" && prenom.get() != "" && nom.get() != ""

    constructor(utilisateur: Utilisateur):this() {
        mobilePhone.set(utilisateur.mobilePhone.toString())
        prenom.set(utilisateur.prenom.toString())
        nom.set(utilisateur.nom.toString())
        genre = utilisateur.genre.toString()
        secondPhone.set(utilisateur.secondPhone.toString())
        id = utilisateur.id
        email.set(utilisateur.email.toString())
        reseau=utilisateur.reseau.toString()


    }
    fun reseauItems(): List<String>? {
        return reseauProvider.items
    }
    fun validate(): Boolean {
        return mobilePhone.get() != "" && prenom.get() != "" && nom.get() != ""
    }
    fun genderItems(): List<String>? {
        val genders = ArrayList<String>()
        genders.add("Masculin")
        genders.add("Feminin")
        genders.add("NA")
        return genders
    }

    override fun toString(): String {
        return "Téléphone : " + mobilePhone.get() + " Prénom(s) " + prenom.get() + " Nom " + nom.get()
    }



}
