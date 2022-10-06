package com.tiramakan.simabf.bootstrap


import java.io.InputStream


class BootstrapApplicationImpl : BootstrapApplication() {
    override fun onAfterInjection() {

    }

    override fun onCreate() {
        super.onCreate()
        //    Fabric.with(this, new Crashlytics());
        setDefaultConfiguration()

    }



    override fun init() {


        //  Timber.plant(new Timber.DebugTree()

        //    );
    }
}
