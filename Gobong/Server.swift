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
    
    //GET FEED
    func getRecipeFeed(completion: @escaping(Result<FeedResponse, Error>) -> Void ){
        let urlString = "\(url)/api/feed/all"
        let parameters: [String: Any] = ["count": 10]
        let accessToken = UserDefaults.standard.string(forKey: "accessToken")
        
        let headers: HTTPHeaders = [
            "Authorization": "Bearer \(accessToken ?? "")"
        ]
        
        AF.request(urlString, method: .get, parameters: parameters, headers: headers).responseDecodable(of: FeedResponse.self) { response in
            switch response.result {
            case .success(let feedResponse):
                completion(.success(feedResponse))
            case .failure(let error):
                completion(.failure(error))
            }
        }
        
    }
    
    //FOLLOWER AND FOLLOWING
    func getFollowers(completion: @escaping (Result<[UserProfile], Error>) -> Void) {
        let urlString = "\(url)/api/follower"
        
        AF.request(urlString, method: .get).responseDecodable(of: UserResponse.self) { response in
            switch response.result {
            case .success(let userProfileArray):
                completion(.success(userProfileArray.UserProfiles))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    func getFollowing(completion: @escaping (Result<[UserProfile], Error>) -> Void) {
        let urlString = "\(url)/api/following"
        
        AF.request(urlString, method: .get).responseDecodable(of: UserResponse.self) { response in
            switch response.result {
            case .success(let userProfileArray):
                completion(.success(userProfileArray.UserProfiles))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    
    //GET USER INFORMATION
    func getUserInfo(completion: @escaping (Result<UserInfoResponse, Error>) -> Void) {
        let urlString = "\(url)/api/users"
        let accessToken = UserDefaults.standard.string(forKey: "accessToken")
        
        let headers: HTTPHeaders = [
            "Authorization": "Bearer \(accessToken ?? "")"
        ]
        
        AF.request(urlString, method: .get, headers: headers).responseDecodable(of: UserInfoResponse.self) { response in
            switch response.result {
            case .success(let userResponse):
                completion(.success(userResponse))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    //USER INFO EDIT
    func changeUserInfo(nickName: String, profileURL: String, completion: @escaping (Result<Void, Error>) -> Void) {
        let urlString = "\(url)/api/users"
        let accessToken = UserDefaults.standard.string(forKey: "accessToken")
        
        let headers: HTTPHeaders = [
            "Authorization": "Bearer \(accessToken ?? "")"
        ]
        
        let requestBody = UpdateUserInfoRequestBody(nickname: nickName, profileImageURL: profileURL)
        
        AF.request(urlString, method: .patch, parameters: requestBody, encoder: JSONParameterEncoder.default, headers: headers).response { response in
            switch response.result {
            case .success:
                completion(.success(()))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }

    
    //UPLOAD IMAGE
    func uploadImage(image: UIImage, nickname: String, completion: @escaping (Result<String, Error>) -> Void) {
        let urlString = "\(url)/api/images/upload"
        
        let imageData = image.jpegData(compressionQuality: 0.1) // Convert UIImage to Data
        
        AF.upload(
            multipartFormData: { multipartFormData in
                multipartFormData.append(imageData!, withName: "image", fileName: "image.jpg", mimeType: "image/jpeg")
                multipartFormData.append(Data(nickname.utf8), withName: "nickname")
            },
            to: urlString,
            method: .post
        )
        .responseDecodable(of: ImageURLResponse.self) { response in
            switch response.result {
            case .success(let data):
                completion(.success(data.imageUrl))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    
    //DOWNLOAD IMAGE
    func downloadImage(url: URL, completion: @escaping (Result<UIImage, Error>) -> Void) {
        AF.request(url).responseImage { response in
            switch response.result {
            case .success(let image):
                completion(.success(image))
            case .failure(let error):
                completion(.failure(error))
            }
        }
    }
    

}
