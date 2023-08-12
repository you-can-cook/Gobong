package com.youcancook.gobong.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityRoutingBinding
import com.youcancook.gobong.model.UserToken
import com.youcancook.gobong.ui.base.GoBongActivity
import com.youcancook.gobong.ui.base.NetworkActivity
import com.youcancook.gobong.ui.login.LoginActivity
import com.youcancook.gobong.ui.login.UserViewModel
import com.youcancook.gobong.util.ACCESS_TOKEN
import com.youcancook.gobong.util.REFRESH_TOKEN
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

class RoutingActivity :
    NetworkActivity<ActivityRoutingBinding, UserViewModel>(R.layout.activity_routing) {

    override val viewModel: UserViewModel by lazy {
        UserViewModel(appContainer.userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isTokenExist().not()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isTokenExpired.collectLatest {
                    if (it) {
                        //TODO 재로그인
                        val intent = Intent(this@RoutingActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@RoutingActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun isTokenExist(): Boolean {
        val token = getSharedPreferences(
            ACCESS_TOKEN, Context.MODE_PRIVATE
        ) ?: return false
        viewModel.isTokenExpired(
            UserToken(
                token.getString(ACCESS_TOKEN, "") ?: "",
                token.getString(REFRESH_TOKEN, "") ?: ""
            )
        )
        return true
    }
}
