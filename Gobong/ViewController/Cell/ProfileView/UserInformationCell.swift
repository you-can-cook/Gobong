//
//  UserInformationCell.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/04.
//

import UIKit

class UserInformationCell: UITableViewCell {
    
    @IBOutlet weak var profileImg: UIImageView!
    @IBOutlet weak var recipeCountLabel: UILabel!
    @IBOutlet weak var followerCountLabel: UILabel!
    @IBOutlet weak var followingCountLabel: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func configuration(img: String?, recipeCount: Int, followerCount: Int, followingCount: Int){
        profileImg.image = UIImage(named: img ?? "프로필 이미지")
        recipeCountLabel.text = "\(recipeCount)"
        followerCountLabel.text = "\(followerCount)"
        followingCountLabel.text = "\(followingCount)"
    }

}
