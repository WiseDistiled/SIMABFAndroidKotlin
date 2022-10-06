package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by tiramakan on 20/02/2016.
 */
class ContextMenuRecyclerView : RecyclerView {

    private lateinit var mContextMenuInfo: RecyclerContextMenuInfo


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun getContextMenuInfo(): ContextMenu.ContextMenuInfo? {
        return mContextMenuInfo
    }

    override fun showContextMenuForChild(originalView: View): Boolean {
        val longPressPosition = getChildAdapterPosition(originalView)
        if (longPressPosition >= 0) {
            val longPressId = adapter?.getItemId(longPressPosition)?:0
            mContextMenuInfo = RecyclerContextMenuInfo(longPressPosition, longPressId)
            return super.showContextMenuForChild(originalView)
        }
        return false
    }

    class RecyclerContextMenuInfo(val position: Int, val id: Long) : ContextMenu.ContextMenuInfo

}