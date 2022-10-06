package com.tiramakan.simabf.ui.view.edits

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.MarcheGPSToUI
import com.tiramakan.simabf.databinding.SaisiePositionGpsMarcheBinding
import com.tiramakan.simabf.ui.view.Welcome
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment

import org.parceler.Parcels

import javax.inject.Inject

/**
 * Created by Ratan on 7/29/2015.
 */

class EditGPSMarcheUI : BaseFragment() {

    protected lateinit var locationManager: LocationManager
    @Inject set
    protected lateinit var marcheGPSToUI: MarcheGPSToUI
    protected lateinit var mLocationListener: LocationListener
    protected var marcheChoisi: String=""
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(GPS_MARCHE_FOR_UI, Parcels.wrap(marcheGPSToUI))
    }

    override fun doBack(): Boolean {
        return true
    }

    override fun onPause() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            bus.post("Verifiez que vous disposez des autorisations necessaires pour utiliser le GPS ou vérifier que le GPS est activé")
        } else
            locationManager.removeUpdates(mLocationListener)
        super.onPause()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<SaisiePositionGpsMarcheBinding>(inflater, R.layout.saisie_position_gps_marche, container, false)
        setHasOptionsMenu(true)
        if (savedInstanceState != null) {
            marcheGPSToUI = Parcels.unwrap(savedInstanceState.getParcelable(GPS_MARCHE_FOR_UI))
            binding.marche.text = marcheChoisi
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)  {
            bus.post("Verifiez que vous disposez des autorisations necessaires pour utiliser le GPS ou vérifier que le GPS est active")
        } else {
            mLocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    binding.longitude.text = location.longitude.toString()
                    binding.latitude.text = location.latitude.toString()
                    marcheGPSToUI.longitude=location.longitude
                    marcheGPSToUI.latitude=location.latitude
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                }

                override fun onProviderEnabled(provider: String) {

                }

                override fun onProviderDisabled(provider: String) {

                }

            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.Location.LOCATION_REFRESH_TIME,
                    Constants.Location.LOCATION_REFRESH_DISTANCE, mLocationListener)
        }
        myParent.setTitle(" GPS : "+marcheChoisi)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            bus.post("Verifiez que vous disposez des autorisations necessaires pour utiliser le GPS ou vérifier que le GPS est activé")
        } else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.Location.LOCATION_REFRESH_TIME,
                    Constants.Location.LOCATION_REFRESH_DISTANCE, mLocationListener)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)
        if (arguments != null) {
            marcheChoisi = arguments?.getString(Constants.Extra.NOM_MARCHE_TAG).toString()
        }
        marcheGPSToUI = MarcheGPSToUI()
        marcheGPSToUI.marche = marcheChoisi
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.action_send -> {
                marcheGPSToUI.let { serviceProvider.service.sendGPSMarche(it) }
                myParent.addFragment(Welcome())
            }
        }
        return true
    }

    companion object {
        val GPS_MARCHE_FOR_UI = "GPS_MARCHE_FOR_UI"
    }


}
