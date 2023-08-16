//
//  SettingsViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/14.
//

import UIKit

class SettingsViewController: UIViewController, UIGestureRecognizerDelegate{

    @IBOutlet weak var myPageView: UIView!
    @IBOutlet weak var notifView: UIView!
    @IBOutlet weak var deleteAccountView: UIView!
    
    override func viewWillAppear(_ animated: Bool) {
        tabBarController?.tabBar.isHidden = true
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupUI()
        setupFunc()
        
        self.navigationController?.interactivePopGestureRecognizer?.delegate = self
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
    
    private func setupFunc(){
        myPageView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(myPageTapped)))
        notifView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(notificationTapped)))
        deleteAccountView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(deleteAccountTapped)))
    }
    
    //SEGUE
    @objc
    private func myPageTapped(){
        performSegue(withIdentifier: "showEditAccountView", sender: self)
    }
    
    @objc
    private func notificationTapped(){
        performSegue(withIdentifier: "showPushNotification", sender: self)
    }
    
    @objc
    private func deleteAccountTapped(){
        performSegue(withIdentifier: "showDeleteAccountView", sender: self)
    }
    
    //MARK: UI
    private func setupUI(){
        setupNavigationBar()
    }
    
    //NAVIGATION BAR
    private func setupNavigationBar(){
        navigationItem.title = "설정"
        
        let backButton = UIBarButtonItem(image: UIImage(systemName: "chevron.left"), style: .plain, target: self, action: #selector(backButtonTapped))
        navigationItem.leftBarButtonItem = backButton
        backButton.tintColor = .black
    }
    
    //BUTTON
    @objc
    private func backButtonTapped(){
        navigationController?.popViewController(animated: true)
    }

    @IBAction func deleteButtonTapped(_ sender: Any) {
    }

}
