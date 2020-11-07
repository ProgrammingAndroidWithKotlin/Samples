package com.peterlaurence.book.javatokotlin.flows.sharedflow.stateflow

import java.io.File

sealed class DownloadEvent
data class DownloadProgress(val progress: Int): DownloadEvent()
data class DownloadComplete(val file: File): DownloadEvent()
object DownloadError: DownloadEvent()