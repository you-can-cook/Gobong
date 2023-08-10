//
//  AddDetailPostViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/10.
//

import UIKit
import YPImagePicker
import Photos

class AddDetailPostViewController: UIViewController {

    @IBOutlet weak var saveButton: UIButton!
    @IBOutlet weak var descriptionTextField: UITextView!
    @IBOutlet weak var add10MinButton: UIButton!
    @IBOutlet weak var add5MinButton: UIButton!
    @IBOutlet weak var add1MinButotn: UIButton!
    @IBOutlet weak var add30secButton: UIButton!
    @IBOutlet weak var add10Sec: UIButton!
    @IBOutlet weak var secondField: UITextField!
    @IBOutlet weak var minuteField: UITextField!
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var postImage: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
        setTapGesture()
    }
}

extension AddDetailPostViewController: UITextFieldDelegate {
    private func setupUI(){
        add10Sec.layer.cornerRadius = 16
        add30secButton.layer.cornerRadius = 16
        add1MinButotn.layer.cornerRadius = 16
        add5MinButton.layer.cornerRadius = 16
        add10MinButton.layer.cornerRadius = 16
        
        descriptionTextField.layer.borderColor = UIColor(named: "gray")?.cgColor
        descriptionTextField.layer.borderWidth = 1
        
        minuteField.layer.borderWidth = 1
        minuteField.layer.borderColor = UIColor(named: "gray")?.cgColor
        minuteField.layer.masksToBounds = true
        
        secondField.layer.borderWidth = 1
        secondField.layer.borderColor = UIColor(named: "gray")?.cgColor
        secondField.layer.masksToBounds = true
        
        postImage.image = UIImage(named: "uploadPhoto")
        
        //완료 버튼
        checkOkNext()
        
        minuteField.delegate = self
        secondField.delegate = self
        
    }
    
    //text field
    func textFieldDidChangeSelection(_ textField: UITextField) {
        if (minuteField.text != "0" && minuteField.text != "" && minuteField.text != nil) ||
           (secondField.text != "0" && secondField.text != "" && secondField.text != nil) {

            minuteField.layer.borderColor = UIColor(named: "pink")?.cgColor
            secondField.layer.borderColor = UIColor(named: "pink")?.cgColor
            checkOkNext()
        } else {
            minuteField.layer.borderColor = UIColor(named: "gray")?.cgColor
            secondField.layer.borderColor = UIColor(named: "gray")?.cgColor
            checkOkNext()
        }
    }

    
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
                            
                            self.checkOkNext()
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
    
    //Button
    @IBAction func tenMinTapped(_ sender: Any) {
        if let currMinutes = minuteField.text {
            let newMinutes = (Int(currMinutes) ?? 0) + 10
            minuteField.text = "\(newMinutes)"
        } else {
            minuteField.text = "\(10)"
        }
        
        minuteField.layer.borderColor = UIColor(named: "pink")?.cgColor
        secondField.layer.borderColor = UIColor(named: "pink")?.cgColor
        checkOkNext()
        
    }
    
    @IBAction func fiveMinTapped(_ sender: Any) {
        if let currMinutes = minuteField.text {
            let newMinutes = (Int(currMinutes) ?? 0) + 5
            minuteField.text = "\(newMinutes)"
        } else {
            minuteField.text = "\(5)"
        }
        
        minuteField.layer.borderColor = UIColor(named: "pink")?.cgColor
        secondField.layer.borderColor = UIColor(named: "pink")?.cgColor
        checkOkNext()
    }
    
    @IBAction func oneMinTapped(_ sender: Any) {
        if let currMinutes = minuteField.text {
            let newMinutes = (Int(currMinutes) ?? 0) + 1
            minuteField.text = "\(newMinutes)"
        } else {
            minuteField.text = "\(1)"
        }
        
        minuteField.layer.borderColor = UIColor(named: "pink")?.cgColor
        secondField.layer.borderColor = UIColor(named: "pink")?.cgColor
        checkOkNext()
    }
    
    @IBAction func thirtysecTapped(_ sender: Any) {
        if let curr = secondField.text {
            var new = (Int(curr) ?? 0) + 30
            var newMinutes = 0
            
            if new > 59 {
                newMinutes = new / 60
                new = new % 60
            }

            if let min = minuteField.text {
                let totalMinutes = (Int(min) ?? 0) + newMinutes
                minuteField.text = "\(totalMinutes)"
                secondField.text = "\(new)"
            } else {
                minuteField.text = "\(newMinutes)"
                secondField.text = "\(new)"
            }
        } else {
            secondField.text = "\(30)"
        }
        
        minuteField.layer.borderColor = UIColor(named: "pink")?.cgColor
        secondField.layer.borderColor = UIColor(named: "pink")?.cgColor
        checkOkNext()
    }
    
    @IBAction func tenSecTapped(_ sender: Any) {
        if let curr = secondField.text {
            var new = (Int(curr) ?? 0) + 10
            if new > 59 {
                if let min = minuteField.text {
                    minuteField.text = "\((Int(min) ?? 0) + 1)"
                    new = new - 60
                    secondField.text = "\(new)"
                } else {
                    minuteField.text = "\(1)"
                    new = new - 60
                    secondField.text = "\(new)"
                }
            } else {
                secondField.text = "\(new)"
            }
        } else {
            secondField.text = "\(10)"
        }
        
        minuteField.layer.borderColor = UIColor(named: "pink")?.cgColor
        secondField.layer.borderColor = UIColor(named: "pink")?.cgColor
        checkOkNext()
    }
    
    private func checkOkNext(){
        if minuteField.layer.borderColor == UIColor(named: "pink")?.cgColor &&
           (descriptionTextField.text != "" && descriptionTextField.text != "자세한 조리 과정을 입력하세요") ||  postImage.image != UIImage(named: "uploadPhoto") {

            print(postImage.image != UIImage(named: "uploadPhoto"))
            saveButton.backgroundColor = UIColor(named: "pink")
            saveButton.isUserInteractionEnabled = true
        } else {
            // Both conditions are not met
            saveButton.backgroundColor = UIColor(named: "softGray")
            saveButton.isUserInteractionEnabled = false
        }

    }
}
