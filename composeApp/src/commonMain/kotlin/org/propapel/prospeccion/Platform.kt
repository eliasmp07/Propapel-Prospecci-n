package org.propapel.prospeccion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform