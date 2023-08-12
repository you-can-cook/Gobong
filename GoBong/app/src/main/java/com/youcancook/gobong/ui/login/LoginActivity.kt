package com.youcancook.gobong.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.youcancook.gobong.R
import com.youcancook.gobong.databinding.ActivityLoginBinding
import com.youcancook.gobong.ui.base.NetworkActivity
import com.youcancook.gobong.util.ACCESS_TOKEN
import com.youcancook.gobong.util.NetworkState
import com.youcancook.gobong.util.REFRESH_TOKEN

class LoginActivity :
    NetworkActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override val viewModel: LoginViewModel by lazy {
        LoginViewModel(appContainer.userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        initListeners()

        viewModel.makeTemporaryToken()
    }

    private fun initListeners() {
        binding.run {

            googleLoginButton.setOnClickListener {
                loginWithGoogle()
            }

            kakaoLoginButton.setOnClickListener {
                loginWithKakao()
            }
        }
    }

    private fun loginWithGoogle() {

    }

    private fun loginWithKakao() {
        viewModel.setNetworkState(NetworkState.LOADING)
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
                viewModel.setNetworkState(NetworkState.FAIL)
            } else if (token != null) {
                Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                viewModel.setNetworkState(NetworkState.SUCCESS)
                moveToRegister(KAKAO, token)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오톡으로 로그인 실패", error)
                    viewModel.setNetworkState(NetworkState.FAIL)
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(
                        this@LoginActivity,
                        callback = callback
                    )
                } else if (token != null) {
                    Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                    //TODO TOKEN 저장
                    viewModel.setNetworkState(NetworkState.SUCCESS)
                    moveToRegister(KAKAO, token)
                }
                viewModel.setNetworkState(NetworkState.DONE)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                this@LoginActivity,
                callback = callback
            )
        }
    }

    private fun moveToRegister(provider: String, oAuth: OAuthToken) {
        println("auth $oAuth")
        val auth = LoginAuth(
            provider,
            oAuth.idToken ?: "",
            viewModel.getTemporaryToken()
        )
        val intent =
            Intent(this@LoginActivity, RegisterActivity::class.java)
                .apply {
                    putExtra(LOGIN_AUTH, auth)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
        startActivity(intent)
        finish()
    }

    companion object {
        const val LOGIN_AUTH = "loginAuth"
        const val KAKAO = "KAKAO"
        const val GOOGLE = "GOOGLE"
    }
}