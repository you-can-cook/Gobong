package com.youcancook.gobong.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
//            googleLoginButton.icon =
//                AppCompatResources.getDrawable(this@LoginActivity, R.drawable.logo_google)
//            kakaoLoginButton.icon =
//                AppCompatResources.getDrawable(this@LoginActivity, R.drawable.logo_kakao)
        }
    }
}