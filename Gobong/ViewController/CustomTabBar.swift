//
//  CustomTabBar.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/02.
//

import Foundation
import UIKit

class CustomTabBarController: UITabBarController, UITabBarControllerDelegate {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.delegate = self
        tabBar.tintColor = UIColor(named: "pink")
        tabBar.backgroundColor = .white
    }
    
    
    func tabBarController(_ tabBarController: UITabBarController, didSelect viewController: UIViewController) {
            if let refreshableVC = viewController as? ViewController {
//                refreshableVC.refresh()
            }
        }

    
    func tabBarController(_ tabBarController: UITabBarController, shouldSelect viewController: UIViewController) -> Bool {
        if let index = tabBarController.viewControllers?.firstIndex(of: viewController) {
            // Customize the tint color based on the selected index
            switch index {
            case 0:
                tabBar.tintColor = UIColor(named: "pink")
            case 1:
                tabBar.tintColor = UIColor(named: "blue")
            case 2:
                tabBar.tintColor = UIColor(named: "yellow")
            case 3:
                tabBar.tintColor = UIColor(named: "green")
            default:
                tabBar.tintColor = UIColor(named: "pink")
            }
        }
        return true
    }
    
}
