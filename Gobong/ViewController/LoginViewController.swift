//
//  LoginViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/12.
//

import UIKit
import KakaoSDKAuth
import KakaoSDKCommon
import KakaoSDKUser
import NVActivityIndicatorView
import GoogleSignIn

class LoginViewController: UIViewController {
    
    @IBOutlet weak var googleLoginView: UIView!
    @IBOutlet weak var kakaoLoginView: UIView!
    
    var activityIndicatorView: NVActivityIndicatorView!
    
    var tempToken: String?
    var oauthId: String?
    var provider: String?
    
    var userDefault = UserDefaults.standard
    
    //MARK: LIFE CYCLE
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        tabBarController?.tabBar.isHidden = true
        setupUI()
        kakaoLoginView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(kakaoLogin)))
        googleLoginView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(googleLogin)))
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showSignUpView",
           let vc = segue.destination as? SignUpViewController {
            vc.oauthId = oauthId
            vc.temporaryToken = tempToken
            vc.provider = provider
        }
    }
    
    //MARK: UI
    private func setupUI(){
        googleLoginView.layer.cornerRadius = 24
        kakaoLoginView.layer.cornerRadius = 24
        activityIndicatorView = ActivityIndicator.shared.setupActivityIndicator(in: view)
    }

    //MARK: GOOGLE LOGIN
    @objc private func googleLogin(){
        GIDSignIn.sharedInstance.signIn(withPresenting: self) { signInResult, error in
            guard error == nil else { return }
            
            // If sign in succeeded, display the app's main content View.
            self.oauthId = signInResult?.user.accessToken.tokenString
            self.provider = "GOOGLE"
            self.LoginSuccess()
            
        }
    }
    
    //MARK: KAKAO LOGIN
    @objc private func kakaoLogin() {
        // 카카오톡 설치 여부 확인
        activityIndicatorView.startAnimating()
        if (UserApi.isKakaoTalkLoginAvailable()) {
            UserApi.shared.loginWithKakaoTalk {(oauthToken, error) in
                if let error = error {
                    print(error)
                }
                else {
                    // 회원가입 성공 시 oauthToken 저장가능하다
                    self.oauthId = oauthToken?.accessToken ?? ""
                    self.provider = "KAKAO"
                    self.LoginSuccess()
                    
                }
            }
        }
        // 카카오톡 미설치
        else {
            // 기본 웹 브라우저를 사용하여 로그인 진행.
            UserApi.shared.loginWithKakaoAccount {(oauthToken, error) in
                if let error = error {
                    print(error)
                }
                else {
                    self.oauthId = oauthToken?.accessToken ?? ""
                    self.provider = "KAKAO"
                    self.LoginSuccess()
                    
                }
            }
        }
        activityIndicatorView.stopAnimating()
    }
    
    //MARK: AUTH SUCCES WITH KAKAO / GOOGLE
    private func LoginSuccess(){
        //임시토큰 발급
        Server.shared.fetchTemporaryToken { result in
            switch result {
            case .success(let token):
                print("Temporary Token:", token)
                self.tempToken = token
                
                //로그인 요청
                Server.shared.login(provider: self.provider ?? "",
                                    oauthId: self.oauthId ?? "",
                                    temporaryToken: self.tempToken ?? ""
                ) { result in
                    switch result {
                    case .success(let loginInfo):
                        print("Temporary Token:", token)
                        //로그인 성공~ 환영합니다아아아아
                        self.userDefault.set(loginInfo.grantType, forKey: "grantType")
                        self.userDefault.set(loginInfo.accessToken, forKey: "accessToken")
                        self.userDefault.set(loginInfo.refreshToken, forKey: "refreshToken")
                        
                        
                    //회원가입이 필요하면 ... 
                    case .failure(let error):
                        print("Error:", error.localizedDescription)
                        //회원 가입 필요하다~~~
                        self.performSegue(withIdentifier: "showSignUpView", sender: self)
                        
                    }
                }
                
            case .failure(let error):
                print("Error:", error.localizedDescription)
                // 발급 실패..? 뭐해야 돼??
                
                
            }
        }
    }
    
    
}
