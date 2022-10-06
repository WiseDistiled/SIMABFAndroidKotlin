package com.tiramakan.simabf.bootstrap.util

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

object UIUtils {

    //  SimpleDateFormat.getInstance();
    val dateFormat: DateFormat
        get() = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRANCE)
    //  SimpleDateFormat.getInstance();
    val prettyDateFormat: DateFormat
        get() = SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE)
    //  SimpleDateFormat.getInstance();
    val prettyDateAndHourFormat: DateFormat
        get() = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRANCE)

    /**
     * Helps determine if the app is running in a Tablet context.
     *
     * @param context
     * @return
     */
    fun isTablet(context: Context): Boolean {
        //   return true;
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun isPaysage(context: Context): Boolean {
        //   return true;
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    }

    fun isValidPhoneNumber(context: Context, entered_number: String): Boolean {
       // val regexStr = "^[+][0-9]{10,13}$"
        val regexStr = "^[+][0-9]{1,3}[0-9]{4,14}$"
        if (!entered_number.matches(regexStr.toRegex())) {
            Toast.makeText(context, "Veuillez entrer un numéro de téléphone valide", Toast.LENGTH_SHORT).show()
            return true
        } else
            return true
    }

    fun formatPrice(value: Double?): String {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val format = android.icu.text.NumberFormat.getInstance(Locale.FRENCH)
            return format.format(value)
        } else {
            val format = java.text.NumberFormat.getInstance(Locale.FRENCH)
            return format.format(value)
        }
    }

    fun formatCurrency(currency: String, value: Double?): String {

        return formatPrice(value) + " " + currency
    }

}
