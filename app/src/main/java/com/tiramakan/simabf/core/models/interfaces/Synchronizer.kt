package com.tiramakan.simabf.core.models.interfaces

/**
 * Created by tiramakan on 23/01/2016.
 */
interface Synchronizer {
    fun execute(): Boolean?
    fun remove(): Boolean?
}
