package com.tiramakan.simabf.core.modelView


import com.tiramakan.simabf.core.models.realm.Mesure
import com.tiramakan.simabf.ui.view.utils.Binding.BindableString

/**
 * Created by tiramakan on 22/12/2015.
 */
@org.parceler.Parcel(org.parceler.Parcel.Serialization.BEAN)
class PriceRequestToUI (var marches: BindableString = BindableString(),
                        var produit: BindableString = BindableString(),
                        var mesure: BindableString = BindableString()) {


    val isValid: Boolean?
        get() = produit.get() != "" || marches.get() != ""



    constructor(mMarches: String, mProduit: String, defaultMesure: Mesure):this() {
        this.marches.set(mMarches)
        this.produit.set(mProduit)
        this.mesure.set(defaultMesure.code.toString())

    }



}
