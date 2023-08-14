package com.youcancook.gobong.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityLoginBinding
import com.youcancook.gobong.databinding.ActivityRegisterBinding
import com.youcancook.gobong.ui.RoutingActivity
import com.youcancook.gobong.ui.base.NetworkActivity
import com.youcancook.gobong.ui.base.NetworkStateListener
import com.youcancook.gobong.util.ACCESS_TOKEN_KEY
import com.youcancook.gobong.util.REFRESH_TOKEN_KEY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterActivity :
    NetworkActivity<ActivityRegisterBinding, UserViewModel>(R.layout.activity_register) {

    override val viewModel: UserViewModel by lazy {
        UserViewModel(appContainer.userRepository)
    }

    override val onNetworkStateChange: NetworkStateListener = object : NetworkStateListener {
        override fun onSuccess() {
            val token = viewModel.getToken()
            saveToken(token.accessToken, token.refreshToken)
            moveToRoutingActivity()
        }

        override fun onFail() {
        }

        override fun onDone() {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        val auth = intent?.getSerializableExtra(LoginActivity.LOGIN_AUTH) as LoginAuth
        viewModel.setLoginAuth(auth)

        binding.run {
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
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        sharedPref.edit {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken)
            apply()
        }
    }

    private fun moveToRoutingActivity() {
        val intent = Intent(this, RoutingActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        startActivity(intent)
    }
}