//
//  FilterViewController.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/12.
//

import UIKit
import StepSlider

class FilterViewController: UIViewController {

    @IBOutlet weak var defaultButton: UIButton!
    @IBOutlet weak var searchButton: UIButton!
    
    @IBOutlet weak var collectionView: UICollectionViewCell!
    //별점
    @IBOutlet weak var oneStarButton: UIButton!
    @IBOutlet weak var twoStarButton: UIButton!
    @IBOutlet weak var threeStarButton: UIButton!
    @IBOutlet weak var fourStarButton: UIButton!
    @IBOutlet weak var fiveStarButton: UIButton!
    //슬라이더
    @IBOutlet weak var stepSlider: StepSlider!
    //난이도
    @IBOutlet weak var hardButton: UIButton!
    @IBOutlet weak var normalButton: UIButton!
    @IBOutlet weak var easyButton: UIButton!
    //정렬
    @IBOutlet weak var byPopularityButton: UIButton!
    @IBOutlet weak var byNewestButton: UIButton!
    @IBOutlet weak var searchBar: UISearchBar!
    override func viewDidLoad() {
        super.viewDidLoad()

        tabBarController?.tabBar.isHidden = true
        // Do any additional setup after loading the view.
    }
    
    @IBAction func searchButtonTapped(_ sender: Any) {
    }
    @IBAction func defaultButtonTapped(_ sender: Any) {
    }
    @IBAction func fiveStarTapped(_ sender: Any) {
    }
    @IBAction func fourStarTapped(_ sender: Any) {
    }
    @IBAction func threeStarTapped(_ sender: Any) {
    }
    @IBAction func twoStarTapped(_ sender: Any) {
    }
    @IBAction func oneStarTapped(_ sender: Any) {
    }
    @IBAction func easyTapped(_ sender: Any) {
    }
    @IBAction func normalTapped(_ sender: Any) {
    }
    @IBAction func hardTapped(_ sender: Any) {
    }
    @IBAction func byNewestTapped(_ sender: Any) {
    }
    @IBAction func byPopularityTapped(_ sender: Any) {
    }
    
}
