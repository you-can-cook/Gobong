//
//  APIModel.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/13.
//

import Foundation

struct TemporaryTokenResponse: Decodable {
    let temporaryToken: String
}

struct LoginRequest: Encodable {
    let provider: String
    let oauthId: String
    let temporaryToken: String
}

struct LoginResponse: Codable {
    let grantType: String
    let accessToken: String
    let refreshToken: String
}

struct SignUpRequest: Encodable {
    let nickname: String
    let provider: String
    let oauthId: String
    let temporaryToken: String
    let profileImageUrl: String?
}