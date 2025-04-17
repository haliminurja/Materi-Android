package id.ac.unuja.sampel

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        internal const val PREF_NAME = "UserSession"
        internal const val KEY_IS_LOGGED_IN = "isLoggedIn"
        internal const val KEY_USERNAME = "nama_pengguna"
        internal const val KEY_PASSWORD = "kata_kunci"
    }

    fun saveLogin(username: String, password: String) {
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getData(): HashMap<String, String> {
        val user = HashMap<String, String>()
        user[KEY_USERNAME] = preferences.getString(KEY_USERNAME, "") ?: ""
        user[KEY_PASSWORD] = preferences.getString(KEY_PASSWORD, "") ?: ""
        return user
    }

    fun isLoggedIn(): Boolean = preferences.getBoolean(KEY_IS_LOGGED_IN, false)

    fun logout() {
        editor.clear()
        editor.apply()
    }
}