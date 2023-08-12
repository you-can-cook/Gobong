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

class LoginViewController: UIViewController {
    
    @IBOutlet weak var googleLoginView: UIView!
    @IBOutlet weak var kakaoLoginView: UIView!
    
    var activityIndicatorView: NVActivityIndicatorView!
    
    var tempToken: String?
    var oauthId: String?
    var provider: String?
    
    var userDefault = UserDefaults.standard
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        tabBarController?.tabBar.isHidden = true
        setupUI()
        kakaoLoginView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(kakaoLogin)))
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showSignUpView",
           let vc = segue.destination as? SignUpViewController {
            vc.oauthId = oauthId
            vc.temporaryToken = tempToken
            vc.provider = provider
        }
    }
    
    private func setupUI(){
        googleLoginView.layer.cornerRadius = 24
        kakaoLoginView.layer.cornerRadius = 24
        setupActivityIndicator()
    }
    
    private func setupActivityIndicator(){
        let frame = CGRect(x: (view.frame.width - 100) / 2, y: (view.frame.height - 100) / 2, width: 100, height: 100)
        let type = NVActivityIndicatorType.ballScaleRippleMultiple
        let color = UIColor(named: "pink")
        let padding: CGFloat = 0
        
        // Create an instance of NVActivityIndicatorView
        activityIndicatorView = NVActivityIndicatorView(frame: frame, type: type, color: color, padding: padding)
        
        // Add it as a subview to your main view
        view.addSubview(activityIndicatorView)
        
    }
    
    @objc private func kakaoLogin() {
        print("loginKakao() called.")
        
        // 카카오톡 설치 여부 확인
        activityIndicatorView.startAnimating()
        if (UserApi.isKakaoTalkLoginAvailable()) {
            UserApi.shared.loginWithKakaoTalk {(oauthToken, error) in
                if let error = error {
                    print(error)
                }
                else {
                    print("loginWithKakaoTalk() success.")
                    
                    // ✅ 회원가입 성공 시 oauthToken 저장가능하다
                    // _ = oauthToken
                    self.oauthId = oauthToken?.accessToken ?? ""
                    self.provider = "KAKAO"
                    self.kakaoLoginSuccess()
                    
                }
            }
        }
        // ✅ 카카오톡 미설치
        else {
            // ✅ 기본 웹 브라우저를 사용하여 로그인 진행.
            UserApi.shared.loginWithKakaoAccount {(oauthToken, error) in
                if let error = error {
                    print(error)
                }
                else {
                    print("loginWithKakaoAccount() success.")
                    
                    // ✅ 회원가입 성공 시 oauthToken 저장
                    // _ = oauthToken
                    
                    self.oauthId = oauthToken?.accessToken ?? ""
                    self.provider = "KAKAO"
                    self.kakaoLoginSuccess()
                
                }
            }
        }
        activityIndicatorView.stopAnimating()
    }
    
    private func kakaoLoginSuccess(){
        //임시토큰 발급
        Server.shared.fetchTemporaryToken { result in
            switch result {
            case .success(let token):
                print("Temporary Token:", token)
                self.tempToken = token

                //로그인 요청
                Server.shared.login(provider: "KAKAO", oauthId: self.oauthId ?? "", temporaryToken: self.tempToken ?? "") { result in
                    switch result {
                    case .success(let loginInfo):
                        print("Temporary Token:", token)
                        //로그인 성공~ 환영합니다아아아아
                        self.userDefault.set(loginInfo.grantType, forKey: "grantType")
                        self.userDefault.set(loginInfo.accessToken, forKey: "accessToken")
                        self.userDefault.set(loginInfo.refreshToken, forKey: "refreshToken")
                        
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
