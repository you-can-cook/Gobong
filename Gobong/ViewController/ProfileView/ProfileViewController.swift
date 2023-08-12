//
//  ProfileViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/04.
//

import UIKit
import RxSwift
import RxCocoa

class ProfileViewController: UIViewController, profileFeedDelegete {
    func cellTapped(cell: ProfileFeedCell) {
        selectedIndexPath = cell.selectedIndexPath
        self.performSegue(withIdentifier: "showDetailView", sender: self)
    }
    
    @IBOutlet weak var mainTableView: UITableView!
    var selectedIndexPath = 0 

    var dummyData: [dummyFeedData] = [
//        dummyFeedData(username: "1", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
//        dummyFeedData(username: "2", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
//        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
//        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),

    ]

    var followStateTapped = ""
    private var ShowingBlockView = true
    private var isShowingBlockView = PublishSubject<Bool>()
    private var isShowingBlockViewObservable : Observable<Bool> {
        return isShowingBlockView.asObservable()
    }

    private let disposeBag = DisposeBag()

    override func viewWillAppear(_ animated: Bool) {
        tabBarController?.tabBar.isHidden = false
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        setupUI()
        
        setupMainTableView()
        setObservable()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetailView",
           let detailVC = segue.destination as? DetailViewController {
            detailVC.information = dummyData[selectedIndexPath]
        }
        
        if segue.identifier == "showFollowView" {
            if let vc = segue.destination as? FollowViewController {
                vc.followStateTapped = followStateTapped
            }
        }
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
                mainTableView.reloadRows(at: [IndexPath(row: 1, section: 0)], with: .automatic)
                
                //NAVIGATION BAR
                let tableViewToogleButton = UIBarButtonItem(image: UIImage(named: "액자형"), style: .plain, target: self, action: #selector(toogleButtonTapped))
                tableViewToogleButton.tintColor = .black
                
                navigationItem.rightBarButtonItem = tableViewToogleButton
            } else {
                ShowingBlockView = false
                mainTableView.reloadRows(at: [IndexPath(row: 1, section: 0)], with: .automatic)
                
                //NAVIGATION BAR
                let tableViewToogleButton = UIBarButtonItem(image: UIImage(named: "카드형"), style: .plain, target: self, action: #selector(toogleButtonTapped))
                tableViewToogleButton.tintColor = .black
                
                navigationItem.rightBarButtonItem = tableViewToogleButton
            }

        }).disposed(by: disposeBag)
    }
}

//MARK: DELEGATE
extension ProfileViewController: UserInformationDelegate{
    func followingTapped(controller: UserInformationCell) {
        followStateTapped = "followings"
        performSegue(withIdentifier: "showFollowView", sender: self)
    }
    
    func followersTapped(controller: UserInformationCell) {
        followStateTapped = "followers"
        performSegue(withIdentifier: "showFollowView", sender: self)
    }
}

//MARK: UI
extension ProfileViewController: UISearchBarDelegate{
    private func setupUI(){
        setupNavigationBar()
    }

    private func setupNavigationBar(){
        let tableViewToogleButton = UIBarButtonItem(image: UIImage(named: "액자형"), style: .plain, target: self, action: #selector(toogleButtonTapped))
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
        mainTableView.showsVerticalScrollIndicator = false
        mainTableView.register(UINib(nibName: "UserInformationCell", bundle: nil), forCellReuseIdentifier: "UserInformationCell")
        mainTableView.register(ProfileFeedCell.self, forCellReuseIdentifier: "ProfileFeedCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.item == 0 {
            let cell = mainTableView.dequeueReusableCell(withIdentifier: "UserInformationCell") as! UserInformationCell
            cell.configuration(img: nil, recipeCount: 0, followerCount: 0, followingCount: 0)
            cell.selectionStyle = .none
            cell.delegate = self
            
            return cell
        } else {
            //if Empty
            if dummyData.isEmpty {
                let cell = mainTableView.dequeueReusableCell(withIdentifier: "ProfileFeedCell") as! ProfileFeedCell
                cell.configEmpty()
                cell.delegate = self
                cell.selectionStyle = .none
                
                return cell
                
            } else {
                if ShowingBlockView {
                    let cell = mainTableView.dequeueReusableCell(withIdentifier: "ProfileFeedCell") as! ProfileFeedCell
                    cell.dummyData = dummyData
                    cell.configuration(isShowingBlock: ShowingBlockView)
                    cell.collectionView.reloadData()
                    cell.delegate = self
                    
                    return cell
                } else {
                    let cell = mainTableView.dequeueReusableCell(withIdentifier: "ProfileFeedCell") as! ProfileFeedCell
                    cell.dummyData = dummyData
                    cell.configuration(isShowingBlock: ShowingBlockView)
                    cell.tableView.reloadData()
                    cell.delegate = self
                    
                    return cell
                }
            }
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.item == 0 {
            return 180
        } else {
            //if empty
            if dummyData.isEmpty {
                return view.bounds.height - 500
            } else {
                if ShowingBlockView {
                    return CGFloat(dummyData.count) * tableView.bounds.width / 3 - 2
                } else {
                    // Calculate the height based on the dummy data count and cell height
                    let cellHeight: CGFloat = 314 // Adjust the expected cell height
                    return CGFloat(dummyData.count) * cellHeight
                }
            }
        }
    }

}


