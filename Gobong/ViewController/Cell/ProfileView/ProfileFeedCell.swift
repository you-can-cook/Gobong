//
//  ProfileFeedCell.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/06.
//

import UIKit

class ProfileFeedCell: UITableViewCell {
    
    var dummyData: [dummyFeedData] = []
    
    let collectionView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        let collectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        
        return collectionView
    }()
    
    let tableView: UITableView = {
        let tableview = UITableView()
        return tableview
    }()
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        addCollectionView()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func addCollectionView(){
        contentView.addSubview(collectionView)
        NSLayoutConstraint.activate([
            collectionView.topAnchor.constraint(equalTo: contentView.topAnchor),
            collectionView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor),
            collectionView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor),
            collectionView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor)
        ])
        
        collectionViewSetup()
    }
    
    private func addTableView(){
        contentView.addSubview(tableView)
        NSLayoutConstraint.activate([
            tableView.topAnchor.constraint(equalTo: contentView.topAnchor),
            tableView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor),
            tableView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor),
            tableView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor)
        ])
        
        tableViewSetup()
    }
            
    func removeAllSubviews() {
        contentView.subviews.forEach { $0.removeFromSuperview() }
    }
    
    func configuration(isShowingBlock: Bool){
        if isShowingBlock{
            removeAllSubviews()
            addCollectionView()
        } else {
            removeAllSubviews()
            addTableView()
        }
    }
}

//MARK: COLLECTION VIEW
extension ProfileFeedCell : UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    private func collectionViewSetup(){
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.showsVerticalScrollIndicator = false
        collectionView.register(UINib(nibName: "FeedBoxCell", bundle: nil), forCellWithReuseIdentifier: "FeedBoxCell")
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return dummyData.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "FeedBoxCell", for: indexPath) as! FeedBoxCell

        cell.img.image = UIImage(named: dummyData[indexPath.item].thumbnailImg)

        NSLayoutConstraint.activate([
            cell.img.widthAnchor.constraint(equalToConstant: contentView.frame.width/3-2),
            cell.img.heightAnchor.constraint(equalToConstant: contentView.frame.width/3-2)
        ])

        return cell
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        // Set the cell height to match the cellWidth so that the cell appears as a square
        return CGSize(width: (contentView.frame.width/3)-2, height: contentView.frame.width/3-2)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 1)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 3
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 0
    }

}

//MARK: TABLEVIEW
extension ProfileFeedCell : UITableViewDelegate, UITableViewDataSource {
    private func tableViewSetup(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.separatorStyle = .none
        tableView.showsVerticalScrollIndicator = false
        tableView.translatesAutoresizingMaskIntoConstraints = false
        tableView.register(UINib(nibName: "FeedCell", bundle: nil), forCellReuseIdentifier: "FeedCell")
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dummyData.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as! FeedCell

        let data = dummyData[indexPath.item]
        cell.configuration(userImg: data.userImg, username: data.username, following: data.following, thumbnailImg: data.thumbnailImg, title: data.title, bookmarkCount: data.bookmarkCount, cookingTime: data.cookingTime, tools: data.tools, level: data.level, stars: data.stars)
        cell.followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)

        cell.selectionStyle = .none
        return cell
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 314.0
    }

}

