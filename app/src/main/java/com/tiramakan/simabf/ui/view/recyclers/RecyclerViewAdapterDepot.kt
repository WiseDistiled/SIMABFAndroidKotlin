package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.core.models.realm.Depot

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterDepot].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterDepot(private val context: Context, private val mValues: List<Depot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.depot_item, parent, false)
        return VHItem(view)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VHItem) {
            holder.mItem = mValues[position]
            holder.mIdView.text = mValues[position].code
            holder.mContentView.text = mValues[position].nom
            val longitude = mValues[position].longitude.toString()
            val latitude = mValues[position].latitude.toString()

            val description = "Longitude : " + longitude + System.getProperty("line.separator") + " Latitude : " +
                    latitude
            holder.mCoordonnes.text = description

        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }


    inner class VHItem(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        val mCoordonnes: TextView
        var mItem: Depot? = null

        init {
            mIdView = mView.findViewById<View>(R.id.code) as TextView
            mContentView = mView.findViewById<View>(R.id.libelle) as TextView
            mCoordonnes = mView.findViewById<View>(R.id.coordonnees) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    companion object {
        private val TYPE_ITEM = 1
    }

}
