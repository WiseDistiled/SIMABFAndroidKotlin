package com.tiramakan.simabf.bootstrap

import android.accounts.AccountsException

import com.google.gson.Gson
import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.beansProviders.RealmServiceProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RetrofitLoginProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RetrofitProvider
import com.tiramakan.simabf.bootstrap.util.MyNetworkDetector

import java.io.IOException

/**
 * Provider for a [BootstrapService] instance
 */
class BootstrapServiceProviderImpl(private val retrofitProvider: RetrofitProvider, private var retrofitLoginProvider: RetrofitLoginProvider, private val realmServiceProvider: RealmServiceProvider, protected var bus: Bus, protected var gson: Gson, protected var myNetworkDetector: MyNetworkDetector,
                                   protected var smsSender: SMSSender) : BootstrapServiceProvider {

    /**
     * Get service for configured key provider
     *
     *
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    override val service: BootstrapService
        get() = BootstrapService(retrofitProvider,retrofitLoginProvider, realmServiceProvider, bus, gson, myNetworkDetector, smsSender)
}
