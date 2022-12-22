package paixao.lueny.curso_android_kotlin.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.extensions.goTo
import paixao.lueny.curso_android_kotlin.model.User
import paixao.lueny.curso_android_kotlin.preferences.dataStore
import paixao.lueny.curso_android_kotlin.preferences.userLoggedPreferences


abstract class ActivityBase : AppCompatActivity() {

    private val userDao by lazy { AppDatabase.instance(this).userDao() }
    private var _user: MutableStateFlow<User?> = MutableStateFlow(null)
    protected var user: StateFlow<User?> = _user


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            checkLoggedUser()
        }
    }

    private suspend fun checkLoggedUser() {
        dataStore.data.collect { preferences ->
            preferences[userLoggedPreferences]?.let { userId ->
                searchUser(userId)
            } ?: goToLogin()
        }
    }

    private suspend fun searchUser(userId: String) {
        _user.value = userDao
            .searchById(userId)
            .firstOrNull()
    }

    suspend fun logOffUser() {
        dataStore.edit { preferences ->
            preferences.remove(userLoggedPreferences)
        }
    }

    private fun goToLogin() {
        goTo(ActivityLogin::class.java){
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }
}