//
//  FeedCell.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/01.
//

import UIKit

class FeedCell: UITableViewCell {
    
    @IBOutlet weak var starStack: UIStackView!
    @IBOutlet weak var levelStack: UIStackView!
    @IBOutlet weak var toolStack: UIStackView!
    @IBOutlet weak var timeStack: UIStackView!
    @IBOutlet weak var background: UIView!
    @IBOutlet weak var followingButton: UIButton!
    @IBOutlet weak var starLabel: UILabel!
    @IBOutlet weak var levelLabel: UILabel!
    @IBOutlet weak var cookingToolsLabel: UILabel!
    @IBOutlet weak var cookTimeLabel: UILabel!
    @IBOutlet weak var bookmarkCountLabel: UILabel!
    @IBOutlet weak var bookmarkImage: UIImageView!
    @IBOutlet weak var postTitle: UILabel!
    @IBOutlet weak var thumbnailImage: UIImageView!
    @IBOutlet weak var userProfile: UIImageView!
    @IBOutlet weak var userName: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        followingButton.layer.cornerRadius = 2
        followingButton.titleLabel?.adjustsFontSizeToFitWidth = true
        followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
        setBackgroundShadow()
    }
    
    func setBackgroundShadow(){
        background.layer.cornerRadius = 12
        background.layer.shadowColor = UIColor(red: 0.721, green: 0.721, blue: 0.721, alpha: 0.25).cgColor
        background.layer.shadowOpacity = 1
        background.layer.shadowRadius = 10
        background.layer.shadowOffset = CGSize(width: 0, height: 0)
    }

    func configuration(userImg: String? , username: String, following: Bool, thumbnailImg: String, title: String, bookmarkCount: Int, cookingTime: Int, tools: String, level: String, stars: Int) {
        userProfile.image = UIImage(named: userImg ?? "프로필 이미지")
        userName.text = username
        followingButton.setTitle(following ? "팔로잉" : "팔로우", for: .normal)
        thumbnailImage.image = UIImage(named: thumbnailImg)
        postTitle.text = title
        bookmarkCountLabel.text = "\(bookmarkCount)"
        cookTimeLabel.text = "\(cookingTime)분"
        cookingToolsLabel.text = tools
        levelLabel.text = level
        starLabel.text = "\(stars)공기"
        
        
    }
}
