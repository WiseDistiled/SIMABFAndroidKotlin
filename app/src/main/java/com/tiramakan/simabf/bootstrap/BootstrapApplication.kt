package com.tiramakan.simabf.bootstrap

import android.app.Application


/**
 * SIMABF application
 */
/**
 * Create main application
 */
abstract class BootstrapApplication : Application() {
    lateinit var component: BootstrapComponent
        private set


    override fun onCreate() {
        super.onCreate()
//        try {
//            // Google Play will install latest OpenSSL
//            ProviderInstaller.installIfNeeded(getApplicationContext());
//            SSLContext sslContext;
//            sslContext = SSLContext.getInstance("TLSv1.2");
//            sslContext.init(null, null, null);
//            sslContext.createSSLEngine();
//        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
//        | NoSuchAlgorithmException | KeyManagementException e) {
//            e.printStackTrace();
//        }
 //   }
        init()

        instance = this

        //   Perform injection
        //  Injector.init(this, )
        component = DaggerComponentInitializer.init()

        onAfterInjection()
    }

    protected abstract fun onAfterInjection()

    protected abstract fun init()
    open fun setDefaultConfiguration() {

    }

    object DaggerComponentInitializer {
        internal fun init(): BootstrapComponent {
            return DaggerBootstrapComponent.builder()
                    .androidModule(AndroidModule())
                    .bootstrapModule(BootstrapModule())
                    .build()
        }

    }

    companion object {

        lateinit var instance: BootstrapApplication
            private set

        fun component(): BootstrapComponent {
            return instance.component
        }
    }
}
