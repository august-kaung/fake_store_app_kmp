package org.example.fake_store_app
import prefs.Prefs


import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults
import androidx.compose.ui.window.ComposeUIViewController
import org.example.fake_store_app.shared.local_db_helper.initializeDatabase
fun MainViewController() = ComposeUIViewController {
    val database = initializeDatabase()
    val settings = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    Prefs.init(settings)
    App(database)
}