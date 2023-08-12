//
//  FollowViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/12.
//

import UIKit

struct dummyProfileData {
    var name: String
    var following: Bool
}

class FollowViewController: UIViewController, FollowDelegate {
    func followingTapped(cell: FollowStateCell) {
        guard let indexPath = tableView.indexPath(for: cell) else { return }
        
        if followStateTapped == "followers" {
            followerData[indexPath.item].following.toggle()
            tableView.reloadRows(at: [indexPath], with: .none)
        } else if followStateTapped == "following" {
            followingData[indexPath.item].following.toggle()
            tableView.reloadRows(at: [indexPath], with: .none)
        }
    }
    

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var followingButton: UIButton!
    @IBOutlet weak var followerButton: UIButton!
    
    var followingData = [
        dummyProfileData(name: "배고파용", following: true),
        dummyProfileData(name: "닉넴", following: true)
    ]
    
    var followerData = [
        dummyProfileData(name: "asdas", following: false),
        dummyProfileData(name: "닉넴", following: true)
    ]
    
    var followStateTapped = ""
    
    override func viewWillAppear(_ animated: Bool) {
        tabBarController?.tabBar.isHidden = true
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        followStateUI()
        tableViewSetup()
    }
    
    private func followStateUI(){
        if followStateTapped == "followers" {
            //get data if nil
            
            
            followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12, weight: .semibold)
            followingButton.titleLabel?.textColor = UIColor(named: "softGray")
            let bottomBorder = CALayer()
            bottomBorder.backgroundColor = UIColor(named: "softGray")?.cgColor// Set the border color
            bottomBorder.frame = CGRect(x: 0, y: followingButton.frame.height - 1, width: followingButton.frame.width, height: 1)
            followingButton.layer.addSublayer(bottomBorder)
            
            followerButton.titleLabel?.font = UIFont.systemFont(ofSize: 12, weight: .semibold)
            followerButton.titleLabel?.textColor = .black
            let bottomBorder2 = CALayer()
            bottomBorder2.backgroundColor = UIColor(named: "pink")?.cgColor// Set the border color
            bottomBorder2.frame = CGRect(x: 0, y: followerButton.frame.height - 1, width: followerButton.frame.width, height: 1)
            followerButton.layer.addSublayer(bottomBorder2)
        } else {
            //get data if nil
            
            
            followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12, weight: .semibold)
            followingButton.titleLabel?.textColor = .black
            let bottomBorder = CALayer()
            bottomBorder.backgroundColor = UIColor(named: "pink")?.cgColor// Set the border color
            bottomBorder.frame = CGRect(x: 0, y: followingButton.frame.height - 1, width: followingButton.frame.width, height: 1)
            followingButton.layer.addSublayer(bottomBorder)
            
            followerButton.titleLabel?.font = UIFont.systemFont(ofSize: 12, weight: .semibold)
            followerButton.titleLabel?.textColor = UIColor(named: "softGray")
            let bottomBorder2 = CALayer()
            bottomBorder2.backgroundColor = UIColor(named: "softGray")?.cgColor// Set the border color
            bottomBorder2.frame = CGRect(x: 0, y: followerButton.frame.height - 1, width: followerButton.frame.width, height: 1)
            followerButton.layer.addSublayer(bottomBorder2)
        }
    }

    @IBAction func followerTapped(_ sender: Any) {
        followStateTapped = "followers"
        followStateUI()
        tableView.reloadData()
    }
    
    @IBAction func followingTapped(_ sender: Any) {
        followStateTapped = "followings"
        followStateUI()
        tableView.reloadData()
    }
}

extension FollowViewController: UITableViewDelegate, UITableViewDataSource {
    private func tableViewSetup(){
        tableView.delegate = self
        tableView.dataSource = self
        tableView.register(UINib(nibName: "FollowStateCell", bundle: nil), forCellReuseIdentifier: "FollowStateCell")
        tableView.separatorStyle = .none
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if followStateTapped == "followers" {
            return followerData.count
        } else {
            return followingData.count
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FollowStateCell") as! FollowStateCell
        
        //followers
        if followStateTapped == "followers" {
            cell.delegate = self
            cell.selectionStyle = .none
            
            let data = followerData[indexPath.item]
            cell.configuration(img: nil, name: data.name, following: data.following)
        }
        
        //following
        else {
            cell.delegate = self
            cell.selectionStyle = .none
            
            let data = followingData[indexPath.item]
            cell.configuration(img: nil, name: data.name, following: data.following)
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 72
    }
    
}
