package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.ProduitUIModel

import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsProduit {

    val produitUIModelComparator: Comparator<ProduitUIModel>
        get() = ProduitUIModelComparator()


    private class ProduitUIModelComparator : Comparator<ProduitUIModel> {

        override fun compare(produit1: ProduitUIModel, produit2: ProduitUIModel): Int {
            return produit1.name!!.compareTo(produit2.name!!)
        }
    }


}
