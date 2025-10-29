package prefs

import com.russhwolf.settings.Settings

object Prefs {
    private lateinit var settings: Settings

    fun init(settings: Settings) {
        this.settings = settings
    }

    fun putString(key: String, value: String) = settings.putString(key, value)
    fun getString(key: String, default: String = "") = settings.getString(key, default)
    fun putInt(key: String, value: Int) = settings.putInt(key, value)
    fun getInt(key: String, default: Int = 0) = settings.getInt(key, default)
    fun putBoolean(key: String, value: Boolean) = settings.putBoolean(key, value)
    fun getBoolean(key: String, default: Boolean = false) = settings.getBoolean(key, default)
}
