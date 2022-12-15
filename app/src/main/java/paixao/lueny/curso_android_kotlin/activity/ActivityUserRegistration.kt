package paixao.lueny.curso_android_kotlin.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityUserRegistrationBinding
import paixao.lueny.curso_android_kotlin.model.User
import java.lang.Exception

class ActivityUserRegistration: AppCompatActivity() {
    private val binding by lazy {ActivityUserRegistrationBinding.inflate(layoutInflater) }
    private val dao by lazy { AppDatabase.instance(this).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButtonRegister()
    }

    private fun configureButtonRegister() {
        binding.activityFormRegistrationButtonRegister.setOnClickListener {
            val newUser = createUser()
            Log.i("CadastroUsuario", "onCreate: $newUser")
            lifecycleScope.launch {
                try {
                    dao.save(newUser)
                    finish()
                }catch (e:Exception){
                    Log.e("CadastroUsuario", "ConfigureButtonRegister:", e)
                    Toast.makeText(
                        this@ActivityUserRegistration,
                        "Falha ao cadastrar usuário",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun createUser(): User {
        val user = binding.activityFormUserRegistration.text.toString()
        val name = binding.activityFormNameRegistration.text. toString()
        val password = binding.activityFormPasswordRegistration.text.toString()
        return User(user, name, password)
    }
}