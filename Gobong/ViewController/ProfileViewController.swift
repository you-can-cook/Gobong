//
//  ProfileViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/04.
//

import UIKit
import RxSwift
import RxCocoa

class ProfileViewController: UIViewController {

    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var followingLabel: UILabel!
    @IBOutlet weak var followerLabel: UILabel!
    @IBOutlet weak var totalRecipeLabel: UILabel!
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var profilePictureImg: UIImageView!
    
    var dummyData = [
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5)
    ]
    
    private var ShowingBlockView = true
    private var isShowingBlockView = PublishSubject<Bool>()
    private var isShowingBlockViewObservable : Observable<Bool> {
        return isShowingBlockView.asObservable()
    }
    
    private let disposeBag = DisposeBag()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
    }

}

//MARK: BUTTON FUNC
extension ProfileViewController {
    @objc private func settingsButtonTapped(){
        
    }
    
    @objc private func toogleButtonTapped(){
        isShowingBlockView.onNext(!ShowingBlockView)
    
    }
}

//MARK: OBSERVABLE
extension ProfileViewController {
    private func setObservable(){
        isShowingBlockView.subscribe(onNext: { [weak self] bool in
            guard let self = self else { return }
            if bool {
                ShowingBlockView = true
                self.collectionView.isHidden = false
                self.tableView.isHidden = true
            } else {
                ShowingBlockView = false
                self.collectionView.isHidden = true
                self.tableView.isHidden = false
            }
            
        }).disposed(by: disposeBag)
    }
}

//MARK: UI
extension ProfileViewController: UISearchBarDelegate {
    private func setupUI(){
        setupNavigationBar()
        tableViewSetup()
        collectionViewSetup()
    }
    
    private func setupNavigationBar(){
        let tableViewToogleButton = UIBarButtonItem(image: UIImage(systemName: "square.stack.3d.up.fill"), style: .plain, target: self, action: #selector(toogleButtonTapped))
        tableViewToogleButton.tintColor = .black
        let settingsButton = UIBarButtonItem(image: UIImage(systemName: "gearshape.fill"), style: .plain, target: self, action: #selector(settingsButtonTapped))
        settingsButton.tintColor = .black

        navigationItem.leftBarButtonItem = tableViewToogleButton
        navigationItem.rightBarButtonItem = settingsButton
    }
}


//MARK: COLLECTION VIEW
extension ProfileViewController : UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    private func collectionViewSetup(){
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.register(UINib(nibName: "FeedBoxCell", bundle: nil), forCellWithReuseIdentifier: "FeedBoxCell")
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return dummyData.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "FeedBoxCell", for: indexPath) as! FeedBoxCell
        
        cell.img.image = UIImage(named: dummyData[indexPath.item].thumbnailImg)
        
        NSLayoutConstraint.activate([
            cell.img.widthAnchor.constraint(equalToConstant: view.frame.width/3-2),
            cell.img.heightAnchor.constraint(equalToConstant: view.frame.width/3-2)
        ])
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        // Set the cell height to match the cellWidth so that the cell appears as a square
        return CGSize(width: (view.frame.width/3)-2, height: view.frame.width/3-2)
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
extension ProfileViewController : UITableViewDelegate, UITableViewDataSource {
    private func tableViewSetup(){
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
        cell.followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
        
        cell.selectionStyle = .none
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 314.0
    }
    
}

