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

    @IBOutlet weak var mainTableView: UITableView!

    var dummyData = [
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),

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
        
        setupMainTableView()
        setupUserInfo()
//        setObservable()
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
//    private func setObservable(){
//        isShowingBlockView.subscribe(onNext: { [weak self] bool in
//            guard let self = self else { return }
//            if bool {
//                ShowingBlockView = true
//                self.collectionView.isHidden = false
//                self.tableView.isHidden = true
//            } else {
//                ShowingBlockView = false
//                self.collectionView.isHidden = true
//                self.tableView.isHidden = false
//            }
//
//        }).disposed(by: disposeBag)
//    }
}

//MARK: UI
extension ProfileViewController: UISearchBarDelegate {
    private func setupUserInfo(){
    }

    private func setupUI(){
        setupNavigationBar()
//        tableViewSetup()
//        collectionViewSetup()
    }

    private func setupNavigationBar(){
        let tableViewToogleButton = UIBarButtonItem(image: UIImage(systemName: "square.stack.3d.up.fill"), style: .plain, target: self, action: #selector(toogleButtonTapped))
        tableViewToogleButton.tintColor = .black
        let settingsButton = UIBarButtonItem(image: UIImage(systemName: "gearshape.fill"), style: .plain, target: self, action: #selector(settingsButtonTapped))
        settingsButton.tintColor = .black

        navigationItem.rightBarButtonItem = tableViewToogleButton
        navigationItem.leftBarButtonItem = settingsButton
        
        navigationItem.title = "유저 이름"
    }
}

extension ProfileViewController: UITableViewDelegate, UITableViewDataSource{
    private func setupMainTableView(){
        mainTableView.delegate = self
        mainTableView.dataSource = self
        mainTableView.separatorStyle = .none
        mainTableView.register(UINib(nibName: "UserInformationCell", bundle: nil), forCellReuseIdentifier: "UserInformationCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
//        if indexPath.item == 0 {
            let cell = mainTableView.dequeueReusableCell(withIdentifier: "UserInformationCell") as! UserInformationCell
            cell.configuration(img: nil, recipeCount: 0, followerCount: 0, followingCount: 0)
        cell.selectionStyle = .none
            return cell
//        }
    }
}



//extension ProfileViewController: UIController {
//
//}


//
////MARK: COLLECTION VIEW
//extension ProfileViewController : UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
//    private func collectionViewSetup(){
//        collectionView.delegate = self
//        collectionView.dataSource = self
//        collectionView.showsVerticalScrollIndicator = false
//        collectionView.register(UINib(nibName: "FeedBoxCell", bundle: nil), forCellWithReuseIdentifier: "FeedBoxCell")
//    }
//
//    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
//        return dummyData.count
//    }
//
//    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
//        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "FeedBoxCell", for: indexPath) as! FeedBoxCell
//
//        cell.img.image = UIImage(named: dummyData[indexPath.item].thumbnailImg)
//
//        NSLayoutConstraint.activate([
//            cell.img.widthAnchor.constraint(equalToConstant: view.frame.width/3-2),
//            cell.img.heightAnchor.constraint(equalToConstant: view.frame.width/3-2)
//        ])
//
//        return cell
//    }
//
//    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
//        // Set the cell height to match the cellWidth so that the cell appears as a square
//        return CGSize(width: (view.frame.width/3)-2, height: view.frame.width/3-2)
//    }
//
//    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
//        return UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 1)
//    }
//
//    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
//        return 3
//    }
//
//    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
//        return 0
//    }
//
//}
//
////MARK: TABLEVIEW
//extension ProfileViewController : UITableViewDelegate, UITableViewDataSource {
//    private func tableViewSetup(){
//        tableView.dataSource = self
//        tableView.delegate = self
//        tableView.separatorStyle = .none
//        tableView.showsVerticalScrollIndicator = false
//        tableView.register(UINib(nibName: "FeedCell", bundle: nil), forCellReuseIdentifier: "FeedCell")
//    }
//
//    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
//        return dummyData.count
//    }
//
//    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
//        let cell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as! FeedCell
//
//        let data = dummyData[indexPath.item]
//        cell.configuration(userImg: data.userImg, username: data.username, following: data.following, thumbnailImg: data.thumbnailImg, title: data.title, bookmarkCount: data.bookmarkCount, cookingTime: data.cookingTime, tools: data.tools, level: data.level, stars: data.stars)
//        cell.followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
//
//        cell.selectionStyle = .none
//        return cell
//    }
//
//    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
//        return 314.0
//    }
//
//    func scrollViewDidScroll(_ scrollView: UIScrollView) {
//            // This method will be called when either the table view or the parent scroll view is scrolled
//            if scrollView == tableView {
//                // If the table view is scrolled, synchronize its content offset with the parent scroll view
//                scrollView.contentOffset.y = max(tableView.contentOffset.y, 0)
//            } else {
//                // If the parent scroll view is scrolled, apply the parallax effect to the header view
//                let offset = scrollView.contentOffset.y
//                if offset < 0 {
//                    // The header view is enlarged when scrolling down (pull to refresh)
//                    informationView.transform = CGAffineTransform(translationX: 0, y: offset)
//                } else {
//                    // Apply the parallax effect to the header view as the table view is scrolled up
//                    informationView.transform = CGAffineTransform(translationX: 0, y: -offset)
//                }
//            }
//        }
//}
//
