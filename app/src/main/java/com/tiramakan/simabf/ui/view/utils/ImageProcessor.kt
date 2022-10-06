package com.tiramakan.simabf.ui.view.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 * Created by tiramakan on 06/04/2016.
 */
class ImageProcessor(private val mFilePath: String, private val mDestinationDirectory: String, private val mFinishedImageName: String) {
    enum class Direction {
        VERTICAL, HORIZONTAL, NONE
    }

    fun processAndSaveImage(scaleFactor: Float, rotateType: Direction) {
        val bitmap = BitmapFactory.decodeFile(mFilePath)
        saveBitmapToDisk(resizeAndRotate(scaleFactor, bitmap, rotateType))
    }

    private fun saveBitmapToDisk(bitmap: Bitmap) {
        val outFile = File(mDestinationDirectory, mFinishedImageName)

        val fos: FileOutputStream

        try {
            fos = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: FileNotFoundException) {
            e.message
        }

    }

    private fun resizeAndRotate(scaleFactor: Float, src: Bitmap, rotateType: Direction): Bitmap {
        val matrix = rotateLogic(scaleFactor, rotateType)
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
    }

    private fun rotateLogic(scaleFactor: Float, rotateType: Direction): Matrix {
        val matrix = Matrix()

        // perform error checking here for scale factor

        if (rotateType == Direction.VERTICAL) {
            matrix.preScale(scaleFactor, -1f * scaleFactor)
        } else if (rotateType == Direction.HORIZONTAL) {
            matrix.preScale(-1f * scaleFactor, scaleFactor)
        } else if (rotateType == Direction.NONE) {
            matrix.preScale(scaleFactor, scaleFactor)
        } else {
            imageProcessorError()
        }

        return matrix

    }

    private fun imageProcessorError() {
        Log.d("ERROR", "There was an error in your request")
    }
}

