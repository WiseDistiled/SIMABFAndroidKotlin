package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.UserToUI
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.ui.view.SelectableAdapter

import java.util.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterUsers].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterUsers(private val context: Context, private var mValues: ArrayList<UserToUI>, private val clickListener: VHItem.ClickListener) : SelectableAdapter<RecyclerViewAdapterUsers.VHItem>() {
    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    fun setItems(items: ArrayList<UserToUI>) {
        mValues = items

        notifyDataSetChanged()

    }

    fun getElementAt(position: Int): UserToUI? {
        return if (itemCount > 0)
            mValues[position]
        else
            null
    }

    fun setElement(item: UserToUI, position: Int) {
        mValues[position] = item
        notifyDataSetChanged()
    }

    fun deleteSelection(position: Int) {
        mValues.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item, parent, false)
        return VHItem(view, clickListener)


    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        holder.mItem = mValues[position]
        holder.mprenom.text = holder.mItem?.prenom?.get()
        holder.mnom.text = holder.mItem?.nom?.get()
        holder.mmobilePhone.text = holder.mItem?.mobilePhone?.get()
        holder.msecondPhone.text = holder.mItem?.secondPhone?.get()
        holder.memail.text = holder.mItem?.email?.get()
        holder.mreseau.text = holder.mItem?.reseau
        holder.msecondPhone.setTextColor(Color.BLACK)
        holder.memail.setTextColor(Color.BLACK)
        holder.mmobilePhone.setTextColor(Color.BLACK)
        holder.mprenom.setTextColor(Color.BLACK)
        holder.mnom.setTextColor(Color.BLACK)
        holder.mView.isLongClickable = true
        if (isSelected(position) || selectedPos == position) {
            setSelection(position)
            selectedPos = -1
        }
    }

    override fun getItemCount(): Int {
        return mValues.size?:0
    }


    class VHItem(val mView: View, private val listener: ClickListener?) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        internal var mprenom: TextView
        internal var mnom: TextView
        internal var mmobilePhone: TextView
        internal var memail: TextView
        internal var msecondPhone: TextView
        internal var mreseau: TextView
        internal var mContainer: RelativeLayout
        var mItem: UserToUI? = null

        init {
            mprenom = mView.findViewById<View>(R.id.firstname) as TextView
            mnom = mView.findViewById<View>(R.id.name) as TextView
            mmobilePhone = mView.findViewById<View>(R.id.phone) as TextView
            msecondPhone = mView.findViewById<View>(R.id.secondPhone) as TextView
            memail = mView.findViewById<View>(R.id.email) as TextView
            mreseau = mView.findViewById<View>(R.id.reseau) as TextView
            mView.setOnClickListener(this)
            mContainer = mView.findViewById<View>(R.id.container) as RelativeLayout
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

    companion object {
        private val TYPE_ITEM = 1
    }

}
