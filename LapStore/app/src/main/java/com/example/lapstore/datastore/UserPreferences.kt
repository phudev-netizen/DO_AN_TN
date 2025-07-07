package com.example.lapstore.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val KEY_TAI_KHOAN = stringPreferencesKey("tai_khoan")
        val KEY_ROLE = stringPreferencesKey("role")
    }

    suspend fun saveLogin(taiKhoan: String, role: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TAI_KHOAN] = taiKhoan
            prefs[KEY_ROLE] = role
        }
    }

    suspend fun clearLogin() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun getLoginInfo(): Pair<String?, String?> {
        val prefs = context.dataStore.data.first()
        return Pair(
            prefs[KEY_TAI_KHOAN],
            prefs[KEY_ROLE]
        )
    }

suspend fun setLoggedOut(loggedOut: Boolean) {
    context.dataStore.edit { prefs ->
        prefs[booleanPreferencesKey("logged_out")] = loggedOut
    }
}

    suspend fun isLoggedOut(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[booleanPreferencesKey("logged_out")] ?: false
    }


}
