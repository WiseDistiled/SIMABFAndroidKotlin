package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.ui.view.comparators.ComparatorsProduit

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections

import io.realm.RealmResults
import io.realm.Sort

/**
 * Created by tiramakan on 17/02/2016.
 */
class ProduitUIModel internal constructor(produit: Produit?) : Serializable {
    var code: String? = null
    var name: String? = null
    var category: String? = null
    var isSelected: Boolean = false

    init {
        if (produit != null) {
            this.name = produit.nom
            this.code = produit.code
            this.category = produit.categorie!!.nom
            this.isSelected = false
        }
    }

    override fun toString(): String {
        return " nom : " + name
    }

    companion object {
        public const val serialVersionUID = 1L
        fun getList(bootstrapService: BootstrapService): ArrayList<ProduitUIModel> {
            val temp = ArrayList<ProduitUIModel>()
            val produits = bootstrapService.produits
            produits.sort("nom", Sort.ASCENDING)
            for (produit in produits) {
                temp.add(ProduitUIModel(produit))
            }
            Collections.sort(temp, ComparatorsProduit.produitUIModelComparator)
            return temp
        }

        fun getProduitStartingWith(bootstrapService: BootstrapService, produitName: String): ProduitUIModel {

            val produit = bootstrapService.getProduitSartingWith(produitName)
            return ProduitUIModel(produit)
        }
    }
}
