package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.ProduitCategorie
import com.tiramakan.simabf.ui.view.comparators.ComparatorsProduitCategorie

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections

import io.realm.RealmResults
import io.realm.Sort

/**
 * Created by tiramakan on 17/02/2016.
 */
class ProduitCategorieUIModel internal constructor(produitCategory: ProduitCategorie?) : Serializable {
    var code: String? = null
    var name: String? = null
    var isSelected: Boolean = false

    init {
        if (produitCategory != null) {
            this.name = produitCategory.nom
            this.code = produitCategory.nom
            this.isSelected = false
        }
    }

    override fun toString(): String {
        return " nom : " + name
    }

    companion object {
        public const val serialVersionUID = 1L
        fun getList(bootstrapService: BootstrapService): ArrayList<ProduitCategorieUIModel> {
            val temp = ArrayList<ProduitCategorieUIModel>()
            val produitCategories = bootstrapService.produitCategories
            produitCategories.sort("nom", Sort.ASCENDING)
            for (produitCategory in produitCategories) {
                temp.add(ProduitCategorieUIModel(produitCategory))
            }

            Collections.sort(temp, ComparatorsProduitCategorie.produitCategoryUIModelComparator)
            return temp
        }

        fun getProduitCategorieStartingWith(bootstrapService: BootstrapService, produitCategName: String): ProduitCategorieUIModel {

            val produitCategory = bootstrapService.getProduitCategorieSartingWith(produitCategName)

            return ProduitCategorieUIModel(produitCategory)
        }
    }
}
