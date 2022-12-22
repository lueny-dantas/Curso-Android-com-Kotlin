package paixao.lueny.curso_android_kotlin.activity


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityLoginBinding
import paixao.lueny.curso_android_kotlin.extensions.goTo
import paixao.lueny.curso_android_kotlin.extensions.toast
import paixao.lueny.curso_android_kotlin.preferences.dataStore
import paixao.lueny.curso_android_kotlin.preferences.userLoggedPreferences

class ActivityLogin : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val userDao by lazy { AppDatabase.instance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButtonRegister()
        configureButtonLogin()
    }

    private fun configureButtonRegister() {
        binding.activityLoginButtonRegister.setOnClickListener {
            goTo(ActivityUserRegistration::class.java)
        }
    }
    private fun configureButtonLogin() {
        binding.activityLoginButton.setOnClickListener {
            lifecycleScope.launch {
                val userId = binding.activityLoginUser.text.toString()
                val password = binding.activityLoginPassword.text.toString()
                authenticate(userId, password)
                }
            }
        }

    private fun authenticate(userId: String, password: String) {
        lifecycleScope.launch {
            userDao.authenticate(userId, password)?.let { userId ->
                dataStore.edit { preferences ->
                    preferences[userLoggedPreferences] = userId.id
                }
                goTo(ActivityProductsList::class.java)
                finish()
            } ?: toast("Falha na Autenticação")
        }
    }
}
