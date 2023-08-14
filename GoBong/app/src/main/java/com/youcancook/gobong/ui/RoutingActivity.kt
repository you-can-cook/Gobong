package com.youcancook.gobong.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityRoutingBinding
import com.youcancook.gobong.ui.base.NetworkActivity
import com.youcancook.gobong.ui.base.NetworkStateListener
import com.youcancook.gobong.ui.login.LoginActivity
import com.youcancook.gobong.util.ACCESS_TOKEN
import com.youcancook.gobong.util.ACCESS_TOKEN_KEY
import com.youcancook.gobong.util.REFRESH_TOKEN
import com.youcancook.gobong.util.REFRESH_TOKEN_KEY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RoutingActivity :
    NetworkActivity<ActivityRoutingBinding, RoutingViewModel>(R.layout.activity_routing) {

    override val viewModel: RoutingViewModel by lazy {
        RoutingViewModel(appContainer.userRepository)
    }

    override val onNetworkStateChange: NetworkStateListener = object : NetworkStateListener {
        override fun onSuccess() {

        }

        override fun onFail() {
            //REFRESH TOKEN 만료
            val intent = Intent(this@RoutingActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        override fun onDone() {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isTokenExist().not()) {
            val intent = Intent(this@RoutingActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accessToken.collectLatest {
                    if (it.isNotEmpty()) {
                        //토큰 발급 성공
                        val intent = Intent(this@RoutingActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun isTokenExist(): Boolean {
        val token = getSharedPreferences(
            ACCESS_TOKEN_KEY, Context.MODE_PRIVATE
        ) ?: return false
        viewModel.getAccessToken(
            token.getString(REFRESH_TOKEN_KEY, "") ?: ""
        )

        ACCESS_TOKEN = token.getString(ACCESS_TOKEN_KEY, "") ?: ""
        REFRESH_TOKEN = token.getString(REFRESH_TOKEN_KEY, "") ?: ""
        return true
    }
}
