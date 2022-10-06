package com.tiramakan.simabf.ui.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

import com.tiramakan.simabf.R


/**
 * Created by Ratan on 7/29/2015.
 */
class LocalizationFragment : Fragment() {
    private var position: WebView? = null
    private var textInfo: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.localisation, null)
        position = view.findViewById<View>(R.id.webView) as WebView
        position!!.settings.javaScriptEnabled = false

        textInfo = view.findViewById<View>(R.id.textInfo) as TextView
        textInfo!!.text = getString(R.string.location)

        val locManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //For Retrieving new Location
        val locListener = MyLocationListener()

        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f,
                    locListener)

        }

        setHasOptionsMenu(true)
        return view
    }

    private inner class MyLocationListener : LocationListener {

        override fun onLocationChanged(location: Location) {

            // Retrieving Latitude
            location.latitude
            // Retrieving getLongitude
            location.longitude

            textInfo!!.text = ""
            val text = ("My Current Location is:\nLatitude = "
                    + location.latitude + "\nLongitude = "
                    + location.longitude)
            textInfo!!.text = text
            Toast.makeText(context, text, Toast.LENGTH_SHORT)
                    .show()

            // set Google Map on webview
            val url = ("http://maps.google.com/staticmap?center="
                    + location.latitude + "," + location.longitude
                    + "&zoom=14&size=512x512&maptype=mobile/&markers="
                    + location.latitude + "," + location.longitude)
            position!!.loadUrl(url)
        }

        override fun onProviderDisabled(provider: String) {
            Toast.makeText(context, "GPS Disabled",
                    Toast.LENGTH_SHORT).show()
        }

        override fun onProviderEnabled(provider: String) {
            Toast.makeText(context, "GPS Enabled",
                    Toast.LENGTH_SHORT).show()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

    }
}
