//
//  DetailViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/04.
//

import UIKit
import RxSwift
import RxCocoa

struct dummyHowTo {
    
}

class DetailViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    var information: dummyFeedData!
    var hashTag = ["면", "면 면", "면 면", "면", "모차렐라(슈레드) 치즈", "자이언트 떡볶이", "자이언트 떡볶이", "자이언트 떡볶이", "면 면"]
    
    var collectionViewHeight = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
        tableViewSetup()
    }
}

extension DetailViewController {
    private func setupUI(){
        navigationBarSetup()
        navigationController?.navigationBar.isHidden = false
    }
    
    private func navigationBarSetup(){
        navigationItem.title = information.title
        
        let backItemButton = UIBarButtonItem(image: UIImage(systemName: "chevron.left"), style: .plain, target: self, action: #selector(backButtonTapped))
        backItemButton.tintColor = .black
        
        let saveItemButton = UIBarButtonItem(image: UIImage(named: "BMark"), style: .plain, target: self, action: #selector(backButtonTapped))
        saveItemButton.tintColor = .black
        
        navigationItem.leftBarButtonItem = backItemButton
        navigationItem.rightBarButtonItem = saveItemButton
    }
    
    @objc private func backButtonTapped(){
        navigationController?.popViewController(animated: true)
    }
    
    @objc private func saveButtonTapped(){
        //
    }
}

extension DetailViewController: UITableViewDelegate, UITableViewDataSource {
    func tableViewSetup(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.separatorStyle = .none
        tableView.register(UINib(nibName: "DetailTitleCell", bundle: nil), forCellReuseIdentifier: "DetailTitleCell")
        tableView.register(HashtagCell.self, forCellReuseIdentifier: "HashtagCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.item == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "DetailTitleCell", for: indexPath) as! DetailTitleCell
            cell.configuration(userImg: information.userImg, username: information.username, following: information.following, thumbnailImg: information.thumbnailImg, title: information.title, bookmarkCount: information.bookmarkCount, cookingTime: information.cookingTime, tools: information.tools, level: information.level, stars: information.stars)
           
            cell.backgroundColor = .brown
            return cell
        }
        
        else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "HashtagCell", for: indexPath) as! HashtagCell
            cell.titleLabel.text = "여기는.. 간단한 소개 "
            
            // Set up the collection view data source and delegate for the cell
            cell.setupCollectionView(dataSource: self, delegate: self)
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.item == 0 {
            return 344
        } else {
            var addLine = 0
            var currentWidth: CGFloat = 0.0
            let collectionViewWidth = tableView.bounds.width - CGFloat((hashTag.count * 10) + 32)
            for i in hashTag {
                currentWidth += CGFloat(calculateLabelSize(text: i).width)
                if currentWidth > collectionViewWidth {
                    addLine += 30
                    currentWidth = CGFloat(calculateLabelSize(text: i).width)
                }
            }
            return CGFloat(100 + addLine)

        }
    }
    
}

extension DetailViewController: UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return hashTag.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "HashtagCollectionCell", for: indexPath) as! HashtagCollectionCell
        cell.setText("\(hashTag[indexPath.item])")
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let labelSize = calculateLabelSize(text: hashTag[indexPath.item])
        return CGSize(width: labelSize.width + 24, height: labelSize.height + 16)
    }
    
    func calculateLabelSize(text: String) -> CGSize {
        let label = UILabel()
        label.numberOfLines = 0
        label.text = text
        label.font = UIFont.systemFont(ofSize: 10)
        label.sizeToFit()
        return label.frame.size
    }
}
