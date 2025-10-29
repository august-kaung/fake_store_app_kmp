package org.example.fake_store_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.fake_store_app.shared.local_db_helper.DatabaseDriverFactory
import org.example.fake_store_app.shared.local_db_helper.DatabaseHelper
import prefs.Prefs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val factory = DatabaseDriverFactory(this)
        val dbHelper = DatabaseHelper(factory)
        val database = dbHelper.database
        val settings = com.russhwolf.settings.SharedPreferencesSettings(
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        )
        Prefs.init(settings)
        setContent {

            App(database)
        }
    }
}

