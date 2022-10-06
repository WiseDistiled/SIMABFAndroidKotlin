package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.EtalonnageToUI
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.core.modelView.StockForUI
import com.tiramakan.simabf.ui.view.SelectableAdapter

import java.util.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterStock].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterStock(private val context: Context, private var mValues: ArrayList<StockForUI>?, private val clickListener: VHItem.ClickListener) : SelectableAdapter<RecyclerViewAdapterStock.VHItem>() {
    override fun getItemViewType(position: Int): Int {
        //        if(isPositionHeader(position))
        //            return TYPE_HEADER;
        return TYPE_ITEM
    }

    fun setItems(items: ArrayList<StockForUI>) {
        mValues = items

        notifyDataSetChanged()

    }

    fun getElementAt(position: Int): StockForUI? {
          return  mValues!![position]

    }

    fun setElement(item: StockForUI, position: Int) {
        mValues!![position] = item
        notifyDataSetChanged()
    }

    fun deleteSelection(position: Int) {
        mValues?.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.stock_item, parent, false)
        return VHItem(view, clickListener)


    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        holder.mItem = mValues!![position]
        holder.mProduit.text = holder.mItem?.produit?.get()
        holder.mMagazin.text = holder.mItem?.depot?.get()
        holder.mBalance.text = holder.mItem?.fullBalance.toString()
        holder.mDate.text = holder.mItem?.prettyDateAndHour
        holder.mView.isLongClickable = true
        holder.mContainer.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.layout_color, null))
        holder.mProduit.setTextColor(Color.BLACK)
        holder.mMagazin.setTextColor(Color.BLACK)
        holder.mBalance.setTextColor(Color.BLACK)
        holder.mDate.setTextColor(Color.BLACK)
        if (isSelected(position) || selectedPos == position) {
            setSelection(position)
            selectedPos = -1
        }

    }


    override fun getItemCount(): Int {
        return mValues?.size?:0
    }


    class VHItem(val mView: View, private val listener: ClickListener?) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        internal var mProduit: TextView
        internal var mMagazin: TextView
        internal var mBalance: TextView
        internal var mDate: TextView
        internal var mContainer: RelativeLayout


        var mItem: StockForUI? = null

        init {
            mProduit = mView.findViewById<View>(R.id.produit) as TextView
            mMagazin = mView.findViewById<View>(R.id.magazin) as TextView
            mBalance = mView.findViewById<View>(R.id.balance) as TextView
            mDate = mView.findViewById<View>(R.id.date) as TextView
            mContainer = mView.findViewById<View>(R.id.container) as RelativeLayout
            // mView.setOnLongClickListener (this);
            mView.setOnClickListener(this)
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
