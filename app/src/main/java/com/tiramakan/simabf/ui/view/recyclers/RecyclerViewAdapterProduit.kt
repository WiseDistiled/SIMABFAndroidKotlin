package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.models.realm.Produit

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterProduit].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterProduit(private val context: Context, private val mValues: List<Produit>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.produit_item, parent, false)
        return VHItem(view)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vhItem = holder as VHItem
        vhItem.mItem = mValues[position]
        vhItem.mIdView.text = mValues[position].code
        vhItem.mContentView.text = mValues[position].nom
    }

    override fun getItemCount(): Int {
        return mValues.size
    }


    inner class VHItem(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        var mItem: Produit? = null

        init {
            mIdView = mView.findViewById<View>(R.id.code) as TextView
            mContentView = mView.findViewById<View>(R.id.libelle) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    companion object {
        private val TYPE_ITEM = 1
    }

}
