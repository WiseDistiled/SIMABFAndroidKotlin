package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */
import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.bootstrap.beansProviders.QualiteProvider
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RegionProvider
import com.tiramakan.simabf.bootstrap.beansProviders.UserParamProvider
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.constants.Constants.Extra.LAST_SAVED_OFFRE_VENTE_TAG
import com.tiramakan.simabf.core.models.savers.ISaverOffer
import com.tiramakan.simabf.core.modelView.OfferToUI
import com.tiramakan.simabf.core.modelView.PickedDate
import com.tiramakan.simabf.core.models.realm.UserParam
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.SimpleTextWatcher
import com.tiramakan.simabf.databinding.SaisieOffreVenteBinding
import com.tiramakan.simabf.ui.view.Lists.ListOffersVenteUI
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent

import org.parceler.Parcels
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

import java.util.Calendar
import javax.inject.Inject

/**
 * Created by tiramakan on 17/01/2016.
 */
//Wire the layout to the step
class SaisieOffreVenteUI : BaseFragment() {
    var qualityProvider: QualiteProvider? = null
        @Inject set
    var mesuresProvider: MesureProvider? = null
        @Inject set
    var regionsProvider: RegionProvider? = null
        @Inject set
    var iSaverOffer: ISaverOffer? = null
        @Inject set
    lateinit var offerToUI: OfferToUI
    lateinit var userParamProvider: UserParamProvider
        @Inject set
    var produitChoisi: String = ""
    val datePicked = PickedDate()
    internal lateinit var options: BitmapFactory.Options
    internal var bm: Bitmap? = null
    var REQUEST_CAMERA = 0
    var SELECT_FILE = 1
    protected var bitmapCaptured: String = ""
    protected lateinit var image: ImageView
    protected lateinit var locationManager: LocationManager
        @Inject set
    protected lateinit var mLocationListener: LocationListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)

        if (savedInstanceState == null) {
            offerToUI = OfferToUI()


        }else
        {
            offerToUI = Parcels.unwrap(savedInstanceState.getParcelable(LAST_SAVED_OFFRE_VENTE_TAG))

        }
        if (arguments != null) {
            produitChoisi = arguments?.getString(Constants.Extra.NOM_PRODUIT_TAG).toString()

        val priceToUIBundle = arguments?.getBundle("offreVenteToUIBundle")
        if (priceToUIBundle != null) {
            offerToUI = Parcels.unwrap(priceToUIBundle.getParcelable(Constants.Extra.OFFER_TO_UI_BUNDLE))
            produitChoisi = offerToUI.produit?.get().toString()

        }
        }
        offerToUI.produit?.set(produitChoisi.toString())

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
        offerToUI.photo=bitmapCaptured
    }
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
        offerToUI.photo=destination.absolutePath
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
    //Set your layout here
    @SuppressLint("PrivateResource")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        offerToUI.qualityProvider= this.qualityProvider
        offerToUI.mesuresProvider= this.mesuresProvider
        offerToUI.regionsProvider= this.regionsProvider
        DataBindingUtil.setDefaultComponent(BindingComponent())
        val binding = DataBindingUtil.inflate<SaisieOffreVenteBinding>(inflater, R.layout.saisie_offre_vente, container, false)

        binding.pickedDate = datePicked

        val c = Calendar.getInstance()
        c.time = datePicked.date   // Now use today date.
        c.add(Calendar.DATE, 15) // Adding 5 days
        offerToUI.expirationDate?.set(c.time)
        binding.offre = offerToUI
        binding.produit.text = offerToUI.produit?.get()

        offerToUI.produit?.set(binding.produit.toString())
        offerToUI.telephone?.set(myPreferences.login?:"")
        binding.authorPhone.setText(myPreferences.login)
        val qtyTextWatcher = object : SimpleTextWatcher() {
            override fun onTextChanged(newValue: String) {
                offerToUI.quantite?.get()?.let { offerToUI.prixUnitaire?.get()?.times(it)?.let { offerToUI.montant?.set(it) } }
           }
        }
        binding.quantity.addTextChangedListener(qtyTextWatcher)
        image = binding.image
        binding.image.setOnClickListener { selectImage() }
        if (offerToUI.photo != "") {
            if (bm == null) {
                options = BitmapFactory.Options()
                options.inJustDecodeBounds = false
                offerToUI.photo
                bm = BitmapFactory.decodeFile(offerToUI.photo, options)
                if (bm != null)
                    binding.image.setImageBitmap(bm)
                bm = null

            }
        }
        val unitpriceTextWatcher = object : SimpleTextWatcher() {
            override fun onTextChanged(newValue: String) {
                if (offerToUI.prixGlobal!! > 0.0)
                    offerToUI.montant?.set(offerToUI.prixGlobal?:0.0)
            }
        }
        if (offerToUI.photo != "") {
            if (bm == null) {
                options = BitmapFactory.Options()
                options.inJustDecodeBounds = false
                offerToUI.photo
                bm = BitmapFactory.decodeFile(offerToUI.photo, options)
                if (bm != null)
                    binding.image.setImageBitmap(bm)
                bm = null

            }
        }
        binding.unitPrice.addTextChangedListener(unitpriceTextWatcher)
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            bus.post("Verifiez que vous disposez des autorisations necessaires pour utiliser le GPS ou vérifier que le GPS est active")
        } else {
            mLocationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    offerToUI.longitude=location.longitude.toString()
                    offerToUI.latitude=location.latitude.toString()
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

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LAST_SAVED_OFFRE_VENTE_TAG, Parcels.wrap(offerToUI))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longitude
        // as you specify a parent activity in AndroidManifest.xml.


        val id = item.itemId
        when (id) {
            R.id.action_next -> {
                offerToUI.offerType?.set("Vente")
                produitChoisi.let { offerToUI.produit?.set(it) }

                if (UIUtils.isValidPhoneNumber(requireContext(), offerToUI.telephone?.get().toString())) {
                    if (offerToUI.validate()) {
                          offerToUI.date?.set(datePicked.date)
                          iSaverOffer?.save(offerToUI)
                           myParent.addFragment(ListOffersVenteUI())

                    } else {
                        bus.post("Veuillez vérifier que vous avez saisi toutes les informations obligatoires")
                    }
                }



            }
        }
        return true
    }

    override fun doBack(): Boolean {

        return true

    }

    companion object {
        val OFFER_TO_UI = "OFFER_TO_UI"
    }

}
