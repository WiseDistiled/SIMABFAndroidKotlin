package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.StockForUI

import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsStockForUI {

    val stockForUIComparator: Comparator<StockForUI>
        get() = StockForUIModelComparator()


    private class StockForUIModelComparator : Comparator<StockForUI> {

        override fun compare(stockForUI1: StockForUI, stockForUI2: StockForUI): Int {
            return stockForUI1.produit.get().compareTo(stockForUI2.produit.get())
        }
    }


}
