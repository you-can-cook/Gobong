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
    var time: Int
    var tool: String
    var img: String?
    var description: String
}

class DetailViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    var information: dummyFeedData!
    var hashTag = ["면", "면 면", "면 면", "면", "모차렐라(슈레드) 치즈", "자이언트 떡볶이", "자이언트 떡볶이", "자이언트 떡볶이", "면 면"]
    var recipeInformation = [
        dummyHowTo(time: 3, tool: "전자레인지", img: nil, description: "자이언트 떡볶이를 순서대로 3분 조리"),
        dummyHowTo(time: 3, tool: "전자레인지", img:"dummyImg", description: "자이언트 떡볶이를 순서대로 3분 조리"),
        dummyHowTo(time: 3, tool: "전자레인지", img:"dummyImg" ,description: "자이언트 떡볶이를 순서대로 3분 조리"),
        dummyHowTo(time: 3, tool: "전자레인지", img:"dummyImg" ,description: "자이언트 떡볶이를 순서대로 3분 조리"),
        dummyHowTo(time: 3, tool: "전자레인지", img:"dummyImg" ,description: "자이 언트 떡 볶이를 순 서대로  3분 조리자이 언트 떡볶 이를 순서대로 3분 조리자이언트 떡볶이를 순서대로 3분 조리자이언트 떡볶이를 순서대로 3분 조리")
    ]
    
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
        tableView.showsVerticalScrollIndicator = false
        tableView.register(UINib(nibName: "DetailTitleCell", bundle: nil), forCellReuseIdentifier: "DetailTitleCell")
        tableView.register(HashtagCell.self, forCellReuseIdentifier: "HashtagCell")
        tableView.register(RecipeCell.self, forCellReuseIdentifier: "RecipeCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return recipeInformation.count + 2
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.item == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "DetailTitleCell", for: indexPath) as! DetailTitleCell
            cell.configuration(userImg: information.userImg, username: information.username, following: information.following, thumbnailImg: information.thumbnailImg, title: information.title, bookmarkCount: information.bookmarkCount, cookingTime: information.cookingTime, tools: information.tools, level: information.level, stars: information.stars)
            cell.selectionStyle = .none
           
            cell.backgroundColor = .brown
            return cell
        }
        
        else if indexPath.item == 1 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "HashtagCell", for: indexPath) as! HashtagCell
            cell.titleLabel.text = "여기는.. 간단한 소개 "
            cell.setupCollectionView(dataSource: self, delegate: self)
            cell.selectionStyle = .none
            
            return cell
        }
        
        else{
            let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeCell", for: indexPath) as! RecipeCell
            let data = recipeInformation[indexPath.item-2]
            cell.configuration(step: indexPath.item-1, time: data.time, tool: data.tool, image: data.img, description: data.description)
            cell.selectionStyle = .none
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.item == 0 {
            return 344
        }
        
        else if indexPath.item == 1{
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
            
        } else {
            let data = recipeInformation[indexPath.item-2]
            let firstline = 16
            var imageHeight: CGFloat = 0
            let last = calculateLabelSizeRecipe(text: data.description).height
            if data.img != nil {
                if let image = UIImage(named: data.img!) {
                    let maxWidth: CGFloat = CGFloat(view.bounds.width/1.5)
                    let maxHeight: CGFloat = 130
                    let aspectRatio: CGFloat = 16 / 9  // 1.91:1

                    let imageWidth = image.size.width
                    imageHeight = min(maxHeight, min(imageWidth * aspectRatio, maxWidth))
                }
                return CGFloat(firstline) + imageHeight + last + 87
            }
            
            return CGFloat(firstline) + imageHeight + last + 57
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
    
    func calculateLabelSizeRecipe(text: String) -> CGSize {
        let label = UILabel()
        label.numberOfLines = 0
        label.text = text
        label.font = UIFont.systemFont(ofSize: 14)
        
        let options: NSStringDrawingOptions = [.usesLineFragmentOrigin, .usesFontLeading]
        let attributes = [NSAttributedString.Key.font: label.font!]
        let size = CGSize(width: CGFloat(view.bounds.width/1.5), height: .greatestFiniteMagnitude)
        
        let boundingRect = (text as NSString).boundingRect(
            with: size,
            options: options,
            attributes: attributes,
            context: nil
        )
        
        return boundingRect.size
    }


}
