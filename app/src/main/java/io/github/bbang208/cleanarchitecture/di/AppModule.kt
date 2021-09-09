package io.github.bbang208.cleanarchitecture.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppSharedPreference

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ConfigSharedPreference

    @AppSharedPreference
    @Provides
    @Singleton
    fun provideAppPreference(@ApplicationContext context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "app_pref",
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @ConfigSharedPreference
    @Provides
    @Singleton
    fun provideConfigPreference(@ApplicationContext context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "config_pref",
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}