package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.ProduitCategorieUIModel

import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsProduitCategorie {

    val produitCategoryUIModelComparator: Comparator<ProduitCategorieUIModel>
        get() = ProduitCategorieUIModelComparator()


    private class ProduitCategorieUIModelComparator : Comparator<ProduitCategorieUIModel> {

        override fun compare(produitCategoryUIModel1: ProduitCategorieUIModel, produitCategoryUIModel2: ProduitCategorieUIModel): Int {
            return produitCategoryUIModel1.name!!.compareTo(produitCategoryUIModel2.name!!)
        }
    }


}
