//
//  ViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/01.
//

import UIKit

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

    @IBOutlet var tableView: UITableView!
    @IBOutlet var addFeedButton: UIButton!
    var selectedIndexPath = 0
    
    var dummyData = [
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5)
    ]
    
    override func viewWillAppear(_ animated: Bool) {
        setupNavigationBar()
        tabBarController?.tabBar.isHidden = false
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        setupUI()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetailView",
                let detailVC = segue.destination as? DetailViewController {
            detailVC.information = dummyData[selectedIndexPath]
        }
    }
    
//    func refresh() {
//        setupUI()
//    }
}

extension ViewController {
    private func setupUI(){
        setupTableView()
        addFeedButton.setTitle("", for: .normal)
        setupNavigationBar()
    }
    
    private func setupNavigationBar(){
        navigationController?.navigationBar.isHidden = true
    }
}

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
