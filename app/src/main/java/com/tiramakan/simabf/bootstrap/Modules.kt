package com.tiramakan.simabf.bootstrap


internal object Modules {
    fun list(): Array<Any> {
        return arrayOf(AndroidModule(), BootstrapModule())
    }
}// No instances.
