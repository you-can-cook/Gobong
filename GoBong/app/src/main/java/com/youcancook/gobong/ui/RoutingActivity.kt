package com.youcancook.gobong.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.youcancook.gobong.R
import com.youcancook.gobong.ui.login.LoginActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
val ACCESS_TOKEN = stringPreferencesKey("access_token")

class RoutingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routing)


    }

    override fun onStart() {
        super.onStart()

        //TODO 로그인 확인
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun isTokenExpired() {
        val accessTokenFlow: Flow<String> = dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[ACCESS_TOKEN] ?: ""
            }
    }
}