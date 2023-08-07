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

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var hardButton: UIButton!
    @IBOutlet weak var normalButton: UIButton!
    @IBOutlet weak var easyButton: UIButton!
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var introductionField: UITextField!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var postImage: UIImageView!
    
    var collectionViewHeightConstraint: NSLayoutConstraint!
    var levelSelected: String = "" {
        didSet{
            if levelSelected == "easy" {
                easyButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                easyButton.layer.borderWidth = 1
                easyButton.backgroundColor = UIColor(named: "pink")
                easyButton.titleLabel?.tintColor = .white
                
                normalButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                normalButton.layer.borderWidth = 1
                normalButton.backgroundColor = .white
                normalButton.titleLabel?.textColor = UIColor(named: "pink")
                
                hardButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                hardButton.layer.borderWidth = 1
                hardButton.backgroundColor = .white
                hardButton.titleLabel?.textColor = UIColor(named: "pink")
            } else if levelSelected == "normal" {
                easyButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                easyButton.layer.borderWidth = 1
                easyButton.backgroundColor = .white
                easyButton.titleLabel?.textColor = UIColor(named: "pink")
                
                normalButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                normalButton.layer.borderWidth = 1
                normalButton.backgroundColor = UIColor(named: "pink")
                normalButton.titleLabel?.tintColor = .white
                
                hardButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                hardButton.layer.borderWidth = 1
                hardButton.backgroundColor = .white
                hardButton.titleLabel?.textColor = UIColor(named: "pink")
            } else if levelSelected == "hard" {
                easyButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                easyButton.layer.borderWidth = 1
                easyButton.backgroundColor = .white
                easyButton.titleLabel?.textColor = UIColor(named: "pink")
                
                normalButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                normalButton.layer.borderWidth = 1
                normalButton.backgroundColor = .white
                normalButton.titleLabel?.textColor = UIColor(named: "pink")
                
                hardButton.layer.borderColor = UIColor(named: "pink")?.cgColor
                hardButton.layer.borderWidth = 1
                hardButton.backgroundColor = UIColor(named: "pink")
                hardButton.titleLabel?.tintColor = .white
            }
        }
    }
    var ingredients: [String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
        view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(viewTapped)))
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
    

}

//UI COLLECTION VIEW (재료)
extension AddPostViewController: UICollectionViewDelegate, UICollectionViewDataSource, IngredientCellDelegate {
    func textFieldDidPressReturn(in cell: AddIngredientCell) {
        if let text = cell.textField.text {
            ingredients.append(text)
            collectionView.reloadData()
            
            collectionView.layoutIfNeeded()
            
            let contentHeight = collectionView.contentSize.height
            collectionViewHeightConstraint.isActive = false
            
            let newHeightConstraint = collectionView.heightAnchor.constraint(equalToConstant: contentHeight)
            newHeightConstraint.isActive = true
            
            collectionViewHeightConstraint = newHeightConstraint
        }
    }

    private func collectionViewSetup(){
        let alignedFlowLayout = collectionView?.collectionViewLayout as? AlignedCollectionViewFlowLayout
        alignedFlowLayout?.horizontalAlignment = .left
        alignedFlowLayout?.verticalAlignment = .center
        alignedFlowLayout?.minimumLineSpacing = 8
        alignedFlowLayout?.minimumInteritemSpacing = 8
        
        collectionView.dataSource = self
        collectionView.register(AddIngredientCell.self, forCellWithReuseIdentifier: "AddIngredientCell")
        
        collectionViewHeightConstraint = collectionView.heightAnchor.constraint(equalToConstant: 34)
        collectionViewHeightConstraint.isActive = true
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return ingredients.count + 1
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "AddIngredientCell", for: indexPath) as! AddIngredientCell
        
        if indexPath.row != ingredients.count {
            // Configure the cell with actual ingredient data
            cell.textField.text = ingredients[indexPath.item]
            cell.textField.isUserInteractionEnabled = false
            cell.textField.layer.borderWidth = 1
            cell.textField.layer.borderColor = UIColor(named: "pink")?.cgColor
        } else {
            // Reset the cell and configure it for adding
            cell.textField.text = nil
            cell.textField.layer.borderWidth = 1
            cell.textField.layer.borderColor = UIColor(named: "gray")?.cgColor
            cell.textField.placeholder = "추가하기"
            cell.textField.isUserInteractionEnabled = true
            
            cell.delegate = self
        }
        
        return cell
    }
}

//extension AddPostViewController: UITableViewDelegate, UITableViewDataSource {
//    private func tableViewSetup(){
//        
//    }
//    
//    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
//        <#code#>
//    }
//     
//    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
//        <#code#>
//    }
//    
//}
