//
//  Server.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/13.
//

import Foundation
import Alamofire

class Server {
    static let shared = Server()
    
    let url = "http://43.202.126.165:8080"
    
    func fetchTemporaryToken(completion: @escaping (Result<String, Error>) -> Void) {
        let urlString = "\(url)/api/users/temporary-token"
        
        AF.request(urlString, method: .post).responseDecodable(of: TemporaryTokenResponse.self) { response in
            switch response.result {
            case .success(let token):
                completion(.success(token.temporaryToken))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    func login(provider: String, oauthId: String, temporaryToken: String, completion: @escaping (Result<LoginResponse, Error>) -> Void) {
        let urlString = "\(url)/api/users/login"
        
        let request = LoginRequest(provider: provider, oauthId: oauthId, temporaryToken: temporaryToken)
        
        AF.request(urlString, method: .post, parameters: request, encoder: JSONParameterEncoder.default).responseDecodable(of: LoginResponse.self) { response in
            debugPrint(response)
            switch response.result {
            case .success(let loginResponse):
                completion(.success(loginResponse))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    func signUp(nickName: String, provider: String, oauthId: String, temporaryToken: String, profileImageUrl: String?, completion: @escaping (Result<LoginResponse, Error>) -> Void) {
        let urlString = "\(url)/api/users/signup"
        
        let request: SignUpRequest!
        if profileImageUrl != nil {
            request = SignUpRequest(nickname: nickName, provider: provider, oauthId: oauthId, temporaryToken: temporaryToken, profileImageUrl: profileImageUrl)
        } else {
            request = SignUpRequest(nickname: nickName, provider: provider, oauthId: oauthId, temporaryToken: temporaryToken, profileImageUrl: nil)
        }
        
        AF.request(urlString, method: .post, parameters: request, encoder: JSONParameterEncoder.default).responseDecodable(of: LoginResponse.self) { response in
            debugPrint(response)
            switch response.result {
            case .success(let loginResponse):
                completion(.success(loginResponse))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
}
