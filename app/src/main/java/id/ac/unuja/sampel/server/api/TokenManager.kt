package id.ac.unuja.sampel.server.api

import android.content.Context
import androidx.core.content.edit

object TokenManager {
    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_TOKEN = "jwt_token"

    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(context: Context, token: String) {
        getPrefs(context).edit {
            putString(KEY_TOKEN, token)
        }
    }

    fun getToken(context: Context): String? {
        return getPrefs(context).getString(KEY_TOKEN, null)
    }

    fun clearToken(context: Context) {
        getPrefs(context).edit {
            remove(KEY_TOKEN)
        }
    }
}
