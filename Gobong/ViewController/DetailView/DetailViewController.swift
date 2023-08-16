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
    var minute: String
    var second: String
    var tool: [String]
    var img: UIImage?
    var description: String
}

//POST'S DETAILED INFORMATION
class DetailViewController: UIViewController, UIGestureRecognizerDelegate{
    
    @IBOutlet weak var tableView: UITableView!
    var labelSizeCache: [String: CGSize] = [:]
    
    var information: dummyFeedData!
    var hashTag = ["자이언트떡볶이 1개", "의성마늘후랑크 소시지 1개 ", "콕콕콕 스파게티 1개", "모짜렐라 피자치즈 20g"]
    var recipeInformation: [dummyHowTo] = [
        dummyHowTo(minute: "", second: "30", tool: ["전자레인지"], img: UIImage(named: "data11"), description: "준비한 소시지를 전자렌지에 약 30초간 데워주세요"),
        dummyHowTo(minute: "", second: "20", tool: [], img: UIImage(named: "data12"), description: "자이언트떡볶이와 콕콕콕 스파게티에 물을 넣어주세요"),
        dummyHowTo(minute: "3", second: "", tool: ["전자레인지"], description: "물을 넣은 자이언트 떡볶이를 전자렌지에 3분 돌려주세요"),
        dummyHowTo(minute: "", second: "50", tool: [], description: "스파게티 물을 버리고 전자렌지에 돌린 떡볶이에 라면과 스프를 넣고 잘 섞어주세요, 의성마늘후랑크소시지를 먹기 좋은 크기로 잘라 넣어주세요"),
        dummyHowTo(minute: "", second: "30", tool: ["전자레인지"], img: UIImage(named: "data13"), description: "모짜렐라 피자치즈를 위에 얹어주고, 치즈가 녹을 정도로 전자렌지에 약 30초간 데워주세요"),
    ]
    
    //table view height 관련 property
    var isFolded = [Bool]()
    var collectionViewHeight = 0
    
    //IF THERE IS REVIEW STAR PROPERTY
    var star = 0
    
    
    //MARK: LIFE CYCLE
    override func viewWillAppear(_ animated: Bool) {
        tabBarController?.tabBar.isHidden = true
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
        setupData()
        tableViewSetup()
        
        isFolded = Array(repeating: true, count: recipeInformation.count)
        self.navigationController?.interactivePopGestureRecognizer?.delegate = self
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showPopUpReview",
           let vc = segue.destination as? ReviewPopUpViewController {
            vc.delegate = self
            if star > 0 {
                vc.star = star
            }
        }
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
    
    //데이터 처리 
    private func setupData(){
        
    }
    
}

//MARK: UI
extension DetailViewController {
    private func setupUI(){
        navigationBarSetup()
        navigationController?.navigationBar.isHidden = false
    }
    
    private func navigationBarSetup(){
        navigationItem.title = information.title
        
        let backItemButton = UIBarButtonItem(image: UIImage(systemName: "chevron.left"), style: .plain, target: self, action: #selector(backButtonTapped))
        backItemButton.tintColor = .black
       
        //SAVE OR DELETE BASED ON MY OR OTHER'S POST
        let saveItemButton = UIBarButtonItem(image: UIImage(named: "BMark"), style: .plain, target: self, action: #selector(backButtonTapped))
        saveItemButton.tintColor = .black
        
        navigationItem.leftBarButtonItem = backItemButton
        navigationItem.rightBarButtonItem = saveItemButton
    }
    
    //NAVIGATION BUTTON
    @objc private func backButtonTapped(){
        navigationController?.popViewController(animated: true)
    }
    
    @objc private func saveButtonTapped(){
    }
}

//MARK: TABLE VIEW
extension DetailViewController: UITableViewDelegate, UITableViewDataSource, RecipeCellDelegate, ReviewDelegate, ReviewPopUpDelegate, DetailTitleDelegate {
    func profileTapped(cell: DetailTitleCell) {
        guard let indexPath = tableView.indexPath(for: cell) else { return }
        //get other's profileID then show the profile
        
    
    }
    
    //GET DATA FROM POPUP REVIEW VIEW
    func reviewTapped(controller: ReviewPopUpViewController) {
        star = controller.star
        tableView.reloadRows(at: [IndexPath(row: recipeInformation.count+2, section: 0)], with: .none)
        //리뷰 처리 !! 
    }
    
    //REVIEW BUTTON TAPPED
    func reviewTapped(cell: ReviewTableViewCell) {
         performSegue(withIdentifier: "showPopUpReview", sender: self)
    }
    
    //WHEN COLLECTION VIEW TAPPED FOLD AND UNFOLD THE CELL
    func collectionViewTapped(sender: RecipeCell) {
        guard let indexPath = tableView.indexPath(for: sender) else { return }
        tableView(tableView, didSelectRowAt: indexPath)
    }
    
    //MARK: TABLE VIEW INIT
    func tableViewSetup(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.separatorStyle = .none
        tableView.showsVerticalScrollIndicator = false
        tableView.register(UINib(nibName: "DetailTitleCell", bundle: nil), forCellReuseIdentifier: "DetailTitleCell")
        tableView.register(HashtagCell.self, forCellReuseIdentifier: "HashtagCell")
        tableView.register(RecipeCell.self, forCellReuseIdentifier: "RecipeCell")
        tableView.register(UINib(nibName: "ReviewTableViewCell", bundle: nil), forCellReuseIdentifier: "ReviewTableViewCell")
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return recipeInformation.count + 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        //REVIEW CELL (LAST)
        if indexPath.item == recipeInformation.count + 2 {
            print("asdfasdf")
            let cell = tableView.dequeueReusableCell(withIdentifier: "ReviewTableViewCell") as! ReviewTableViewCell
            cell.selectionStyle = .none
            cell.delegate = self
            
//            //if reviewed
            if star > 0 {
                cell.isReviewed(star)
            }
//            //else if not reviewed
            else {
                cell.isNotReviewed()
            }
//
            return cell
            
            //PICTURE AND 요약 정보 OF THE POST CELL (1'ST)
        } else if indexPath.item == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "DetailTitleCell", for: indexPath) as! DetailTitleCell
            
            cell.configuration(userImg: information.userImg, username: information.username, following: information.following, thumbnailImg: information.thumbnailImg, title: information.title, bookmarkCount: information.bookmarkCount, cookingTime: information.cookingTime, tools: information.tools, level: information.level, stars: information.stars)
            cell.selectionStyle = .none
           
            cell.delegate = self
            cell.backgroundColor = .brown
            return cell
        }
        
        // EXPLANATION AND INGREDIENTS CELL (2'ND)
        else if indexPath.item == 1 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "HashtagCell", for: indexPath) as! HashtagCell
            cell.titleLabel.text = "편의점 꿀조합 레시피 best of best"
            cell.setupCollectionView(dataSource: self, delegate: self)
            cell.selectionStyle = .none
            
            return cell
        }
        
        // 단계'S CELL
        else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeCell", for: indexPath) as! RecipeCell
            let data = recipeInformation[indexPath.item-2]
            
            let minute = data.minute == "" ? "" : "\(data.minute)분"
            let second = data.second == "" ? "" : "\(data.second)초"
            let time = "\(minute)\(second)"
            
            cell.configuration(step: indexPath.item-1, time: time, tool: data.tool, image: data.img, description: data.description, isFolded: isFolded[indexPath.item-2])
            cell.delegate = self
            
            if !isFolded[indexPath.item-2] {
                cell.toggleImageViewVisibility(isFolded: isFolded[indexPath.item-2], image: data.img)
                cell.informationView.layer.borderColor = UIColor(named: "pink")?.cgColor
                cell.dottedLine.backgroundColor = UIColor(named: "pink")
                cell.stepLabelBackground.tintColor = UIColor(named: "pink")
                cell.stepLabel.textColor = .white
            } else {
                cell.toggleImageViewVisibility(isFolded: isFolded[indexPath.item-2], image: data.img)
                cell.informationView.layer.borderColor = UIColor(named: "gray")?.cgColor
                cell.dottedLine.backgroundColor = UIColor(named: "gray")
                cell.stepLabelBackground.tintColor = UIColor(named: "softGray")
                cell.stepLabel.textColor = UIColor(named: "gray")
            }
            
            //IF ITS THE LAST STEP HIDE THE DOTTED LINE
            if indexPath.item - 1 == recipeInformation.count  {
                cell.dottedLine.isHidden = true
            } else {
                cell.dottedLine.isHidden = false
            }
            
            cell.selectionStyle = .none
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        // REVIEW CELL (LAST)
        if indexPath.item == recipeInformation.count + 2 {
            return 158
        }
        
        // POST INFORMATION (1'ST)
        else if indexPath.item == 0 {
            return 344
        }
        
        // INGREDIENT AND EXPLANATION (2'ND)
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
            
        //STEP CELL
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeCell") as! RecipeCell
            cell.layoutIfNeeded()
            let data = recipeInformation[indexPath.item-2]
            
            let minute = data.minute == "" ? "" : "\(data.minute)분"
            let second = data.second == "" ? "" : "\(data.second)초"
            let time = "\(minute)\(second)"
            
            var firstline: CGFloat = calculateLabelSize(text: time).width
            firstline += data.tool.map({calculateLabelSize(text: $0).width}).reduce(0, +)
//            firstline += CGFloat(1 * 12) + 32
            firstline += CGFloat(data.tool.count * 12) + 32
            
            if firstline/(view.bounds.width/1.5) > 1 {
                let line = firstline/(view.bounds.width/1.8)
                firstline = ceil(line) * 31
            } else {
                firstline = 30
            }
            
            var imageHeight: CGFloat = 0
            let last = calculateLabelSizeRecipe(text: data.description).height
            if !isFolded[indexPath.item-2] {
                if data.img != nil {
                    if let image = data.img {
                        let maxWidth: CGFloat = CGFloat(view.bounds.width/1.5)
                        let maxHeight: CGFloat = 130
                        let aspectRatio: CGFloat = 16 / 9  // 1.91:1
                        
                        let imageWidth = image.size.width
                        imageHeight = min(maxHeight, min(imageWidth * aspectRatio, maxWidth))
                    }
                    
                    return CGFloat(firstline) + imageHeight + last + 80
                }
            }
            
            return CGFloat(firstline) + imageHeight + last + 50
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.item > 1  && indexPath.item != recipeInformation.count + 2 {
            let allTrueValues = Array(repeating: true, count: isFolded.count)
            let lastFolded = isFolded.firstIndex(where: {$0 == false})
            
            isFolded = allTrueValues
            isFolded[indexPath.item-2].toggle()
            
            if let lastFolded = lastFolded {
                tableView.reloadRows(at: [indexPath, IndexPath(row: lastFolded+2, section: 0)], with: .automatic)
            } else {
                tableView.reloadRows(at: [indexPath], with: .automatic)
            }
        }
    }
    
    //CALCULATE FOR STEP'S HEIGHT
    func calculateLabelSizeRecipe(text: String) -> CGSize {
        if let cachedSize = labelSizeCache[text] {
            return cachedSize
        }
        
        let label = UILabel()
        label.numberOfLines = 0
        label.text = text
        label.font = UIFont.systemFont(ofSize: 14)
        
        let options: NSStringDrawingOptions = [.usesLineFragmentOrigin, .usesFontLeading]
        let attributes = [NSAttributedString.Key.font: label.font!]
        let size = CGSize(width: CGFloat(view.bounds.width/1.3), height: .greatestFiniteMagnitude)
        
        let boundingRect = (text as NSString).boundingRect(
            with: size,
            options: options,
            attributes: attributes,
            context: nil
        )
        
        let calculatedSize = boundingRect.size
        labelSizeCache[text] = calculatedSize
        return calculatedSize
    }
    
}

//MARK: UI COLLECTION VIEW (INGREDIENTS // HASHTAG)
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
    
    //CALCULATING FOR INGREDIENT'S HEIGHT
    func calculateLabelSize(text: String) -> CGSize {
        if let cachedSize = labelSizeCache[text] {
            return cachedSize
        }
        
        let label = UILabel()
        label.numberOfLines = 0
        label.text = text
        label.font = UIFont.systemFont(ofSize: 10)
        label.sizeToFit()
        let calculatedSize = label.frame.size
        labelSizeCache[text] = calculatedSize // Cache the calculated size
        return calculatedSize
    }
}
