//
//  APIModel.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/13.
//

import Foundation

//TEMPORARY TOKEN
struct TemporaryTokenResponse: Decodable {
    let temporaryToken: String
}

//SIGN IN && SIGN UP
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

//GET USER INFO
struct UserInfoResponse: Decodable {
    let id: Int
    let nickname: String
    let profileImageURL: String?
    let oAuthProvider: String
}

//UPLOAD USER INFO
struct UpdateUserInfoRequestBody: Encodable {
    let nickname: String
    let profileImageURL: String
}

//IMAGE
struct ImageURLResponse: Encodable, Decodable{
    let imageUrl: String
}

//FEED
struct AuthorFeed: Decodable {
    let id: Int
    let nickname: String
    let profileImageURL: String?
    let following: Bool
    let myself: Bool
}

struct FeedInfo: Decodable {
    let id: Int
    let title: String
    let thumbnailURL: String?
    let author: AuthorFeed
    let totalBookmarkCount: Int
    let totalCookTimeInSeconds: Int
    let cookwares: [String]
    let difficulty: String
    let averageRating: Double?
    let bookmarked: Bool
}

struct FeedResponse: Decodable {
    let feed: [FeedInfo]
    let hasNext: Bool
}

//FOLLOWER AND FOLLOWERS
struct UserResponse: Encodable, Decodable{
    let UserProfiles: [UserProfile]
}

struct UserProfile: Encodable, Decodable {
    let profileImageURL: String
    let nickname: String
    let userId: Int
}


