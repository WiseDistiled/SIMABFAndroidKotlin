package com.tiramakan.simabf.core.modelView


import com.tiramakan.simabf.ui.view.utils.Binding.BindableString
import org.parceler.Parcel

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(value = Parcel.Serialization.BEAN)
class OfferRequestToUI(var produits:BindableString = BindableString(), var offerType:BindableString = BindableString()) {
    val isValid: Boolean?
        get() = produits.get() != "" || offerType.get() != ""

}
