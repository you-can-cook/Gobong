package com.youcancook.gobong.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.youcancook.gobong.R
import com.youcancook.gobong.ui.login.LoginActivity

class RoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routing)

        //TODO 로그인 확인

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}