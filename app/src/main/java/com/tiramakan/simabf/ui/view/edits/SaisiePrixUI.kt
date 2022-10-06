package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.github.clans.fab.FloatingActionButton

import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.beansProviders.EtatApproProvider
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.bootstrap.beansProviders.UserParamProvider
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.constants.Constants.Extra.LAST_SAVED_PRICE_TAG
import com.tiramakan.simabf.core.modelView.PickedDate
import com.tiramakan.simabf.core.modelView.PriceToUI
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.core.models.savers.ISaverPrice
import com.tiramakan.simabf.databinding.SaisiePrixBinding
import com.tiramakan.simabf.ui.view.Lists.ListPricesUI
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent
import org.parceler.Parcels
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
//Wire the layout to the step
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SaisiePrixUI : BaseFragment() {

    lateinit var userParamProvider: UserParamProvider
        @Inject set
    lateinit var priceSaver: ISaverPrice
        @Inject set
    lateinit var mesuresProvider: MesureProvider
        @Inject set
    lateinit var etatsApprovProvider: EtatApproProvider
        @Inject set
    val datePicked = PickedDate()
    protected lateinit var mainView: View
    protected var marche: Marche? = null
    protected var marcheChoisi: String? = ""
    protected var produitChoisi: String? = ""
    protected var typePrixChoisi: String? = ""
    protected lateinit var locationManager: LocationManager
        @Inject set
    var REQUEST_CAMERA = 0
    var SELECT_FILE = 1
    protected var bitmapCaptured: String = ""
    protected lateinit var image: ImageView
    protected lateinit var mLocationListener: LocationListener
    var typologie_marche:String=""
    var region_marche:String=""
    var province_marche:String=""
    var commune_marche:String=""
    var periodicite_marche:String=""
    internal lateinit var options: BitmapFactory.Options
    internal var bm: Bitmap? = null

    protected lateinit var priceToUI: PriceToUI

    fun isSIMBetail():Boolean {
        return (userParamProvider.get().reseau=="SIMBETAIL")
    }
    fun isSIMSONAGESS():Boolean {
        return (userParamProvider.get().reseau=="SIMSONAGESS")
    }
    fun isSIMPFNL():Boolean {
        return (userParamProvider.get().reseau=="SIMPFNL")
    }
    fun isSIMDGPER():Boolean {
        return (userParamProvider.get().reseau=="SIMDGPER")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)
        if (savedInstanceState == null) {
            priceToUI = PriceToUI()
            priceToUI.marche = marcheChoisi.toString()
            priceToUI.produit = produitChoisi.toString()
            priceToUI.typePrix = typePrixChoisi.toString()

        }else
        {
            priceToUI = Parcels.unwrap(savedInstanceState.getParcelable(LAST_SAVED_PRICE_TAG))

        }
        if (arguments != null) {


            marcheChoisi = arguments?.getString(Constants.Extra.NOM_MARCHE_TAG).toString()
            produitChoisi = arguments?.getString(Constants.Extra.NOM_PRODUIT_TAG).toString()
            typePrixChoisi = arguments?.getString(Constants.Extra.TYPE_PRIX_TAG).toString()
            marche = realm.where(Marche::class.java)
                    .equalTo("nom", marcheChoisi)
                    .findFirst()
            typologie_marche = marche?.typologie?:""
            province_marche = marche?.nomProvince?:""
            commune_marche = marche?.nomCommune?:""
            region_marche = marche?.nomRegion?:""
            periodicite_marche = marche?.periodicite?:""
            priceToUI.marche = marcheChoisi.toString()
            priceToUI.produit = produitChoisi.toString()
            priceToUI.typePrix = typePrixChoisi.toString()

            val priceToUIBundle = arguments?.getBundle("priceToUIBundle")
            if (priceToUIBundle != null) {
                priceToUI = Parcels.unwrap(priceToUIBundle.getParcelable(Constants.Extra.PRICE_TAG))
                marcheChoisi = priceToUI.marche
                produitChoisi = priceToUI.produit
                typePrixChoisi = priceToUI.typePrix
                marche = realm.where(Marche::class.java)
                        .equalTo("nom", marcheChoisi)
                        .findFirst()
                typologie_marche = marche?.typologie?:""
                province_marche = marche?.nomProvince?:""
                commune_marche = marche?.nomCommune?:""
                region_marche = marche?.nomRegion?:""
                periodicite_marche = marche?.periodicite?:""
            }

        }







    }

    private fun validate(): Boolean {
        priceToUI.date=datePicked.date
        if (priceToUI.isValid) {
            priceSaver.saveRow(priceToUI)
            addFragment(ListPricesUI())
            return true
        } else {
            bus.post("Veuillez vérifier que vous avez saisi toutes les informations obligatoires")
            return false
        }
    }

    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        DataBindingUtil.setDefaultComponent(BindingComponent())
        val binding = DataBindingUtil.inflate<SaisiePrixBinding>(inflater, R.layout.saisie_prix, container, false)

        mainView = binding.root
        priceToUI.mesuresProvider= this.mesuresProvider
        priceToUI.etatApproProvider= this.etatsApprovProvider
        binding.pickedDate = datePicked
        binding.priceToUI = priceToUI
        binding.saisiePrixUI = this
//        if (isSIMBetail())
//        binding.etatApprovLabel.text="Etat d'embompoint"
        image = binding.image
        binding.image.setOnClickListener { selectImage() }
        if (priceToUI.note_photo != "") {
            if (bm == null) {
                options = BitmapFactory.Options()
                options.inJustDecodeBounds = false
                priceToUI.note_photo
                bm = BitmapFactory.decodeFile(priceToUI.note_photo, options)
                if (bm != null)
                    binding.image.setImageBitmap(bm)
                bm = null

            }
        }
        val  ajouterAudio =  mainView.findViewById(R.id.fabAjouterAudio) as FloatingActionButton;
        ajouterAudio.setOnClickListener { myParent.addFragment(TelechargerVideoUI()) }

        val  ajouterVideo =  mainView.findViewById(R.id.fabAjouterVideo) as FloatingActionButton;
        ajouterVideo.setOnClickListener { myParent.addFragment(TelechargerVideoUI()) }

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            bus.post("Verifiez que vous disposez des autorisations necessaires pour utiliser le GPS ou vérifier que le GPS est activé")
        } else {
            mLocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    binding.longitude.text = location.longitude.toString()
                    binding.latitude.text = location.latitude.toString()
                    priceToUI.note_longitude = location.longitude.toString()
                    priceToUI.note_latitude = location.latitude.toString()
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

        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data!!)
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data!!)
        }
    }
    private fun onSelectFromGalleryResult(data: Intent) {
        val selectedImageUri = data.data
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        var selectedImagePath=""
        val cursor = context?.contentResolver?.query(selectedImageUri!!, projection, null, null, null)
        try {
            val column_index = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            cursor?.moveToFirst()
            selectedImagePath = cursor?.getString(column_index?:0).toString()
        } finally {
            cursor?.close()
        }

        val bm: Bitmap
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(selectedImagePath, options)
        val REQUIRED_SIZE = 80
        var scale = 1
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2
        options.inSampleSize = scale
        options.inJustDecodeBounds = false
        bm = BitmapFactory.decodeFile(selectedImagePath, options)
        image.setImageBitmap(bm)
        bitmapCaptured = selectedImagePath
        priceToUI.note_photo=bitmapCaptured
    }

    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras?.get("data") as Bitmap

        val bytes = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 80, bytes)
        val destination = File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg")

        val fo: FileOutputStream
        try {
            if (destination.createNewFile()) {
                fo = FileOutputStream(destination)
                fo.write(bytes.toByteArray())
                fo.close()
            }
        } catch (e: IOException) {
            bus.post("Echec capture photo "+e.message)
            e.printStackTrace()
        }

        image.setImageBitmap(thumbnail)
        bitmapCaptured = destination.absolutePath
        priceToUI.note_photo=destination.absolutePath


    }

    private fun selectImage() {
        val items = arrayOf<CharSequence>("Prendre une photo", "Choisir une image dans la galérie", "Annuler")

        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
        builder.setItems(items) { dialog, item ->
            if (items[item] == "Prendre une photo") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, REQUEST_CAMERA)
            } else if (items[item] == "Choisir une image dans la galérie") {
                val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(
                        Intent.createChooser(intent, "Choisissez un fichier"),
                        SELECT_FILE)
            } else if (items[item] == "Annuler") {
                dialog.dismiss()
            }
        }
        builder.show()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAST_SAVED_PRICE_TAG, Parcels.wrap(priceToUI))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_next -> validate()
        }
        return true
    }

    override fun doBack(): Boolean {

        return true

    }


}
