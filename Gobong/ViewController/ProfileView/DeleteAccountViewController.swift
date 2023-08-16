//
//  DeleteAccountViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/13.
//

import UIKit

class DeleteAccountViewController: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var deleteAccountButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
        
        self.navigationController?.interactivePopGestureRecognizer?.delegate = self
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
    
    private func setupUI(){
        setupNavigationBar()
    }
        
    private func setupNavigationBar(){
        navigationItem.title = "회원탈퇴"
        
        let backButton = UIBarButtonItem(image: UIImage(systemName: "chevron.left"), style: .plain, target: self, action: #selector(backButtonTapped))
        navigationItem.leftBarButtonItem = backButton
        backButton.tintColor = .black
    }
    
    @objc
    private func backButtonTapped(){
        navigationController?.popViewController(animated: true)
    }

    @IBAction func deleteButtonTapped(_ sender: Any) {
    }
}
