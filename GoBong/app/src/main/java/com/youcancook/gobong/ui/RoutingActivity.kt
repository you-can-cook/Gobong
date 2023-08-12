package com.youcancook.gobong.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youcancook.gobong.MainActivity
import com.youcancook.gobong.R
import com.youcancook.gobong.ui.login.LoginActivity
import com.youcancook.gobong.util.ACCESS_TOKEN

class RoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routing)
    }

    override fun onStart() {
        super.onStart()

        //TODO 로그인 확인
        if (isTokenExpired()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    private fun isTokenExpired(): Boolean {
        getSharedPreferences(
            ACCESS_TOKEN, Context.MODE_PRIVATE
        ) ?: return false
        return true
    }
}