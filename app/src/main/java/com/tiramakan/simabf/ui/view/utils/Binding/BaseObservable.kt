package com.tiramakan.simabf.ui.view.utils.Binding


import android.os.Parcelable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import kotlinx.android.parcel.Parcelize
import org.parceler.Parcel
import org.parceler.Transient

@Parcel
open class BaseObservable() : Observable {
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    @Synchronized
    override fun addOnPropertyChangedCallback(listener: Observable.OnPropertyChangedCallback) {
        if (this.mCallbacks == null) {
            this.mCallbacks = PropertyChangeRegistry()
        }

        this.mCallbacks?.add(listener)
    }

    @Synchronized
    override fun removeOnPropertyChangedCallback(listener: Observable.OnPropertyChangedCallback) {
        if (this.mCallbacks != null) {
            this.mCallbacks?.remove(listener)
        }
    }

    @Synchronized
    fun notifyChange() {
        if (this.mCallbacks != null) {
            this.mCallbacks?.notifyCallbacks(this, 0, null)
        }
    }

    fun notifyPropertyChanged(fieldId: Int) {
        if (this.mCallbacks != null) {
            this.mCallbacks?.notifyCallbacks(this, fieldId, null)
        }
    }
}
