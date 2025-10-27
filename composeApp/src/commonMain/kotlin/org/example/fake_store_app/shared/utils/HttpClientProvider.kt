package org.example.fake_store_app.shared.utils

import io.ktor.client.*


expect object HttpClientProvider {
    val client: HttpClient
}
