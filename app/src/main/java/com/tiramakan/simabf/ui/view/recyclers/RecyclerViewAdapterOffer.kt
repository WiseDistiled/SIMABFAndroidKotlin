package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.OfferToUI
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.ui.view.SelectableAdapter

import java.util.ArrayList
import java.util.Locale

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterOffer].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterOffer(private val context: Context, private var mValues: ArrayList<OfferToUI>, private val clickListener: VHItem.ClickListener, private val devise: String) : SelectableAdapter<RecyclerViewAdapterOffer.VHItem>() {
    internal var options: BitmapFactory.Options
    internal var bm: Bitmap? = null
    fun setItems(items: ArrayList<OfferToUI>) {
        mValues = items
        notifyDataSetChanged()

    }

    fun getElementAt(position: Int): OfferToUI? {
        return if (itemCount > 0)
            mValues.get(position)
        else
            OfferToUI()
    }

    fun setElement(item: OfferToUI, position: Int) {
        mValues.set(position, item)
        notifyDataSetChanged()
    }

    fun deleteSelection(position: Int) {
        mValues.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.offer_item, parent, false)
        return VHItem(view, clickListener)


    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {

        holder.mItem = mValues.get(position)

        holder.mConditionView.text = holder.mItem.conditionOffre
        holder.mProduit.text = holder.mItem.produit?.get()
        holder.mPrixUnitaire.text = holder.mItem.prixUnitaireTxt
        holder.mPrixTotal.text = holder.mItem.getPrixTotal(devise)
        holder.mQuantite.text = String.format(Locale.FRENCH, "%.0f", holder.mItem.quantite?.get())
        holder.mTelephone.text = holder.mItem.telephone?.get()
        holder.mEmailAuteur.text = holder.mItem.email?.get()
        holder.mAuteur.text = holder.mItem.nomAuteur?.get()
        holder.mLieuxLivraison.text = holder.mItem.lieuxLivraison?.get()
        holder.mLieuxStockage.text = holder.mItem.lieuxStockage?.get()
        holder.mRegion.text = holder.mItem.region
        holder.mDate.text = holder.mItem.prettyDate
        holder.mView.isLongClickable = true
        if (holder.mItem.photo != "") {
            if (bm == null) {

                holder.mItem.photo
                bm = BitmapFactory.decodeFile(holder.mItem.photo, options)
                if (bm != null)
                    holder.mPhoto.setImageBitmap(bm)
                bm = null

            }
        }
        holder.mContainer.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.layout_color, null))

        if (isSelected(position) || selectedPos == position) {
            setSelection(position)
            selectedPos = -1
        }


    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    init {
        options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
    }
    class VHItem(val mView: View, private val listener: ClickListener?) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        internal var mConditionView: TextView
        internal var mProduit: TextView
        internal var mQuantite: TextView
        internal var mPrixUnitaire: TextView
        internal var mPrixTotal: TextView
        internal var mAuteur: TextView
        internal var mEmailAuteur: TextView
        internal var mLieuxStockage: TextView
        internal var mLieuxLivraison: TextView
        internal var mTelephone: TextView
        internal var mRegion: TextView
        internal var mDate: TextView
        internal var mPhoto: ImageView
        internal var mContainer: RelativeLayout
        lateinit var mItem: OfferToUI

        init {
            mConditionView = mView.findViewById<View>(R.id.conditions) as TextView
            mProduit = mView.findViewById<View>(R.id.produit) as TextView
            mQuantite = mView.findViewById<View>(R.id.quantity) as TextView
            mPrixUnitaire = mView.findViewById<View>(R.id.prixUnitaire) as TextView
            mPrixTotal = mView.findViewById<View>(R.id.prixTotal) as TextView
            mAuteur = mView.findViewById<View>(R.id.authorNom) as TextView
            mRegion = mView.findViewById<View>(R.id.region) as TextView
            mEmailAuteur= mView.findViewById<View>(R.id.authorEmail) as TextView
            mTelephone= mView.findViewById<View>(R.id.authorPhone) as TextView
            mLieuxStockage= mView.findViewById<View>(R.id.lieuxStockage) as TextView
            mLieuxLivraison= mView.findViewById<View>(R.id.lieuxLivraison) as TextView
            mDate = mView.findViewById<View>(R.id.date) as TextView
            mContainer = mView.findViewById<View>(R.id.container) as RelativeLayout
            mPhoto = mView.findViewById<View>(R.id.photo) as ImageView
            mView.setOnClickListener(this)
            //   mcheckPriceView = (CheckBox) view.findViewById(R.id.checkPrice);
        }

        override fun onClick(v: View) {
            listener?.onItemClicked(adapterPosition)
        }

        interface ClickListener {
            fun onItemClicked(position: Int)

        }

        override fun toString(): String {
            return super.toString() + " '" + "'"
        }
    }

    internal inner class VHHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView

        init {
            this.txtTitle = itemView.findViewById<View>(R.id.txtHeader) as TextView
        }
    }

    companion object {
        private val TYPE_ITEM = 1
    }
}
