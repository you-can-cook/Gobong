//
//  AddPostViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/05.
//

import UIKit
import Photos
import YPImagePicker
import AlignedCollectionViewFlowLayout

class AddPostViewController: UIViewController {

    @IBOutlet weak var tableViewHeight: NSLayoutConstraint!
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var hardButton: UIButton!
    @IBOutlet weak var normalButton: UIButton!
    @IBOutlet weak var easyButton: UIButton!
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var introductionField: UITextField!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var postImage: UIImageView!
    @IBOutlet weak var scrollView: UIScrollView!
    
    var collectionViewHeightConstraint: NSLayoutConstraint!
    var levelSelected: String = "" {
        didSet{
            if levelSelected == "easy" {
                easyButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                easyButton.layer.borderWidth = 1
                easyButton.backgroundColor = UIColor(named: "pink")
                easyButton.titleLabel?.tintColor = .white
                
                normalButton.layer.borderColor = UIColor(named: "gray")?.cgColor
                normalButton.layer.borderWidth = 1
                normalButton.backgroundColor = .white
                normalButton.titleLabel?.textColor = UIColor(named: "gray")
                
                hardButton.layer.borderColor = UIColor(named: "gray")?.cgColor
                hardButton.layer.borderWidth = 1
                hardButton.backgroundColor = .white
                hardButton.titleLabel?.textColor = UIColor(named: "gray")
            } else if levelSelected == "normal" {
                easyButton.layer.borderColor = UIColor(named: "gray")?.cgColor
                easyButton.layer.borderWidth = 1
                easyButton.backgroundColor = .white
                easyButton.titleLabel?.textColor = UIColor(named: "gray")
                
                normalButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                normalButton.layer.borderWidth = 1
                normalButton.backgroundColor = UIColor(named: "pink")
                normalButton.titleLabel?.tintColor = .white
                
                hardButton.layer.borderColor = UIColor(named: "gray")?.cgColor
                hardButton.layer.borderWidth = 1
                hardButton.backgroundColor = .white
                hardButton.titleLabel?.textColor = UIColor(named: "gray")
            } else if levelSelected == "hard" {
                easyButton.layer.borderColor = UIColor(named: "gray")?.cgColor
                easyButton.layer.borderWidth = 1
                easyButton.backgroundColor = .white
                easyButton.titleLabel?.textColor = UIColor(named: "gray")
                
                normalButton.layer.borderColor = UIColor(named: "gray")?.cgColor
                normalButton.layer.borderWidth = 1
                normalButton.backgroundColor = .white
                normalButton.titleLabel?.textColor = UIColor(named: "gray")
                
                hardButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                hardButton.layer.borderWidth = 1
                hardButton.backgroundColor = UIColor(named: "pink")
                hardButton.titleLabel?.tintColor = .white
            }
            checkOK()
        }
    }
    
    var ingredients: [String] = []
    var recipes =  [dummyHowTo(time: ["3분"], tool: ["전자레인지"], img: UIImage(named: "dummyImg") ,description: "자이언트 떡볶이를 순서대로 3분 조리"),
                    dummyHowTo(time: ["3분"], tool: ["전자레인지"], img: UIImage(named: "dummyImg") ,description: "자이언트 떡볶이를 순서대로 3분 조리")]
    var isFolded = [Bool]()
    var tableViewCellHeight: [CGFloat] = [CGFloat(0), CGFloat(0), CGFloat(127)]
    
    override func viewWillAppear(_ animated: Bool) {
        tabBarController?.tabBar.isHidden = true
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
        view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(viewTapped)))
        isFolded = Array(repeating: true, count: recipes.count)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showAddDetailPost" {
            if let VC = segue.destination as? AddDetailPostViewController {
                VC.delegate = self
            }
        }
    }
    
    @objc private func viewTapped(){
        introductionField.resignFirstResponder()
        titleTextField.resignFirstResponder()
    }
    
}

extension AddPostViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate, UITextFieldDelegate {
    private func setupUI(){
        setupNavigationBar()
        setTapGesture()
        levelButtonUI()
        textFieldUI()
        collectionViewSetup()
        tableViewSetup()
    }
    
    private func setupNavigationBar(){
        navigationController?.navigationBar.isHidden = false
        let backItemButton = UIBarButtonItem(image: UIImage(systemName: "chevron.left"), style: .plain, target: self, action: #selector(backButtonTapped))
        backItemButton.tintColor = .black
        navigationItem.leftBarButtonItem = backItemButton
    }
    
    @objc private func backButtonTapped(){
        navigationController?.popViewController(animated: true)
    }
    
    //IMAGE
    private func setupYPImagePicker() -> YPImagePickerConfiguration{
        var config = YPImagePickerConfiguration()
        config.screens = [.library]
        config.showsPhotoFilters = false
        config.shouldSaveNewPicturesToAlbum = false
        config.showsCrop = .rectangle(ratio: (16/9))
        
        let renderer = UIGraphicsImageRenderer(size: CGSize(width: 168.0, height: 168.0))
        let newCapturePhotoImage = renderer.image { context in
            let symbolImage = UIImage(systemName: "largecircle.fill.circle")?.withTintColor(UIColor(named: "pink")!)
            symbolImage?.draw(in: CGRect(x: 0, y: 0, width: 168.0, height: 168.0))
        }
        let finalCapturePhotoImage = newCapturePhotoImage ?? config.icons.capturePhotoImage
        config.icons.capturePhotoImage = newCapturePhotoImage
    
        return config
    }
    
    private func setTapGesture(){
        postImage.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(pickImage)))
    }
    
    @IBAction func pickImage(_ sender: UIButton) {
        requestPhotoLibraryAccess { granted in
            if granted {
                DispatchQueue.main.async {
                    let picker = YPImagePicker(configuration: self.setupYPImagePicker())
                    picker.didFinishPicking { [unowned picker] items, _ in
                        if let photo = items.singlePhoto {
                            self.postImage.image = photo.image
                            self.checkOK()
                        }
                        picker.dismiss(animated: true, completion: nil)
                    }
                    self.present(picker, animated: true, completion: nil)
                }
            } else {
                print(granted)
            }
        }
    }
    
    func requestPhotoLibraryAccess(completion: @escaping (Bool) -> Void) {
        // Check the current authorization status
        let status = PHPhotoLibrary.authorizationStatus()

        switch status {
        case .authorized:
            // User has already granted access
            completion(true)
        case .notDetermined:
            // Request access
            PHPhotoLibrary.requestAuthorization { status in
                completion(status == .authorized)
            }
        default:
            // User has denied or restricted access
            completion(false)
        }
    }
    
    //TEXT FIELD
    private func textFieldUI(){
        introductionField.tintColor = UIColor(named: "pink")
        introductionField.borderStyle = .roundedRect
        introductionField.layer.borderWidth = 1
        introductionField.layer.borderColor = UIColor(named: "gray")?.cgColor
        
        titleTextField.tintColor = UIColor(named: "pink")
        titleTextField.borderStyle = .roundedRect
        titleTextField.layer.borderWidth = 1
        titleTextField.layer.borderColor = UIColor(named: "gray")?.cgColor
        
        titleTextField.delegate = self
        introductionField.delegate = self
    }
    
    func textFieldDidChangeSelection(_ textField: UITextField) {
        if let text = titleTextField.text, !text.isEmpty {
            titleTextField.layer.borderWidth = 1
            titleTextField.layer.borderColor = UIColor(named: "pink")?.cgColor
        } else {
            titleTextField.layer.borderWidth = 1
            titleTextField.layer.borderColor = UIColor(named: "gray")?.cgColor
        }
        
        if let text = introductionField.text, !text.isEmpty {
            introductionField.layer.borderWidth = 1
            introductionField.layer.borderColor = UIColor(named: "pink")?.cgColor
        } else {
            introductionField.layer.borderWidth = 1
            introductionField.layer.borderColor = UIColor(named: "gray")?.cgColor
        }
        
        checkOK()
        
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        introductionField.resignFirstResponder()
        titleTextField.resignFirstResponder()
        
        return true
    }
    
    //LEVEL
    private func levelButtonUI(){
        easyButton.layer.borderColor = UIColor(named: "gray")?.cgColor
        easyButton.layer.borderWidth = 1
        normalButton.layer.borderColor = UIColor(named: "gray")?.cgColor
        normalButton.layer.borderWidth = 1
        hardButton.layer.borderColor = UIColor(named: "gray")?.cgColor
        hardButton.layer.borderWidth = 1
    }
    
    @IBAction func hardButtonTapped(_ sender: Any) {
        levelSelected = "hard"
    }
    
    @IBAction func normalButtonTapped(_ sender: Any) {
        levelSelected = "normal"
    }
    
    @IBAction func easyButtonTapped(_ sender: Any) {
        levelSelected = "easy"
    }
    
    
    func tableViewSetup(){
        tableView.dataSource = self
        tableView.delegate = self
        tableView.separatorStyle = .none
        tableView.showsVerticalScrollIndicator = false
//        tableView.isScrollEnabled = false
        tableView.register(AddRecipeCell.self, forCellReuseIdentifier: "AddRecipeCell")
        tableView.register(AddedRecipeCell.self, forCellReuseIdentifier: "AddedRecipeCell")
        tableViewHeight.constant = 127
    }
}

//get passed data
extension AddPostViewController: AddDetailPostDelegate {
    func passData(controller: AddDetailPostViewController) {
        let description = controller.descriptionTextField.text == "자세한 조리 과정을 입력하세요" ? "" : controller.descriptionTextField.text
        var minute = controller.minuteField.text ?? ""
        var second = controller.secondField.text ?? ""
        
        if minute != "" {
            minute += "분"
        }
        
        if second != "" {
            second += "분"
        }
        
        var newRecipe: dummyHowTo
        
        if controller.postImage.image != UIImage(named: "uploadPhoto") {
            newRecipe = dummyHowTo(time: ["\(minute)\(second)"], tool: controller.tools, img: controller.postImage.image, description: description ?? "")
        } else {
            newRecipe = dummyHowTo(time: ["\(minute)\(second)"], tool: controller.tools, description: description ?? "")
        }
        
        recipes.append(newRecipe)
        isFolded.append(true)
        tableViewCellHeight.insert(0, at: tableViewCellHeight.count - 1)
        
        tableView.reloadData()
        reloadHeight()
        checkOK()
    }
    
    func checkOK(){
        if postImage.image != UIImage(named: "uploadPhoto") && titleTextField.text != "" && ingredients.count != 0 && (easyButton.backgroundColor == UIColor(named: "pink") || normalButton.backgroundColor == UIColor(named: "pink") || hardButton.backgroundColor == UIColor(named: "pink")) && recipes.count != 0 {
            
            let postButton = UIBarButtonItem(title: "게시", style: .plain, target: self, action: #selector(postButtonTapped))
            postButton.setTitleTextAttributes([NSAttributedString.Key.font: UIFont.boldSystemFont(ofSize: 16)], for: .normal)
            postButton.tintColor = UIColor(named: "pink")
            navigationItem.rightBarButtonItem = postButton
        } else {
            navigationItem.rightBarButtonItem = nil
        }
    }

    @objc private func postButtonTapped(){
        navigationController?.popViewController(animated: true)
    }
}

