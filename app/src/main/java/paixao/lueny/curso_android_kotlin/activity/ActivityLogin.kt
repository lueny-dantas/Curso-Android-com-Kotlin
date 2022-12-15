package paixao.lueny.curso_android_kotlin.activity


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityLoginBinding
import paixao.lueny.curso_android_kotlin.extensions.goTo

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
                val user = userDao.authenticate(userId, password).firstOrNull()
                if (user != null)
                    goTo(ActivityProductsList::class.java) {
                        putExtra("KEY_USER_ID", user.id)
                    }
                else
                    Toast.makeText(
                        applicationContext, "Falha na Autenticação", Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }
}