package com.tiramakan.simabf.bootstrap

import MyPreferencesImpl
import android.content.Context
import android.content.SharedPreferences

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.tiramakan.simabf.core.modelView.StockViewModel
import com.tiramakan.simabf.core.modelView.StockViewModelImpl
import com.tiramakan.simabf.core.models.savers.ISaverNews
import com.tiramakan.simabf.core.models.savers.ISaverNote
import com.tiramakan.simabf.core.models.savers.ISaverOffer
import com.tiramakan.simabf.core.models.savers.ISaverOfferImpl
import com.tiramakan.simabf.core.models.savers.ISaverPriceImpl
import com.tiramakan.simabf.core.models.savers.ISaverStock
import com.tiramakan.simabf.core.models.savers.ISaverNewsImpl
import com.tiramakan.simabf.core.models.savers.ISaverNoteImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.beansProviders.*
import com.tiramakan.simabf.core.models.savers.ISaverPrice
import com.tiramakan.simabf.core.models.savers.ISaverUser
import com.tiramakan.simabf.core.models.savers.UserSaverImpl
import com.tiramakan.simabf.core.models.savers.StockSaverImpl
import com.tiramakan.simabf.bootstrap.util.MyEmulatorDetector
import com.tiramakan.simabf.bootstrap.util.MyEmulatorDetectorImpl
import com.tiramakan.simabf.bootstrap.util.MyNetworkDetector
import com.tiramakan.simabf.bootstrap.util.MyNetworkDetectorImpl
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.core.modelView.EtalonViewModel
import com.tiramakan.simabf.core.modelView.EtalonViewModelImpl

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmObject

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
class BootstrapModule {

    @Singleton
    @Provides
    internal fun provideOttoBus(): Bus {
        return PostFromAnyThreadBus()
    }
    @Provides
    internal fun provideStockViewModel(realmServiceProvider: RealmServiceProvider, preferences: MyPreferences, bus: Bus,
                                       mesureProvider: MesureProvider, depotProvider: DepotProvider): StockViewModel {
        return StockViewModelImpl(realmServiceProvider, preferences, bus, mesureProvider, depotProvider)

    }
    @Provides
    internal fun provideEtalonViewModel(realmServiceProvider: RealmServiceProvider, bus: Bus): EtalonViewModel {
        return EtalonViewModelImpl(realmServiceProvider, bus)

    }
    @Provides
    internal fun provideSMSSender(context: Context, myPreferences: MyPreferences): SMSSender {
        return SMSSender(context, myPreferences)

    }


    @Provides
    internal fun provideRealmServiceProvider(context: Context, preferences: MyPreferences, bus: Bus): RealmServiceProvider {
        return RealmServiceProviderImpl(context, preferences, bus)

    }

    @Provides
    internal fun provideBootstrapServiceProvider(retrofitProvider: RetrofitProvider,retrofitLoginProvider: RetrofitLoginProvider, realmServiceProvider: RealmServiceProvider, bus: Bus, gson: Gson, myNetworkDetector: MyNetworkDetector, smsSender: SMSSender): BootstrapServiceProvider {
        return BootstrapServiceProviderImpl(retrofitProvider,retrofitLoginProvider, realmServiceProvider, bus, gson, myNetworkDetector, smsSender)
    }

    @Provides
    internal fun provideEtatApprovProvider(bsProvider: BootstrapServiceProvider, realm: Realm): EtatApproProvider {
        return EtatApproProviderImpl(bsProvider.service, realm)
    }


    @Provides
    internal fun provideMarcheProvider(bsProvider: BootstrapServiceProvider, realm: Realm): MarcheProvider {
        return MarcheProviderImpl(bsProvider.service, realm)
    }


    @Provides
    internal fun provideMesureProvider(bsProvider: BootstrapServiceProvider, realm: Realm): MesureProvider {
        return MesureProviderImpl(bsProvider.service, realm)
    }

    @Provides
    internal fun provideRegionProvider(bsProvider: BootstrapServiceProvider, realm: Realm): RegionProvider {
        return RegionProviderImpl(bsProvider.service, realm)
    }
    @Provides
    internal fun providePriceSaverProvider(realm: Realm, bus: Bus): ISaverPrice {
        return ISaverPriceImpl(realm, bus)
    }

    @Provides
    internal fun provideUserSaverProvider(realm: Realm, bus: Bus): ISaverUser {
        return UserSaverImpl(realm, bus)
    }

    @Provides
    internal fun provideNoteSaverProvider(realm: Realm, bus: Bus): ISaverNote {
        return ISaverNoteImpl(realm, bus)
    }

    @Provides
    internal fun provideNewsSaverProvider(realm: Realm, bus: Bus): ISaverNews {
        return ISaverNewsImpl(realm, bus)
    }

    @Provides
    internal fun provideAssetImporterProvider(context: Context, bus: Bus): AssetImporter {
        return AssetImporterImpl(context, bus)
    }

    @Provides
    internal fun provideRealm(realmServiceProvider: RealmServiceProvider): Realm {
        return realmServiceProvider.realm
    }


    @Provides
    internal fun provideStockSaverProvider(realm: Realm, bus: Bus): ISaverStock {
        return StockSaverImpl(realm, bus)
    }


    @Provides
    internal fun provideDepotProvider(bootstrapServiceProvider: BootstrapServiceProvider, realm: Realm): DepotProvider {
        return DepotProviderImpl(bootstrapServiceProvider.service, realm)
    }

    @Provides
    internal fun provideProduitProvider(bootstrapServiceProvider: BootstrapServiceProvider, realm: Realm): ProduitProvider {
        return ProduitProviderImpl(bootstrapServiceProvider.service, realm)
    }

    @Provides
    internal fun provideReseauProvider(bootstrapServiceProvider: BootstrapServiceProvider, realm: Realm): ReseauProvider {
        return ReseauProviderImpl(bootstrapServiceProvider.service, realm)
    }

    @Provides
    internal fun provideUserParamProvider(bootstrapServiceProvider: BootstrapServiceProvider, realm: Realm): UserParamProvider {
        return UserParamProviderImpl(bootstrapServiceProvider.service, realm)
    }


    @Provides
    internal fun provideQualiteProvider(bootstrapServiceProvider: BootstrapServiceProvider, realm: Realm): QualiteProvider {
        return QualiteProviderImpl(bootstrapServiceProvider.service, realm)
    }


    @Provides
    internal fun provideAPIAuthenticator(preferences: MyPreferences): APIAuthenticator {
        return APIAuthenticatorImpl(preferences)
    }

    @Provides
    internal fun provideMyNetworkDetector(context: Context): MyNetworkDetector {
        return MyNetworkDetectorImpl(context)
    }

    @Provides
    internal fun provideMyEmulatorDetector(context: Context): MyEmulatorDetector {
        return MyEmulatorDetectorImpl(context)
    }

    @Provides
    internal fun provideMyPreferences(myEmulatorDetector: MyEmulatorDetector, preferences: SharedPreferences): MyPreferences {
        return MyPreferencesImpl(myEmulatorDetector, preferences)
    }

    @Provides
    internal fun provideOfferSaver(realm: Realm, bus: Bus): ISaverOffer {
        return ISaverOfferImpl(realm, bus)
    }

    @Provides
    internal fun provideGson(): Gson {

        return GsonBuilder().setLenient().setDateFormat("dd-MM-yyyy HH:mm:ss").setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaringClass == RealmObject::class.java
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        }).create()

        /**
         * .registerTypeAdapter(Prix.class, new PriceSerializer())
         * .registerTypeAdapter(Marche.class, new MarcheSerializer())
         * .registerTypeAdapter(Mesure.class, new MesureSerializer())
         * .registerTypeAdapter(Produit.class, new ProduitSerializer())
         * GSON instance to use for all request  with date format set up for proper parsing.
         *
         *
         * You can also configure GSON with different naming policies for your API.
         * Maybe your API is Rails API and all json values are lower case with an underscore,
         * like this "first_name" instead of "firstName".
         * You can configure GSON as such below.
         *
         *
         *
         * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
         * .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
         */
    }


    @Provides
    internal fun provideRetrofitProvider(gson: Gson, authenticator: APIAuthenticator, myEmulatorDetector: MyEmulatorDetector, preferences: MyPreferences, bus: Bus): RetrofitProvider {
        return RetrofitProviderImpl(gson, authenticator, myEmulatorDetector, preferences, bus)
    }
    @Provides
    internal fun provideRetrofitLoginProvider(gson: Gson, authenticator: APIAuthenticator, myEmulatorDetector: MyEmulatorDetector, preferences: MyPreferences, bus: Bus): RetrofitLoginProvider {
        return RetrofitLoginProviderImpl(gson, authenticator, myEmulatorDetector, preferences, bus)
    }
}
