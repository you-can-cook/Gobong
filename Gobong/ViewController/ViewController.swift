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
    var cookingTime: Int
    var tools: String
    var level: String
    var stars: Int
}

class ViewController: UIViewController, UITabBarControllerDelegate {

    var userDefault = UserDefaults.standard
    
    @IBOutlet weak var emptyStateView: UIView!
    @IBOutlet var tableView: UITableView!
    @IBOutlet var addFeedButton: UIButton!
    var selectedIndexPath = 0
    var activityIndicator: NVActivityIndicatorView?
    
    var dummyData: [dummyFeedData] = [
//        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
//        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
//        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
//        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5)
    ]
    
    override func viewWillAppear(_ animated: Bool) {
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
            detailVC.information = dummyData[selectedIndexPath]
        }
    }
    
    //when heme icon in toolbar is clicked
//    func refresh() {
//        setupUI()
//    }
}

extension ViewController {
    // MARK: DATA
    private func setupUser() {
        //!!!!!임시!!!!
        userDefault.set("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInN1YiI6IkFDQ0VTUyIsImlzcyI6ImdvYm9uZy55b3VjYW5jb29rLm9yZyIsImlhdCI6MTY5MTQ3Nzk5OSwiZXhwIjoyNDExNDc3OTk5fQ.m_uF4Tpu9dp2UZWVeGLNj39TYArHtmFidSv4SWfw-Sc", forKey: "accessKey")
        
        if userDefault.string(forKey: "accessKey") == nil {
            performSegue(withIdentifier: "showLoginView", sender: self)
        }
    }
    
    private func setupData(){
//        activityIndicator?.startAnimating()
        
        if dummyData.isEmpty {
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
extension ViewController : UITableViewDelegate, UITableViewDataSource {
    private func setupTableView(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.separatorStyle = .none
        tableView.register(UINib(nibName: "FeedCell", bundle: nil), forCellReuseIdentifier: "FeedCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dummyData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as! FeedCell
        
        let data = dummyData[indexPath.item]
        cell.configuration(userImg: data.userImg, username: data.username, following: data.following, thumbnailImg: data.thumbnailImg, title: data.title, bookmarkCount: data.bookmarkCount, cookingTime: data.cookingTime, tools: data.tools, level: data.level, stars: data.stars)
        
        cell.selectionStyle = .none
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        var selectedIndexPath = indexPath.item
        self.performSegue(withIdentifier: "showDetailView", sender: self)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 314.0
    }
    
}
