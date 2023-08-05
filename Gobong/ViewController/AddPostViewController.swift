//
//  AddPostViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/05.
//

import UIKit
import Photos
import YPImagePicker

class AddPostViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var hardButton: UIButton!
    @IBOutlet weak var normalButton: UIButton!
    @IBOutlet weak var easyButton: UIButton!
    @IBOutlet weak var collectionView: UICollectionViewCell!
    @IBOutlet weak var introductionField: UIStackView!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var postImage: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
    }

}

extension AddPostViewController: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    private func setupUI(){
        setupNavigationBar()
        setTapGesture()
        setupYPImagePicker()
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
//
//
//    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
//        picker.dismiss(animated: true, completion: nil)
//
//        if let pickedImage = info[.originalImage] as? UIImage {
//            postImage.image = pickedImage
//        }
//    }
//
//
//    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
//        picker.dismiss(animated: true, completion: nil)
//    }
}

