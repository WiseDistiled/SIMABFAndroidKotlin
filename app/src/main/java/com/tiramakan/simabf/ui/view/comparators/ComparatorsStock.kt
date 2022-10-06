package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.StockForUI

import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsStock {

    val stockForUIProduitComparator: Comparator<StockForUI>
        get() = StockForUIProduitComparator()


    private class StockForUIProduitComparator : Comparator<StockForUI> {

        override fun compare(stockForUI1: StockForUI, stockForUI2: StockForUI): Int {
            return stockForUI1.produit.get().compareTo(stockForUI2.produit.get())
        }
    }


}
