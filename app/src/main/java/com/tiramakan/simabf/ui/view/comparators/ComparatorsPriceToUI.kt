package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.PriceToUI

import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsPriceToUI {

    val priceToUIComparator: Comparator<PriceToUI>
        get() = PriceToUIModelComparator()


    private class PriceToUIModelComparator : Comparator<PriceToUI> {

        override fun compare(priceToUI1: PriceToUI, priceToUI2: PriceToUI): Int {
            return priceToUI1.produit.compareTo(priceToUI2.produit)
        }
    }


}
