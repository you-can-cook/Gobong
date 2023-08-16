//
//  ViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/01.
//

import UIKit
import NVActivityIndicatorView

struct dummyFeedData {
    var userImg: String?
    var username: String
    var following: Bool
    var thumbnailImg: String
    var title: String
    var bookmarkCount: Int
    var isBookmarked: Bool
    var cookingTime: Int
    var tools: String
    var level: String
    var stars: Double
}

class ViewController: UIViewController, UITabBarControllerDelegate {

    var userDefault = UserDefaults.standard
    
    @IBOutlet weak var emptyStateView: UIView!
    @IBOutlet var tableView: UITableView!
    @IBOutlet var addFeedButton: UIButton!
    var selectedIndexPath = 0
    var activityIndicator: NVActivityIndicatorView?
    
    var FeedData: [FeedInfo] = []
    
    override func viewWillAppear(_ animated: Bool) {
        setupUser()
        setupNavigationBar()
        tabBarController?.tabBar.isHidden = false
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        setupUI()
        setupData()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetailView",
                let detailVC = segue.destination as? DetailViewController {
//            detailVC.information = FeedData[selectedIndexPath]
        }
    }
    
    //when home icon in toolbar is clicked
//    func refresh() {
//        setupUI()
//    }
}

extension ViewController {
    // MARK: DATA
    private func setupUser() {
        //!!!!!임시!!!!
        if userDefault.string(forKey: "accessToken") == nil {
            performSegue(withIdentifier: "showLoginView", sender: self)
        }
    }
    
    private func setupData(){
        activityIndicator?.startAnimating()
        
        Server.shared.getRecipeFeed { Result in
            switch Result {
            case .success(let FeedResponse):
                print(Result)
                self.FeedData = FeedResponse.feed
                self.tableView.reloadData()
            case .failure(let Error):
                print(Error)
            }
            
        }
        
        if FeedData.isEmpty {
            emptyStateView.isHidden = false
        } else {
            emptyStateView.isHidden = true
        }
    }
    
    //MARK: UI
    private func setupUI(){
        setupTableView()
        addFeedButton.setTitle("", for: .normal)
        setupNavigationBar()
        
        activityIndicator = ActivityIndicator.shared.setupActivityIndicator(in: view)
    }
    
    private func setupNavigationBar(){
        navigationController?.navigationBar.isHidden = true
    }
}

//MARK: FEED (CARD 형식)
extension ViewController : UITableViewDelegate, UITableViewDataSource, FeedCellDelegate {
    func profileTapped(cell: FeedCell) {
        guard let indexPath = tableView.indexPath(for: cell) else { return }
        //get other's profileID then show the profile
        
    
    }
    
    private func setupTableView(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.separatorStyle = .none
        tableView.register(UINib(nibName: "FeedCell", bundle: nil), forCellReuseIdentifier: "FeedCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return FeedData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as! FeedCell
        
        let data = FeedData[indexPath.item]
        cell.configuration(userImg: data.userImg, username: data.username, following: data.following, thumbnailImg: data.thumbnailImg, title: data.title, bookmarkCount: data.bookmarkCount, isBookmarked: data.isBookmarked, cookingTime: data.cookingTime, tools: data.tools, level: data.level, stars: data.stars)
        cell.delegate = self
        
        cell.selectionStyle = .none
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        var selectedIndexPath = indexPath.item
        self.performSegue(withIdentifier: "showDetailView", sender: self)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 380.0
    }
    
}
