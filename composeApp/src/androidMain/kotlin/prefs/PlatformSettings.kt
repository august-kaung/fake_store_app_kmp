package prefs

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

fun provideSettings(context: Context): Settings {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(prefs)
}
