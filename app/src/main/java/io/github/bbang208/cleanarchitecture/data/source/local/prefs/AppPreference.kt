package io.github.bbang208.cleanarchitecture.data.source.local.prefs

import android.content.SharedPreferences
import io.github.bbang208.cleanarchitecture.di.AppModule
import javax.inject.Inject

class AppPreference @Inject constructor(
    @AppModule.AppSharedPreference private val appPreference: SharedPreferences,
    @AppModule.ConfigSharedPreference private val configPreference: SharedPreferences
) {

    fun getBoolValue(): Boolean {
        return appPreference.getBoolean("key_boolean", false)
    }

    fun getConfigValue(): Boolean {
        return configPreference.getBoolean("config_boolean", true)
    }
}