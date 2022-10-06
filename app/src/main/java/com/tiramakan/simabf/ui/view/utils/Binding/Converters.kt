package com.tiramakan.simabf.ui.view.utils.Binding

import android.icu.text.NumberFormat
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.util.UIUtils
import java.util.*


object Converters {

    @JvmStatic
    fun toInt(any: Any): Int {
        return any as Int
    }

    @BindingConversion
    @JvmStatic
    fun convertBindableToString(bindableString: BindableString): String {
        return bindableString.get()
    }

    @BindingConversion
    @JvmStatic
    fun convertBindableToBoolean(bindableBoolean: BindableBoolean): Boolean {
        return !(!bindableBoolean.get())
    }

    @BindingConversion
    @JvmStatic
    fun convertBindableToDouble(bindableDouble: BindableDouble): Double {
        return bindableDouble.get()

    }

    @BindingConversion
    @JvmStatic
    fun convertBindableToDate(bindableDate: BindableDate): Date {
        return bindableDate.get()

    }

    @BindingConversion
    @JvmStatic
    fun convertBindableToIntlPhoneInput(bindableIntlPhoneInput: BindableIntlPhoneInput): String {
        return bindableIntlPhoneInput.get()

    }

    @BindingAdapter("android:binding")
    @JvmStatic
    fun bindEditText(view: EditText, bindableString: BindableString) {
        val isObservableOk = (view.getTag(R.id.bound_observable) != null);
        if (!isObservableOk) {
            try {
                val pair = view.getTag(R.id.bound_observable) as Pair<*, *>
                if (pair.first !== bindableString) {
                    view.removeTextChangedListener(pair.second as TextWatcher)
                }
            } catch (e: Exception) {

            }
            val watcher = object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    bindableString.set(s.toString())
                }
            }
            view.setTag(R.id.bound_observable, Pair<BindableString, TextWatcherAdapter>(bindableString, watcher))
            view.addTextChangedListener(watcher)
        }
        if (isObservableOk) {
            val newValue = bindableString.get()
            if (view.text.toString() != newValue) {
                view.setText(newValue)
            }
        }

    }

    @BindingAdapter("android:binding")
    @JvmStatic
    fun bindEditText(view: EditText, bindableDouble: BindableDouble) {
        val isObservableOk = (view.getTag(R.id.bound_observable) != null);
        if (!isObservableOk) {
            try {
                val pair = view.getTag(R.id.bound_observable) as Pair<*, *>
                if (pair.first !== bindableDouble) {
                    view.removeTextChangedListener(pair.second as TextWatcher)
                }
            } catch (e: Exception) {

            }
            val watcher = object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val floatString = s.toString().trim { it <= ' ' }.replace("\\s".toRegex(), "").replace(",".toRegex(), ".")
                    try {
                        if (floatString != "")
                            bindableDouble.set(java.lang.Double.parseDouble(floatString))
                        else
                            bindableDouble.set(java.lang.Double.valueOf("0.0"))
                    } catch (e: Exception) {
                        bindableDouble.set(java.lang.Double.valueOf("0.0"))

                    }

                }
            }

            view.setTag(R.id.bound_observable, Pair<BindableDouble, TextWatcherAdapter>(bindableDouble, watcher))
            view.addTextChangedListener(watcher)
        }


        var newValue = ""
        if (bindableDouble.get() != 0.0 ) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                val format = NumberFormat.getInstance(Locale.FRENCH)
                newValue = format.format(bindableDouble.get())
            } else {
                val format = java.text.NumberFormat.getInstance(Locale.FRENCH)
                newValue = format.format(bindableDouble.get())
            }
        }
        if (view.text.toString() != newValue && newValue != "0.0" && newValue != "") {
            view.setText(newValue)
            val posvirgule = view.text.toString().indexOf('.')
            if (posvirgule > 0)
                view.setSelection(posvirgule)
            else
                view.setSelection(view.text.length)
        }
        if (newValue == "")
            view.setText("")

    }

    @BindingAdapter("android:binding")
    @JvmStatic
    fun bindEditText(view: EditText, bindableInteger: BindableInteger) {
        val isObservableOk = (view.getTag(R.id.bound_observable) != null);
        if (isObservableOk) {
            val pair = view.getTag(R.id.bound_observable) as Pair<*, *>
            if (pair.first !== bindableInteger) {
                view.removeTextChangedListener(pair.second as TextWatcher)
            }
        } else {
            val watcher = object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().trim { it <= ' ' } != "")
                        bindableInteger.set(Integer.valueOf(s.toString()))
                    else
                        bindableInteger.set(Integer.valueOf("0"))

                }
            }
            view.setTag(R.id.bound_observable, Pair<BindableInteger, TextWatcherAdapter>(bindableInteger, watcher))
            view.addTextChangedListener(watcher)
        }

        val newValue = bindableInteger.get()
        if (view.text.toString() != newValue.toString() && newValue.toString() != "0") {
            view.setText(newValue.toString())
            view.setSelection(view.text.length)
        }
        if (newValue == 0)
            view.setText("")

    }

    @BindingAdapter("android:binding")
    @JvmStatic
    fun bindEditText(view: EditText, bindableLong: BindableLong) {
        val isObservableOk = (view.getTag(R.id.bound_observable) != null);
        if (isObservableOk) {
            val pair = view.getTag(R.id.bound_observable) as Pair<*, *>
            if (pair.first !== bindableLong) {
                view.removeTextChangedListener(pair.second as TextWatcher)
            }
        } else {

            val watcher = object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString().trim { it <= ' ' } != "")
                        bindableLong.set(java.lang.Long.valueOf(s.toString()))
                    else
                        bindableLong.set(java.lang.Long.valueOf("0"))

                }
            }
            view.setTag(R.id.bound_observable, Pair<BindableLong, TextWatcherAdapter>(bindableLong, watcher))
            view.addTextChangedListener(watcher)
        }
        val newValue = bindableLong.get()
        if (view.text.toString() != newValue.toString()) {
            view.setText(newValue.toString())
            view.setSelection(view.text.length - 2)
        }
    }

    @BindingAdapter("android:binding")
    @JvmStatic
    fun bindEditText(view: EditText, bindableDate: BindableDate) {
        val newValue = UIUtils.prettyDateFormat.format(bindableDate.get())

        if (view.text.toString() != newValue) {
            view.setText(newValue)
        }
    }

    @BindingAdapter("android:binding")
    @JvmStatic
    fun bindRadioGroup(view: RadioGroup, bindableString: BindableString) {

        if (view.getTag(R.id.bound_observable) !== bindableString) {
            view.setTag(R.id.bound_observable, bindableString)
            view.setOnCheckedChangeListener { group, checkedId ->
                val count = group.childCount
                for (i in 0 until count) {
                    val o = group.getChildAt(i)
                    if (o is RadioButton) {
                        if (o.id == checkedId) {
                            bindableString.set(o.text.toString())
                            break
                        }


                    }
                }
            }
        }
        val newValue = bindableString.get()
        //        RadioButton b = (RadioButton) findViewById(R.id.option1);
        //        b.setChecked(true);
        val count = view.childCount
        for (i in 0 until count) {
            val o = view.getChildAt(i)
            if (o is RadioButton) {

                if (o.text === newValue)
                    o.isChecked = true


            }
        }
    }

    @BindingAdapter("android:binding")
    @JvmStatic fun bindRadioGroup(view: RadioGroup, bindableBoolean: BindableBoolean) {

        if (view.getTag(R.id.bound_observable) != null) {
            if (view.getTag(R.id.bound_observable) !== bindableBoolean) {
                view.setTag(R.id.bound_observable, bindableBoolean)
                view.setOnCheckedChangeListener { group, checkedId -> bindableBoolean.set(checkedId == group.getChildAt(1).id) }
            }
            val newValue = bindableBoolean.get()
            (view.getChildAt(if (newValue) 1 else 0) as RadioButton).isChecked = true
        }
    }

    //private method of your class
    fun getIndex(spinner: AppCompatSpinner, myString: String): Int {
        var index = 0

        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                index = i
                break
            }
        }
        return index

    }


    @BindingAdapter("android:binding")
    @JvmStatic fun bindSpinner(view: AppCompatSpinner, bindableString: BindableString) {

            if (view.getTag(R.id.bound_observable) !== bindableString) {
                view.setTag(R.id.bound_observable, bindableString)

                view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*> , view: View, position: Int, id: Long) {

                                val spinner = parent as AppCompatSpinner
                                val selectedValue = (view as TextView).text.toString()
                                spinner.setSelection(getIndex(spinner, selectedValue))
                                bindableString.set(selectedValue)
                   }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
            }



    }

//    @BindingAdapter("entries")
//    @JvmStatic fun Spinner.setEntries(entries: List<Any>?) {
//        setSpinnerEntries(entries)
//    }
//
//    @BindingAdapter("onItemSelected")
//    @JvmStatic fun Spinner.setItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
//        setSpinnerItemSelectedListener(itemSelectedListener)
//    }
//
//    @BindingAdapter("newValue")
//    @JvmStatic fun Spinner.setNewValue(newValue: Any?) {
//        if (newValue != null) {
//            setSpinnerValue(newValue)
//        }
//    }
//
//    @BindingAdapter("selectedValue")
//    @JvmStatic fun Spinner.setSelectedValue(selectedValue: Any?) {
//        if (selectedValue != null) {
//            setSpinnerValue(selectedValue)
//        }
//    }
//
//    @BindingAdapter("selectedValueAttrChanged")
//    @JvmStatic fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
//        setSpinnerInverseBindingListener(inverseBindingListener)
//    }
//    @JvmStatic companion object InverseSpinnerBindings {
//
//        @JvmStatic
//        @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
//        fun Spinner.getSelectedValue(): Any? {
//            return getSpinnerValue()
//        }
//    }




    @BindingAdapter("onClick")
    @JvmStatic fun bindOnClick(view: View, runnable: Runnable) {
        view.setOnClickListener { runnable.run() }
    }


}

