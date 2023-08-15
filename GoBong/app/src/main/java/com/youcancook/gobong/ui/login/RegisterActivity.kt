package com.youcancook.gobong.ui.login

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityRegisterBinding
import com.youcancook.gobong.model.LoginUser
import com.youcancook.gobong.ui.ImageActivity
import com.youcancook.gobong.ui.MainActivity
import com.youcancook.gobong.ui.RoutingActivity
import com.youcancook.gobong.ui.base.NetworkActivity
import com.youcancook.gobong.ui.base.NetworkStateListener
import com.youcancook.gobong.util.ACCESS_TOKEN_KEY
import com.youcancook.gobong.util.REFRESH_TOKEN_KEY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterActivity :
    NetworkActivity<ActivityRegisterBinding, RegisterUserViewModel>(R.layout.activity_register) {

    override val viewModel: RegisterUserViewModel by lazy {
        RegisterUserViewModel(appContainer.userRepository)
    }

    override val onNetworkStateChange: NetworkStateListener = object : NetworkStateListener {
        override fun onSuccess() {
            val token = viewModel.getToken()
            saveToken(token.accessToken, token.refreshToken)
        }

        override fun onFail() {
        }

        override fun onDone() {
        }

    }
    private var imagePickActivityLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        val auth = intent?.getSerializableExtra(LoginActivity.LOGIN_AUTH) as LoginUser
        viewModel.setLoginUser(auth)

        imagePickActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageData = result.data?.getByteArrayExtra(ImageActivity.IMAGE_DATA_TAG)
                        ?: return@registerForActivityResult
                    viewModel.setProfileImageByteArray(imageData)
                }
            }

        binding.run {
            profileImageView.setOnClickListener {
                Intent(this@RegisterActivity, ImageActivity::class.java).apply {
                    putExtra(ImageActivity.PHOTO_SIZE, ImageActivity.PROFILE)
                }.run {
                    imagePickActivityLauncher?.launch(this)
                }
            }

            registerButton.setOnClickListener {
                viewModel.registerNickname()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorMessage.collectLatest {
                    if (it.isNotEmpty()) {
                        binding.nicknameTextLayout.error = it
                    }
                }
            }
        }
    }

    private fun saveToken(accessToken: String, refreshToken: String) {
        getPreferences(Context.MODE_PRIVATE).edit {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken)
            commit()

            moveToMainActivity()
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        startActivity(intent)
    }
}