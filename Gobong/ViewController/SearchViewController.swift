//
//  SearchViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/02.
//

import UIKit
import RxCocoa
import RxSwift

class SearchViewController: UIViewController {
    
    //property
    private let searchBar = UISearchBar()
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var collectionView: UICollectionView!
    
    var selectedIndexPath = 0
    
    var dummyData = [
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5)
    ]
    
    var filteredData = [
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5),
        dummyFeedData(username: "찝찝박사", following: true, thumbnailImg: "dummyImg", title: "맛있는 라면", bookmarkCount: 2, cookingTime: 3, tools: "냄비", level: "쉬워요", stars: 5)
    ]
    var isSearching = false
    
    private var ShowingBlockView = true
    private var isShowingBlockView = PublishSubject<Bool>()
    private var isShowingBlockViewObservable : Observable<Bool> {
        return isShowingBlockView.asObservable()
    }
    
    private let disposeBag = DisposeBag()
    
    override func viewWillAppear(_ animated: Bool) {
        isShowingBlockView.onNext(true)
        tabBarController?.tabBar.isHidden = false
    }

    override func viewDidLoad() {
        
        // Do any additional setup after loading the view.
        super.viewDidLoad()
        setupUI()
        setObservable()
        setupSearchBar()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetailView",
                let detailVC = segue.destination as? DetailViewController {
            detailVC.information = dummyData[selectedIndexPath]
        }
    }
    
}

//MARK: BUTTON FUNC
extension SearchViewController {
    @objc private func filterButtonTapped(){
        performSegue(withIdentifier: "showFilterView", sender: self)
    }
    
    @objc private func toogleButtonTapped(){
        isShowingBlockView.onNext(!ShowingBlockView)
    
    }
}

//MARK: OBSERVABLE
extension SearchViewController {
    private func setObservable(){
        isShowingBlockView.subscribe(onNext: { [weak self] bool in
            guard let self = self else { return }
            if bool {
                ShowingBlockView = true
                self.collectionView.isHidden = false
                self.tableView.isHidden = true
                navigationItem.rightBarButtonItem = nil
                
                //NAVIGATION BAR
                let filterButton = UIBarButtonItem(image: UIImage(named: "Filter"), style: .plain, target: self, action: #selector(filterButtonTapped))
                filterButton.tintColor = .black
                
        //        filterButton.imageInsets = UIEdgeInsets(top: 0.0, left: 50, bottom: 0, right: 30);

                let tableViewToogleButton = UIBarButtonItem(image: UIImage(named: "액자형"), style: .plain, target: self, action: #selector(toogleButtonTapped))
                tableViewToogleButton.tintColor = .black
                
                navigationItem.rightBarButtonItems = [filterButton]
                navigationItem.leftBarButtonItem = tableViewToogleButton
            } else {
                ShowingBlockView = false
                self.collectionView.isHidden = true
                self.tableView.isHidden = false
                
                //NAVIGATION BAR
                let filterButton = UIBarButtonItem(image: UIImage(named: "Filter"), style: .plain, target: self, action: #selector(filterButtonTapped))
                filterButton.tintColor = .black
                
        //        filterButton.imageInsets = UIEdgeInsets(top: 0.0, left: 50, bottom: 0, right: 30);

                let tableViewToogleButton = UIBarButtonItem(image: UIImage(named: "카드형"), style: .plain, target: self, action: #selector(toogleButtonTapped))
                tableViewToogleButton.tintColor = .black
                
                navigationItem.rightBarButtonItems = [filterButton]
                navigationItem.leftBarButtonItem = tableViewToogleButton
            }
            
        }).disposed(by: disposeBag)
    }
}

//MARK: UI
extension SearchViewController {
    private func setupUI(){
        searchBar.tintColor = .black
        
        setupNavigationBar()
        tableViewSetup()
        collectionViewSetup()
        setupSearchBar()
    }
    
    private func setupNavigationBar(){
        navigationItem.titleView = searchBar
        
        let filterButton = UIBarButtonItem(image: UIImage(named: "Filter"), style: .plain, target: self, action: #selector(filterButtonTapped))
        filterButton.tintColor = .black
        
//        filterButton.imageInsets = UIEdgeInsets(top: 0.0, left: 50, bottom: 0, right: 30);

        let tableViewToogleButton = UIBarButtonItem(image: UIImage(named: "카드형"), style: .plain, target: self, action: #selector(toogleButtonTapped))
        tableViewToogleButton.tintColor = .black
        
        navigationItem.rightBarButtonItems = [filterButton]
        navigationItem.leftBarButtonItem = tableViewToogleButton
    }
}

//MARK: SEARCH BAR
extension SearchViewController: UISearchBarDelegate {
    private func setupSearchBar(){
        searchBar.placeholder = "검색어를 입력하세요"
        searchBar.delegate = self
    }
    
    func searchBarTextDidBeginEditing(_ searchBar: UISearchBar) {
        collectionView.isHidden = true
        tableView.isHidden = true
        
        navigationItem.leftBarButtonItem = nil
        let cancelButton = UIBarButtonItem(title: "취소", style: .plain, target: self, action: #selector(doneSearching))
        cancelButton.tintColor = .black
        
        let filterButton = UIBarButtonItem(image: UIImage(named: "Filter"), style: .plain, target: self, action: #selector(filterButtonTapped))
        filterButton.tintColor = .black
        
        navigationItem.rightBarButtonItems = [cancelButton, filterButton]
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        //search for the data
        
        //show it with block view first..
        isShowingBlockView.onNext(true)
    }
    
    
    
    @objc private func doneSearching(){
        isSearching = false
    }
}

//MARK: COLLECTION VIEW
extension SearchViewController : UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
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
        
        if !isSearching {
            cell.img.image = UIImage(named: dummyData[indexPath.item].thumbnailImg)
        } else {
            cell.img.image = UIImage(named: filteredData[indexPath.item].thumbnailImg)
        }
        
        NSLayoutConstraint.activate([
            cell.img.widthAnchor.constraint(equalToConstant: view.frame.width/3-2),
            cell.img.heightAnchor.constraint(equalToConstant: view.frame.width/3-2)
        ])
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        selectedIndexPath = indexPath.item
        self.performSegue(withIdentifier: "showDetailView", sender: self)
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
extension SearchViewController : UITableViewDelegate, UITableViewDataSource {
    private func tableViewSetup(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.separatorStyle = .none
        tableView.showsVerticalScrollIndicator = false
        tableView.register(UINib(nibName: "FeedCell", bundle: nil), forCellReuseIdentifier: "FeedCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dummyData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "FeedCell", for: indexPath) as! FeedCell
        
        if !isSearching {
            let data = dummyData[indexPath.item]
            cell.configuration(userImg: data.userImg, username: data.username, following: data.following, thumbnailImg: data.thumbnailImg, title: data.title, bookmarkCount: data.bookmarkCount, cookingTime: data.cookingTime, tools: data.tools, level: data.level, stars: data.stars)
            cell.followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
        } else {
            let data = filteredData[indexPath.item]
            cell.configuration(userImg: data.userImg, username: data.username, following: data.following, thumbnailImg: data.thumbnailImg, title: data.title, bookmarkCount: data.bookmarkCount, cookingTime: data.cookingTime, tools: data.tools, level: data.level, stars: data.stars)
            cell.followingButton.titleLabel?.font = UIFont.systemFont(ofSize: 12)
        }
        
        cell.selectionStyle = .none
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        selectedIndexPath = indexPath.item
        self.performSegue(withIdentifier: "showDetailView", sender: self)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 314.0
    }
    
}
