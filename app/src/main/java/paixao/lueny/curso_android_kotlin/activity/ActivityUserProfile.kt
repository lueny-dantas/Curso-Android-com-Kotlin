package paixao.lueny.curso_android_kotlin.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.databinding.ActivityUserProfileBinding

class ActivityUserProfile : ActivityBase() {
    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureButtonExitApp()
        fillInFields()
    }

    private fun fillInFields() {
        lifecycleScope.launch {
            user
                .filterNotNull()
                .collect { loggedUser ->
                    binding.activityUserProfileId.text = loggedUser.id
                    binding.activitiyUserProfileName.text = loggedUser.name

                }
        }
    }

    private fun configureButtonExitApp() {
        binding.activityUserProfileExitApp.setOnClickListener {
            lifecycleScope.launch {
                logOffUser()
            }
        }
    }
}