package com.peterlaurence.book.javatokotlin.flows.callbackFlow

import java.io.OutputStream


class ZipTask(
    private val listener: ZipProgressionListener,
    private val content: Content,
    private val outputStream: OutputStream
) : Thread() {
    private var isCancelled = false

    override fun start() {
        super.start()

        try {
            for (i in 1..100) {
                if (isCancelled) break
                sleep(10)   // simulate zipping content to an OutputStream
                listener.onZipProgress(i)
            }
            listener.onZipFinished()
        } catch (e: Exception) {
            listener.onZipError(e)
        }
    }

    fun cancel() {
        isCancelled = true
    }

    interface ZipProgressionListener {
        fun onZipProgress(p: Int)
        fun onZipFinished()
        fun onZipError(e: Exception)
    }
}
