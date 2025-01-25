package org.propapel.prospeccion.domain

interface Downloader {
    fun downloadFile(url: String): Long
}