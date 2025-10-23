package org.example.fake_store_app


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform