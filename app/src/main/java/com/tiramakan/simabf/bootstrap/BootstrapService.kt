package com.tiramakan.simabf.bootstrap

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast

import com.google.gson.Gson
import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.beansProviders.RealmServiceProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RetrofitLoginProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RetrofitProvider
import com.tiramakan.simabf.bootstrap.util.MyNetworkDetector
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.constants.Constants
import com.tiramakan.simabf.core.modelView.*
import com.tiramakan.simabf.core.models.interfaces.retrofit.*
import com.tiramakan.simabf.core.models.notifiers.*
import com.tiramakan.simabf.core.models.realm.*
import com.tiramakan.simabf.core.models.retrofitObjets.Login
import com.tiramakan.simabf.core.models.retrofitObjets.UserRetrieved

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import io.realm.Case
import io.realm.Realm
import io.realm.RealmResults
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*


/**
 * Bootstrap API service
 */
class BootstrapService
/**
 * Create bootstrap service
 *
 * @param retrofitProvider The Retrofit that allows HTTP Communication.
 */
(private val retrofitProvider: RetrofitProvider, private var retrofitLoginProvider: RetrofitLoginProvider, protected var realmServiceProvider: RealmServiceProvider, internal var bus: Bus, protected var gson: Gson, val networkDetector: MyNetworkDetector,
 private val smsSender: SMSSender) {
    protected var context: Context
    protected var myPreferences: MyPreferences

    private val userService: IRetrofitUser
        get() = retrofitProvider.retrofit.create(IRetrofitUser::class.java)

    private val gpsDepotService: IRetrofitGPSDepot
        get() = retrofitProvider.retrofit.create(IRetrofitGPSDepot::class.java)
    private val gpsMarcheService: IRetrofitGPSMarche
        get() = retrofitProvider.retrofit.create(IRetrofitGPSMarche::class.java)

    private val produitService: IretrofitProduit
        get() = retrofitProvider.retrofit.create(IretrofitProduit::class.java)

    private val priceService: IRetrofitPrice
        get() = retrofitProvider.retrofit.create(IRetrofitPrice::class.java)
    private val requestService: IRetrofitRequest
        get() = retrofitProvider.retrofit.create(IRetrofitRequest::class.java)
    private val marcheService: IRetrofitMarche
        get() = retrofitProvider.retrofit.create(IRetrofitMarche::class.java)
    private val mesureService: IRetrofitMesure
        get() = retrofitProvider.retrofit.create(IRetrofitMesure::class.java)
    private val userParamService: IRetrofitUserParams
        get() = retrofitProvider.retrofit.create(IRetrofitUserParams::class.java)
    private val depotService: IRetrofitDepot
        get() = retrofitProvider.retrofit.create(IRetrofitDepot::class.java)
    private val reseauService: IRetrofitReseau
        get() = retrofitProvider.retrofit.create(IRetrofitReseau::class.java)

    private val typePrixService: IRetrofitTypePrix
        get() = retrofitProvider.retrofit.create(IRetrofitTypePrix::class.java)
    private val qualityService: IRetrofitQualite
        get() = retrofitProvider.retrofit.create(IRetrofitQualite::class.java)

    private val stockService: IRetrofitStock
        get() = retrofitProvider.retrofit.create(IRetrofitStock::class.java)

    private val regionService: IRetrofitRegion
        get() = retrofitProvider.retrofit.create(IRetrofitRegion::class.java)

    private val etalonnagePostService: IRetrofitPostEtalonnage
        get() = retrofitProvider.retrofit.create(IRetrofitPostEtalonnage::class.java)

    private val etalonnageService: IRetrofitEtalonnage
        get() = retrofitProvider.retrofit.create(IRetrofitEtalonnage::class.java)




    private val offerVenteService: IRetrofitOfferVente
        get() = retrofitProvider.retrofit.create(IRetrofitOfferVente::class.java)
    private val offerAchatService: IRetrofitOfferAchat
        get() = retrofitProvider.retrofit.create(IRetrofitOfferAchat::class.java)
    private val loginService: IRetrofitLogin
        get() = retrofitLoginProvider.retrofit.create(IRetrofitLogin::class.java)
    private val newsService: IRetrofitNews
        get() = retrofitProvider.retrofit.create(IRetrofitNews::class.java)
    private val EtatApproService: IRetrofitEtatAppro
        get() = retrofitProvider.retrofit.create(IRetrofitEtatAppro::class.java)

    val listMesures: ArrayList<String>
        get() {
            val theMesures = ArrayList<String>()
            val mesures = realmServiceProvider.realm.where(Mesure::class.java)
                    ?.findAll()
            mesures?.sort("nom")

            for (mesure in mesures!!) {
                theMesures.add(mesure.code.toString())
            }
            return theMesures

        }
    /**
     * Get all bootstrap Users that exist on simabf.net
     */

    val stocks: RealmResults<Stock>
        get() = realmServiceProvider.realm.where(Stock::class.java)?.findAll() as RealmResults<Stock>
    val utilisateurs: RealmResults<Utilisateur>
        get() = realmServiceProvider.realm.where(Utilisateur::class.java)?.findAll() as RealmResults<Utilisateur>
    val reseaux: RealmResults<Reseau>
        get() = realmServiceProvider.realm.where(Reseau::class.java)?.findAll() as RealmResults<Reseau>
    val regions: RealmResults<Region>
        get() = realmServiceProvider.realm.where(Region::class.java)?.findAll() as RealmResults<Region>

    val qualities: RealmResults<Qualite>
        get() = realmServiceProvider.realm.where(Qualite::class.java)?.findAll() as RealmResults<Qualite>

    val etatsAppro: RealmResults<EtatAppro>
        get() = realmServiceProvider.realm.where(EtatAppro::class.java)?.findAll() as RealmResults<EtatAppro>

    val produits: RealmResults<Produit>
        get() = realmServiceProvider.realm.where(Produit::class.java)?.findAll() as RealmResults<Produit>
    val produitCategories: RealmResults<ProduitCategorie>
        get() = realmServiceProvider.realm.where(ProduitCategorie::class.java)?.findAll() as RealmResults<ProduitCategorie>
    val mesures: RealmResults<Mesure>
        get() = realmServiceProvider.realm.where(Mesure::class.java)?.findAll() as RealmResults<Mesure>
    val marches: RealmResults<Marche>
        get() = realmServiceProvider.realm.where(Marche::class.java)?.findAll() as RealmResults<Marche>
    val typesPrix: RealmResults<TypePrix>
        get() = realmServiceProvider.realm.where(TypePrix::class.java)?.findAll() as RealmResults<TypePrix>
    val marchesActeurs: RealmResults<ActeurMarche>
        get() = realmServiceProvider.realm.where(ActeurMarche::class.java)?.findAll() as RealmResults<ActeurMarche>
    val depots: RealmResults<Depot>
        get() = realmServiceProvider.realm.where(Depot::class.java)?.findAll() as RealmResults<Depot>

    init {
        this.context = smsSender.context
        this.myPreferences = smsSender.myPreferences
    }

    fun getProduitsByCategorie(categorie: String): RealmResults<Produit>? {
        return if (categorie != "") {
            realmServiceProvider.realm.where(Produit::class.java)?.equalTo("categorie.nom", categorie)?.findAll()
        } else
            realmServiceProvider.realm.where(Produit::class.java)?.findAll()
    }

    fun getProduitSartingWith(produitName: String): Produit? {
        return realmServiceProvider.realm.where(Produit::class.java)?.contains("nom", produitName.trim { it <= ' ' }, Case.INSENSITIVE)?.findFirst()
    }

    fun getMesureSartingWith(mesureName: String): Mesure? {
        return realmServiceProvider.realm.where(Mesure::class.java)?.contains("nom", mesureName.trim { it <= ' ' }, Case.INSENSITIVE)?.findFirst()
    }

    fun getMarchesSartingWith(marcheName: String): RealmResults<Marche>? {
        return realmServiceProvider.realm.where(Marche::class.java)?.contains("nom", marcheName.trim { it <= ' ' }, Case.INSENSITIVE)?.findAll()
    }

    fun getProduitCategorieSartingWith(produitCategoryName: String): ProduitCategorie? {
        return realmServiceProvider.realm.where(ProduitCategorie::class.java)?.contains("nom", produitCategoryName.trim { it <= ' ' }, Case.INSENSITIVE)?.findFirst()
    }

    fun getDepotSartingWith(depotName: String): Depot? {
        return realmServiceProvider.realm.where(Depot::class.java)?.contains("nom", depotName.trim { it <= ' ' }, Case.INSENSITIVE)?.findFirst()
    }

    fun getMarcheSartingWith(marcheName: String): Marche? {
        return realmServiceProvider.realm.where(Marche::class.java)?.contains("nom", marcheName.trim { it <= ' ' }, Case.INSENSITIVE)?.findFirst()
    }


    fun removeStock(stockForUI: StockForUI?) {
        if (stockForUI != null) {
            val realm = realmServiceProvider.realm

            realm.executeTransaction {
                realm.where(Stock::class.java)?.equalTo("id", stockForUI.id)?.findFirst()?.deleteFromRealm()

            }
        }

    }
    fun removeEtalonnage(etalonnageToUI: EtalonnageToUI?) {
        if (etalonnageToUI != null) {
            val realm = realmServiceProvider.realm

            realm.executeTransaction {
                realm.where(Etalonnage::class.java).equalTo("id", etalonnageToUI.id).findFirst()?.deleteFromRealm()

            }
        }

    }

    fun setStockSended(stockForUI: StockForUI?) {
        if (stockForUI != null) {
            val realm = realmServiceProvider.realm
            realm.executeTransaction {
                realm.where(Stock::class.java)?.equalTo("id", stockForUI.id)?.findFirst()?.status = "sended"
            }
        }

    }

    fun setEtalonnageSended(etalonnageToUI: EtalonnageToUI?) {
        if (etalonnageToUI != null) {
            val realm = realmServiceProvider.realm
            realm.executeTransaction {
                realm.where(Etalonnage::class.java)?.equalTo("id", etalonnageToUI.id)?.findFirst()?.status = "sended"
            }
        }

    }

    fun setPriceSended(priceToUI: PriceToUI?) {
        if (priceToUI != null) {
            val realm = realmServiceProvider.realm

            realm.executeTransaction {
                realm.where(Prix::class.java)?.equalTo("id", priceToUI.id)?.findFirst()?.status = "sended"
            }

        }

    }

    fun removePrice(priceToUI: PriceToUI?) {
        if (priceToUI != null) {
            val realm = realmServiceProvider.realm

            realm.executeTransaction {
                realm.where(Prix::class.java)?.equalTo("id", priceToUI.id)?.findFirst()?.deleteFromRealm()
            }

        }

    }

    fun setOfferSended(offerToUI: OfferToUI?) {
        if (offerToUI != null) {
            val realm = realmServiceProvider.realm

            realm.executeTransaction {
                realm.where(Offre::class.java)?.equalTo("id", offerToUI.id)?.findFirst()?.status = "sended"
            }

        }

    }

    fun removeOffer(offerToUI: OfferToUI?) {
        if (offerToUI != null) {
            val realm = realmServiceProvider.realm
            realm.executeTransaction{
                realm.where(Offre::class.java)?.equalTo("id", offerToUI.id)?.findFirst()?.deleteFromRealm()
            }
        }
    }


    fun removeUser(userToUI: UserToUI?) {
        if (userToUI != null) {
            val realm = realmServiceProvider.realm

            realm.executeTransaction {
                realm.where(Utilisateur::class.java)?.equalTo("id", userToUI.id)?.findFirst()?.deleteFromRealm()
            }

        }

    }

    fun setUserSended(userToUI: UserToUI?) {
        if (userToUI != null) {
            val realm = realmServiceProvider.realm

            realm.executeTransaction {
                realm.where(Utilisateur::class.java)?.equalTo("id", userToUI.id)?.findFirst()?.status = "sended"
            }

        }

    }


    @Throws(IOException::class)
    fun sendPrice(prix: Prix) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {
                if (prix.produit != null && prix.marche != null) {
                    bus.post("Début de l'envoi des prix")
                    val priceSender = priceService
                    val marche = prix.marche?.nom?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val produit = prix.produit?.nom?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val typePrix = prix.typePrix.toRequestBody("text/plain".toMediaTypeOrNull())
                    val date = UIUtils.dateFormat.format(prix.date).toRequestBody("text/plain".toMediaTypeOrNull())
                    val montant = prix.montant.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val mesure = prix.mesure?.code?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val comment = prix.comment.toRequestBody("text/plain".toMediaTypeOrNull())
                    val sendPrice:Call<RequestResponse>

                    if (prix.note_contenu > "") {

                          val note_contenu = prix.note_contenu.toRequestBody("text/plain".toMediaTypeOrNull())
                          val note_longitude = prix.note_longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                          val note_latitude = prix.note_latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                                val file = File(prix.note_photo)

                                if (file.exists()) {
                                    if (file.length() <= Constants.Extra.MAX_IMAGE_SIZE) {
                                       val  photo = file.asRequestBody(contentType = "image/*".toMediaTypeOrNull())
                                        sendPrice = priceSender.sendWithNoteAndPhoto(photo,typePrix,marche, produit, date, montant, mesure,
                                                note_contenu,note_longitude,note_latitude,comment)
                                    } else {

                                        bus.post("Le fichier que vous tentez d'envoyer est trop volumineux sa taille est :" + file.length().toString() + ", La taille maximum est : " + Constants.Extra.MAX_IMAGE_SIZE.toString() + " octets"+
                                        "Les données seront envoyés sans la photo")
                                         sendPrice = priceSender.sendWithNote(typePrix,marche, produit, date, montant, mesure,
                                                note_contenu,note_longitude,note_latitude,comment)
                                    }
                                } else {
                                    if (prix.note_photo>"") {
                                        bus.post("Le fichier " + prix.note_photo + " n'' a pas ete retrouve, Les données seront envoyés sans la photo ")
                                    }
                                     sendPrice = priceSender.sendWithNote(typePrix,marche, produit, date, montant, mesure,
                                            note_contenu,note_longitude,note_latitude,comment)
                                }

                            }else {
                                    sendPrice = priceSender.sendWithoutNote(typePrix,marche, produit, date, montant, mesure,comment
                                    )
                          }

                       sendPrice.enqueue(object : Callback<RequestResponse> {
                        override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                            if (response.body() != null) {
                                if (response.body()?.result != null) {
                                    realmServiceProvider.realm.executeTransaction { 
                                        prix.status = "Sended"
                                    }
                                    bus.post(PriceSended("Prix envoyé avec succès"))
                                } else
                                    bus.post(PriceSended("Echec cause : " + response.body()?.prettyMessage))

                            } else {
                                if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                    Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                                else
                                    Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                            if (t.cause != null)
                                bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                        }


                    })
                }
            } else {
                bus.post(ActionFailed("veuillez vérifiez vos paramètres de connexion (login, mot de passe) "))
            }

        } else {
            if (myPreferences.sendRequestBySMS || myPreferences.forceTransportSMS) {
                val priceToSMS = PriceToSMS(prix)
                confirmSendBySMS(priceToSMS.messageSMSPrixDeGros)
                confirmSendBySMS(priceToSMS.messageSMSPrixDeDetail)
            }
        }
    }

    fun confirmSendBySMS(message: String) {
            smsSender.sendMessage(message)
    }

    fun sendBySMS(message: String) {
            smsSender.sendSMS(message)
    }

    @Throws(IOException::class)
    fun sendPriceRequest(requests: String) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {
                var globalRequest = ""
                val priceRequester = requestService
                val sendRequest = priceRequester.send(requests)

                sendRequest.enqueue(object : Callback<RequestResponse> {
                    override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                        if (response.body() != null) {
                            bus.post(response.body())
                        } else
                            bus.post(RequestResponse("Aucune réponse reçue du serveur", "", false))
                    }

                    override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                        if (t.cause != null)
                            bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                    }

                })
            }
        } else {
                confirmSendBySMS(requests)
        }
    }

    @Throws(IOException::class)
    fun sendOfferRequest(requests: String) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {
                val requester = requestService
                val sendRequest = requester.send(requests)

                sendRequest.enqueue(object : Callback<RequestResponse> {
                    override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                        if (response.body() != null)
                            bus.post(response.body())
                        else
                            bus.post(RequestResponse("Aucune réponse reçue du serveur", "", false))
                    }

                    override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                        if (t.cause != null)
                            bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                    }
                })
            } else {
                Toast.makeText(context, "Vous devez renseigner vos paramètres d'identification dans paramètres->Authentification", Toast.LENGTH_LONG).show()
            }
        } else {
                confirmSendBySMS(requests)
        }

    }

    @Throws(IOException::class)
    fun sendStockRequest(requests: String) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {
                val requester = requestService
                val sendRequest = requester.send(requests)

                sendRequest.enqueue(object : Callback<RequestResponse> {
                    override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                        if (response.body() != null)
                            bus.post(response.body())
                        else
                            bus.post(RequestResponse("Aucune réponse reçue du serveur", "", false))
                    }

                    override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                        if (t.cause != null)
                            bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                    }


                })
            }
        } else {
                confirmSendBySMS(requests)
        }
    }




    @Throws(IOException::class)
    fun sendEtalonnage(etalonnage: Etalonnage) {

                if (etalonnage.valeur != 0.0) {
                    bus.post("Début de l'envoi de l''étalonnage")
                    val etalonnageSender = etalonnagePostService
                    val uml = etalonnage.uml.toRequestBody("text/plain".toMediaTypeOrNull())
                    val ul = etalonnage.ul.toRequestBody("text/plain".toMediaTypeOrNull())
                    val valeur = etalonnage.valeur.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val marche = etalonnage.marche.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val dateEtalon = etalonnage.date.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val sendEtalon = etalonnageSender.send(uml, ul, valeur, dateEtalon, marche)
                    sendEtalon.enqueue(object : Callback<RequestResponse> {
                        override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                            if (response.body() != null) {
                                if (response.body()?.result !=null) {
                                    realmServiceProvider.realm.executeTransaction {
                                        etalonnage.status = "Sended"
                                    }
                                    bus.post(EtalonnageSended("Etalonnage envoyé avec succès"))
                                } else {
                                    bus.post(EtalonnageSended("Echec, cause :" + response.body()?.prettyMessage))
                                }
                                } else {
                                if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                    Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                                else
                                    Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                            }

                        }

                        override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                            if (t.cause != null)
                                bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                        }


                    })
                }
    }

    @Throws(IOException::class)
    fun sendStock(stock: Stock) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {
                if (stock.produit != null && stock.mesure != null) {
                    bus.post("Début de l'envoi des stocks")
                    val stockSender = stockService
                    val depot = stock.depot?.code?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val mesure = stock.mesure?.code?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val produit = stock.produit?.code?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val balance = stock.balance.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val stockDate = stock.date
                    val date = UIUtils.dateFormat.format(stockDate).toRequestBody("text/plain".toMediaTypeOrNull())
                    val sendStock = stockSender.send(depot, mesure, produit, date, balance)
                    sendStock.enqueue(object : Callback<RequestResponse> {
                        override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                            if (response.body() != null) {
                                if (response.body()?.result !=null) {
                                    realmServiceProvider.realm.executeTransaction { 
                                        stock.status = "Sended"
                                    }

                                    bus.post(StockSended("Stock envoyé avec succès"))
                                } else
                                    bus.post(StockSended("Echec, cause :" + response.body()?.prettyMessage))
                            } else {
                                if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                    Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                                else
                                    Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                            }

                        }

                        override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                            if (t.cause != null)
                                bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                        }


                    })
                }
            }
        } else {
            confirmSendBySMS(StockToSMS(stock).message)

        }
    }

    @Throws(IOException::class)
    fun sendOfferVente(offre: Offre) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            bus.post("Début de l'envoi des offres")
            if (login()) {
                val offerSender = offerVenteService
                val sendOffer:Call<RequestResponse>
                if (offre.produit != null && offre.mesure != null) {
                    val offerType = offre.offerType.toRequestBody("text/plain".toMediaTypeOrNull())
                    val date = UIUtils.dateFormat.format(offre.date).toRequestBody("text/plain".toMediaTypeOrNull())
                    val quantite = offre.quantite.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val mesure = offre.mesure?.code.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val conditions = offre.conditions.toRequestBody("text/plain".toMediaTypeOrNull())
                    val produit = offre.produit?.code?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val prixUnitaire = offre.prixUnitaire.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val montant = offre.montant.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val nomAuteur = offre.nomAuteur.toRequestBody("text/plain".toMediaTypeOrNull())
                    val telephone = offre.telephone.toRequestBody("text/plain".toMediaTypeOrNull())
                    val email = offre.email.toRequestBody("text/plain".toMediaTypeOrNull())
                    val longitude = offre.longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val latitude = offre.latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val lieuxStockage = offre.lieuxStockage.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val lieuxLivraison = offre.lieuxLivraison.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                    val qualite: RequestBody
                    qualite = offre.qualite?.code?.toRequestBody("text/plain".toMediaTypeOrNull())!!
                    val region: RequestBody
                    region = offre.region?.code?.toRequestBody("text/plain".toMediaTypeOrNull())!!

                    val file = File(offre.photo)

                    if (file.exists()) {
                        if (file.length() <= Constants.Extra.MAX_IMAGE_SIZE) {
                            val  photo = file.asRequestBody(contentType = "image/*".toMediaTypeOrNull())
                            sendOffer = offerSender.sendWithPhoto(offerType, date, quantite, mesure, conditions,
                                    produit, prixUnitaire, montant,  qualite,nomAuteur, telephone, email,
                                    longitude,latitude,photo,lieuxStockage,lieuxLivraison,region)
                        } else {

                            bus.post("Le fichier que vous tentez d'envoyer est trop volumineux sa taille est :" + file.length().toString() + ", La taille maximum est : " + Constants.Extra.MAX_IMAGE_SIZE.toString() + " octets"+
                                    "Les données seront envoyés sans la photo")
                            sendOffer = offerSender.send(offerType, date, quantite, mesure, conditions,
                                    produit, prixUnitaire, montant,  qualite,nomAuteur, telephone, email,
                                    longitude,latitude,lieuxStockage,lieuxLivraison,region)
                        }
                    } else {
                        if (offre.photo>"") {
                            bus.post("Le fichier " + offre.photo + " n'' a pas ete retrouve, Les données seront envoyés sans la photo ")
                        }
                            sendOffer = offerSender.send(offerType, date, quantite, mesure, conditions,
                                    produit, prixUnitaire, montant, qualite, nomAuteur, telephone, email,
                                    longitude, latitude, lieuxStockage, lieuxLivraison, region)

                    }
               sendOffer.enqueue(object : Callback<RequestResponse> {
                        override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                            if (response.body() != null) {
                                if (response.body()?.result !=null) {
                                    realmServiceProvider.realm.executeTransaction { 
                                        offre.status = "Sended"
                                    }

                                    bus.post(OfferSended("Offre envoyée avec succès"))
                                } else
                                    bus.post(OfferSended("Echec cause : " + response.body()?.prettyMessage))
                            } else {
                                if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                    Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                                else
                                    Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                            if (t.cause != null)
                                bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                        }

                    })
                }
            } else {
                bus.post(ActionFailed("veuillez vérifiez vos paramètres de connexion (login, mot de passe) "))
            }
        } else {
            confirmSendBySMS(OfferToSMS(offre).message)
        }

    }

    @Throws(IOException::class)
    fun sendOfferAchat(offre: Offre) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            bus.post("Début de l'envoi des offres d'achat")
            if (login()) {
                val offerSender = offerAchatService
                if (offre.produit != null && offre.mesure != null) {
                    val offerType = offre.offerType.toRequestBody("text/plain".toMediaTypeOrNull())
                    val date = UIUtils.dateFormat.format(offre.date).toRequestBody("text/plain".toMediaTypeOrNull())
                    val quantite = offre.quantite.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val mesure = offre.mesure?.code.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val conditions = offre.conditions.toRequestBody("text/plain".toMediaTypeOrNull())
                    val produit = offre.produit?.code?.toRequestBody("text/plain".toMediaTypeOrNull())
                    val prixUnitaire = offre.prixUnitaire.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val montant = offre.montant.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val nomAuteur = offre.nomAuteur.toRequestBody("text/plain".toMediaTypeOrNull())
                    val telephone = offre.telephone.toRequestBody("text/plain".toMediaTypeOrNull())
                    val email = offre.email.toRequestBody("text/plain".toMediaTypeOrNull())
                    val longitude = offre.longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val latitude = offre.latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val lieuxLivraison = offre.lieuxLivraison.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                    val qualite: RequestBody
                    qualite = offre.qualite?.code?.toRequestBody("text/plain".toMediaTypeOrNull())!!
                    val region: RequestBody
                    region = offre.region?.code?.toRequestBody("text/plain".toMediaTypeOrNull())!!

                    val sender = offerSender.send(offerType, date, quantite, mesure, conditions,
                            produit, prixUnitaire, montant,  qualite,nomAuteur, telephone, email,longitude,latitude,lieuxLivraison,region)
                    sender.enqueue(object : Callback<RequestResponse> {
                        override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                            if (response.body() != null) {
                                if (response.body()?.result !=null) {
                                    realmServiceProvider.realm.executeTransaction { 
                                        offre.status = "Sended"
                                    }

                                    bus.post(OfferSended("Offre envoyée avec succès"))
                                } else
                                    bus.post(OfferSended("Echec cause : " + response.body()?.prettyMessage))
                            } else {
                                if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                    Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                                else
                                    Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                            if (t.cause != null)
                                bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                        }

                    })
                }
            } else {
                bus.post(ActionFailed("veuillez vérifiez vos paramètres de connexion (login, mot de passe) "))
            }
        } else {
            confirmSendBySMS(OfferToSMS(offre).message)
        }

    }



    fun login(): Boolean {
        try {
            if (networkDetector.isNetworkAvailable) {

                if (myPreferences.isLoginAndPasswordOk) {
                    val logcall = loginService.login(Login(myPreferences.login, myPreferences.password))
                    val result: Response<UserRetrieved> = logcall.execute()
                    try {
                        myPreferences.token = result.body()?.token?.toString()?:""

                   if  (myPreferences.token.equals(""))
                        return false
                         else return true
                    } catch (e: Exception) {
                        Toast.makeText(context, " Echec Connexion au serveur  : "+ myPreferences.webserviceURL +" verifiez votre code utilisateur et votre mot de passe ou contactez votre administrateur " , Toast.LENGTH_LONG).show()
                        return false
                    }
                } else {
                    Toast.makeText(context, " Echec Connexion au serveur  : "+ myPreferences.webserviceURL +" verifiez votre code utilisateur et votre mot de passe ou contactez votre administrateur " , Toast.LENGTH_LONG).show()
                    return false
                }
            } else {
                Toast.makeText(context, "Vous devez renseigner vos paramètres d'identification dans paramètres->Authentification", Toast.LENGTH_LONG).show()
                return false
            }
        }
        catch (e:Exception){
            Toast.makeText(context, "Impossible d'accéder au serveur rest  : "+ myPreferences.webserviceURL +e.message, Toast.LENGTH_LONG).show()
            return false
        }

    }

    @Throws(IOException::class)
    protected fun getCompressedImage(fileName: String): ByteArrayOutputStream {
        val out = ByteArrayOutputStream()
        val original = BitmapFactory.decodeStream(context.assets.open(fileName))
        if (fileName.toLowerCase(Locale.FRANCE).endsWith("png")) {
            original.compress(Bitmap.CompressFormat.PNG, 100, out)
        } else {
            original.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return out

    }


    @Throws(IOException::class)
    fun sendNews(news: Info) {
        if (login()) {
            bus.post("Début de l'envoi des informations")
            val newsSender = newsService
            val title = news.titre.toRequestBody("text/plain".toMediaTypeOrNull())
            val contenu = news.contenu.toRequestBody("text/plain".toMediaTypeOrNull())
            val url = news.url.toRequestBody("text/plain".toMediaTypeOrNull())
            val date = UIUtils.dateFormat.format(news.date).toRequestBody("text/plain".toMediaTypeOrNull())
            val longitude = news.longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val latitude = news.latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val sender = newsSender.send(title, contenu, url, date, longitude, latitude)
            sender.enqueue(object : Callback<RequestResponse> {
                override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                    if (response.body() != null) {
                        if (response.body()?.result !=null) {
                            realmServiceProvider.realm.executeTransaction { 
                                news.status = "Sended"
                            }

                            bus.post(NewsSended("Info envoyée avec succès"))
                        } else
                            bus.post(NewsSended("Echec cause " + response.body()?.prettyMessage))
                    } else {
                        if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                            Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                    if (t.cause != null)
                        bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                }


            })
        }

    }

    @Throws(IOException::class)
    fun sendGPSDepot(depotGPSToUI:DepotGPSToUI) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {

                bus.post("Début envoie de la position GPS du Dépôt ")
                val depotGPSSender = gpsDepotService

                val longitude = depotGPSToUI.longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val latitude = depotGPSToUI.latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val date = UIUtils.dateFormat.format(Date()).toRequestBody("text/plain".toMediaTypeOrNull())

                val depot = myPreferences.depot.toRequestBody("text/plain".toMediaTypeOrNull())

                val sender = depotGPSSender.send(date, longitude, latitude, depot)
                sender.enqueue(object : Callback<RequestResponse> {
                    override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                        if (response.body() != null) {
                            if (response.body()?.result !=null) {
                                realmServiceProvider.realm.executeTransaction {

                                }

                                bus.post(UserSended("Position GPS du dépôt "+myPreferences.depot+" envoyé avec succès"))
                            } else
                                bus.post(UserSended("Echec, cause : " + response.body()?.prettyMessage))
                        } else {
                            if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                            else
                                Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                        if (t.cause != null)
                            bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                    }


                })
            } else {
                bus.post(ActionFailed("veuillez vérifiez vos paramètres de connexion (login, mot de passe) "))
            }
        } else
            Toast.makeText(context, "Désolé, vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show()


    }

    @Throws(IOException::class)
    fun sendGPSMarche(gpsPositionForUI:MarcheGPSToUI) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {
                bus.post("Début de la position GPS du Marché ")
                val marcheGPSSender = gpsMarcheService

                val longitude = gpsPositionForUI.longitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val latitude = gpsPositionForUI.latitude.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val date = UIUtils.dateFormat.format(Date()).toRequestBody("text/plain".toMediaTypeOrNull())

                val marche =gpsPositionForUI.marche.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                val sender = marcheGPSSender.send(date, longitude, latitude, marche)
                sender.enqueue(object : Callback<RequestResponse> {
                    override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                        if (response.body() != null) {
                            if (response.body()?.result !=null) {
                                realmServiceProvider.realm.executeTransaction {

                                }

                                bus.post(UserSended("Position GPS du marché "+gpsPositionForUI.marche+" envoyé avec succès"))
                            } else
                                bus.post(UserSended("Echec, cause : " + response.body()?.prettyMessage))
                        } else {
                            if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                            else
                                Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                        if (t.cause != null)
                            bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                    }


                })
            } else {
                bus.post(ActionFailed("veuillez vérifiez vos paramètres de connexion (login, mot de passe) "))
            }
        } else
            Toast.makeText(context, "Désolé, vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show()
    }

    @Throws(IOException::class)
    fun sendUser(utilisateur: Utilisateur) {
        if (networkDetector.isNetworkAvailable && !myPreferences.forceTransportSMS) {
            if (login()) {

                bus.post("Début de l'envoi de $utilisateur")
                val userSender = userService

                val phone = utilisateur.mobilePhone.toRequestBody("text/plain".toMediaTypeOrNull())
                val firstname = utilisateur.prenom.toRequestBody("text/plain".toMediaTypeOrNull())
                val gender = utilisateur.genre.toRequestBody("text/plain".toMediaTypeOrNull())
                val name = utilisateur.nom.toRequestBody("text/plain".toMediaTypeOrNull())
                val email = utilisateur.email.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val secondPhone = utilisateur.secondPhone.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val reseau = utilisateur.reseau.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val sender = userSender.send(phone, firstname, name, gender, email, secondPhone,reseau)

                sender.enqueue(object : Callback<RequestResponse> {
                    override fun onResponse(call: Call<RequestResponse>, response: Response<RequestResponse>) {
                        if (response.body() != null) {
                            if (response.body()?.result !=null) {
                                realmServiceProvider.realm.executeTransaction { 
                                    utilisateur.status = "Sended"
                                }

                                bus.post(UserSended("Utilisateur envoyé avec succès"))
                            } else
                                bus.post(UserSended("Echec, cause : " + response.body()?.prettyMessage))
                        } else {
                            if (response.message().toUpperCase(Locale.FRANCE).contains("UNAUTHORIZED"))
                                Toast.makeText(context, "Vos identifiants (Login,Mot de passe) ne sont pas valides veuillez contacter l'administrateur de la plateforme simabf pour avoir des identifiants valides ", Toast.LENGTH_LONG).show()
                            else
                                Toast.makeText(context, "Echec cause " + response.message(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<RequestResponse>, t: Throwable) {

                        if (t.cause != null)
                            bus.post(ActionFailed("Echec cause : " + t.cause.toString()))
                    }


                })
            } else {
                bus.post(ActionFailed("veuillez vérifiez vos paramètres de connexion (login, mot de passe) "))
            }
        } else
            Toast.makeText(context, "Désolé, vous n'êtes pas connecté à internet", Toast.LENGTH_LONG).show()


    }


    @Throws(IOException::class)
    fun syncEtatAppro(realm: Realm) {
        val userIRetrofitEtatApproServices = EtatApproService
        val callEtatAppro = userIRetrofitEtatApproServices.etatsAppro

        callEtatAppro.enqueue(object : Callback<List<EtatAppro>> {
            override fun onResponse(call: Call<List<EtatAppro>>, response: Response<List<EtatAppro>>) {
                if (response.body() != null) {
                    val etatsAppro = response.body()
                    if (etatsAppro?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction {
                            realm.copyToRealmOrUpdate(etatsAppro)
                        }

                        bus.post("Fin chargement des états d'approvisionnement")
                    }

                } else
                    bus.post("Echec syncEtatAppro cause " + response.message())
            }

            override fun onFailure(call: Call<List<EtatAppro>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec syncEtatAppro cause : " + t.cause.toString()))
            }

        })


    }

    @Throws(IOException::class)
    fun syncQualities(realm: Realm) {
        val userIRetrofitQualiteServices = qualityService
        val callQualities = userIRetrofitQualiteServices.qualities

        callQualities.enqueue(object : Callback<List<Qualite>> {
            override fun onResponse(call: Call<List<Qualite>>, response: Response<List<Qualite>>) {
                if (response.body() != null) {
                    val qualities = response.body()
                    if (qualities?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction { 
                            realm.copyToRealmOrUpdate(qualities)
                        }

                        bus.post("Fin chargement des qualités")
                    }

                } else
                    bus.post("Echec cause syncQualities " + response.message())
            }

            override fun onFailure(call: Call<List<Qualite>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec syncQualities cause : " + t.cause.toString()))
            }

        })


    }

    fun synchroniser() {
        if (networkDetector.isNetworkAvailable) {
            bus.post("Début de la synchronisation des objets, veuillez patienter peut prendre quelques minutes")

            val realm = realmServiceProvider.realm
             if (login()) {
                 try {
                   realm.let { emptyRealmObjects(it) }
                   realm.let { syncUserParams(it) }
                   realm.let { syncMesures(it) }
                   realm.let { syncProduits(it) }
                   realm.let { syncQualities(it) }
                   realm.let { syncDepots(it) }
                   realm.let { syncReseaux(it) }
                   realm.let { syncTypePrix(it) }
                   realm.let { syncEtatAppro(it) }
                   realm.let { syncEtalonnages(it) }
                   realm.let { syncRegions(it) }
                   realm.let { syncMarches(it) }
                 } catch (exception: Exception) {
                     bus.post(ActionFailed("Echec Synchronisation, cause :  " + exception.message))
                  //   bus.post(exception.message)
                 }
             }

        } else {
            Toast.makeText(context, "Désolé, vous n'êtes pas connecté à internet donc la synchronisation ne peut se faire ", Toast.LENGTH_LONG).show()
        }
    }


    @Throws(IOException::class)
    fun syncDepots(realm: Realm) {
        val IRetrofitDepot = depotService

        val callDepots = IRetrofitDepot.depots

        callDepots.enqueue(object : Callback<List<Depot>> {
            override fun onResponse(call: Call<List<Depot>>, response: Response<List<Depot>>) {
                if (response.body() != null) {
                    val depots = response.body()
                    if (depots?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction { 
                            realm.copyToRealmOrUpdate(depots)
                        }
                       bus.post("Fin chargement des magazins")
                    }

                } else
                    bus.post("Echec cause syncDepots " + response.message())
            }

            override fun onFailure(call: Call<List<Depot>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec cause syncDepots: " + t.cause.toString()))
            }

        })


    }



    @Throws(IOException::class)
    fun syncUserParams(realm: Realm) {
        val IRetrofitUserParams = userParamService
        val callMesures = IRetrofitUserParams.usersParams
        callMesures.enqueue(object : Callback<List<UserParam>> {
            override fun onResponse(call: Call<List<UserParam>>, response: Response<List<UserParam>>) {
                if (response.body() != null) {
                    val userParams = response.body()
                    if (userParams?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction {
                            realm.copyToRealmOrUpdate(userParams)
                        }

                      bus.post("Fin chargement des paramètres utilisateurs")
                    }

                } else
                    bus.post("Pas de données reponse syncUserParams: " + response.message())
            }

            override fun onFailure(call: Call<List<UserParam>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec exception syncUserParams cause : " + t.cause.toString()))
            }


        })


    }

    @Throws(IOException::class)
    fun syncReseaux(realm: Realm) {
        val IRetrofitReseau = reseauService
        val callReseaux = IRetrofitReseau.reseaux
        callReseaux.enqueue(object : Callback<List<Reseau>> {
            override fun onResponse(call: Call<List<Reseau>>, response: Response<List<Reseau>>) {
                if (response.body() != null) {
                    val reseau = response.body()

                    if (reseau?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction {
                            realm.copyToRealmOrUpdate(reseau)
                        }

                      bus.post("Fin chargement des réseaux")
                    }

                } else
                    bus.post("Pas de données syncReseaux reponse: " + response.message())
            }

            override fun onFailure(call: Call<List<Reseau>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec exception syncReseaux cause : " + t.cause.toString()))
            }


        })
    }
    @Throws(IOException::class)
    fun syncRegions(realm: Realm) {
        val IRetrofitRegion = regionService
        val callRegions = IRetrofitRegion.regions
        callRegions.enqueue(object : Callback<List<Region>> {
            override fun onResponse(call: Call<List<Region>>, response: Response<List<Region>>) {
                if (response.body() != null) {
                    val region = response.body()

                    if (region?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction {
                            realm.copyToRealmOrUpdate(region)
                        }

                        bus.post("Fin chargement des régions")
                    }

                } else
                    bus.post("Pas de données syncRegion reponse: " + response.message())
            }

            override fun onFailure(call: Call<List<Region>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec exception syncRegion cause : " + t.cause.toString()))
            }


        })


    }
    @Throws(IOException::class)
    fun syncEtalonnages(realm: Realm) {
        val IRetrofitEtalonnage = etalonnageService

        val callEtalonnages = IRetrofitEtalonnage.etalonnages
        callEtalonnages.enqueue(object : Callback<List<Etalonnage>> {
            override fun onResponse(call: Call<List<Etalonnage>>, response: Response<List<Etalonnage>>) {
                if (response.body() != null) {
                    val etalonnages = response.body()
                    if (etalonnages?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction {
                            realm.copyToRealmOrUpdate(etalonnages)
                        }

                     bus.post("Fin chargement des mesures")
                    }

                } else
                    bus.post("Pas de données syncEtalonnages reponse: " + response.message())
            }

            override fun onFailure(call: Call<List<Etalonnage>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec syncEtalonnages exception cause : " + t.cause.toString()))
            }
        })
    }



    @Throws(IOException::class)
    fun syncMesures(realm: Realm) {
        val IRetrofitMesure = mesureService

        val callMesures = IRetrofitMesure.mesures
        callMesures.enqueue(object : Callback<List<Mesure>> {
            override fun onResponse(call: Call<List<Mesure>>, response: Response<List<Mesure>>) {
                if (response.body() != null) {
                    val mesures = response.body()
                    if (mesures?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction { 
                            realm.copyToRealmOrUpdate(mesures)
                        }

                      bus.post("Fin chargement des mesures")
                    }

                } else
                    bus.post("Pas de données reponse syncMesures: " + response.message())
            }

            override fun onFailure(call: Call<List<Mesure>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec exception syncMesures cause : " + t.cause.toString()))
            }
        })
    }
    @Throws(IOException::class)
    fun syncTypePrix(realm: Realm) {
        val IRetrofitTypePrix = typePrixService

        val callTypePrix = IRetrofitTypePrix.typesPrix
        callTypePrix.enqueue(object : Callback<List<TypePrix>> {
            override fun onResponse(call: Call<List<TypePrix>>, response: Response<List<TypePrix>>) {
                if (response.body() != null) {
                    val typesPrix = response.body()
                    if (typesPrix?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction {
                            realm.copyToRealmOrUpdate(typesPrix)
                        }

                     bus.post("Fin chargement des types de prix")
                    }

                } else
                    bus.post("Pas de données syncTypePrix reponse: " + response.message())
            }

            override fun onFailure(call: Call<List<TypePrix>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec exception syncTypePrix cause : " + t.cause.toString()))
            }


        })


    }



    @Throws(IOException::class)
    fun syncMarches(realm: Realm) {
        val IRetrofitMarche = marcheService

        val callMarches = IRetrofitMarche.marches
        callMarches.enqueue(object : Callback<List<Marche>> {
            override fun onResponse(call: Call<List<Marche>>, response: Response<List<Marche>>) {

                if (response.body() != null) {
                    val marches = response.body()
                    if (marches?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction { 
                            realm.copyToRealmOrUpdate(marches)
                        }

                       bus.post("Fin chargement des marchés marches : ")
                    }
                } else
                    bus.post(ActionFailed("Echec syncMarches cause " + response.errorBody().toString()))

            }

            override fun onFailure(call: Call<List<Marche>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec syncMarches cause : " + t.cause.toString()))
                   // bus.post(ActionFailed("Echec syncMarches cause : " + t.cause.toString()))
            }

        })


    }

    fun loadDatabase(): Boolean {
        return true
    }

    fun emptyRealmObjects(realm: Realm) {

        realm.executeTransaction {
            it.delete(Prix::class.java)
            it.delete(Offre::class.java)
            it.delete(Stock::class.java)
            it.delete(Utilisateur::class.java)
            it.delete(Qualite::class.java)
            it.delete(Produit::class.java)
            it.delete(Marche::class.java)
            it.delete(Depot::class.java)
            it.delete(Mesure::class.java)
            it.delete(TypePrix::class.java)
            it.delete(EtatAppro::class.java)
            it.delete(Reseau::class.java)
            it.delete(Etalonnage::class.java)
            it.delete(Region::class.java)
        }


    }


    @Throws(IOException::class)
    fun syncProduits(realm: Realm) {
        val iretrofitProduit = produitService

        val callProduits = iretrofitProduit.produits


        callProduits.enqueue(object : Callback<List<Produit>> {
            override fun onResponse(call: Call<List<Produit>>, response: Response<List<Produit>>) {
                if (response.body() != null) {
                    val produits = response.body()
                    if (produits?.size!! > 0) {
                        realmServiceProvider.realm.executeTransaction { 
                            realm.copyToRealmOrUpdate(produits)
                        }

                        bus.post("Fin chargement des produits")
                    }

                } else
                    bus.post("Echec cause syncProduits " + response.message())
            }

            override fun onFailure(call: Call<List<Produit>>, t: Throwable) {

                if (t.cause != null)
                    bus.post(ActionFailed("Echec syncProduits cause : " + t.cause.toString()))
            }

        })


    }

}