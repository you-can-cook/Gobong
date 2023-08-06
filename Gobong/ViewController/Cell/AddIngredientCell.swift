//
//  AddIngredientCell.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/06.
//

import Foundation
import UIKit

protocol IngredientCellDelegate: Any {
    func textFieldDidPressReturn(in cell: AddIngredientCell)
}

class AddIngredientCell: UICollectionViewCell, UITextFieldDelegate {
    
    var delegate: IngredientCellDelegate?
    
    let textField: UITextField = {
        let textField = UITextField()
        textField.translatesAutoresizingMaskIntoConstraints = false
        textField.tintColor = UIColor(named: "pink")
        textField.borderStyle = .roundedRect
        
        textField.layer.cornerRadius = 15
        textField.layer.masksToBounds = true
        
        textField.textAlignment = .center
        textField.font = UIFont.systemFont(ofSize: 14)
        textField.placeholder = "추가하기"
        return textField
    }()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        // Add the label to the cell's contentView
        contentView.addSubview(textField)
        textField.delegate = self
        
        // Set up constraints to make the label hug its content
        NSLayoutConstraint.activate([
            textField.topAnchor.constraint(equalTo: contentView.topAnchor),
            textField.leadingAnchor.constraint(equalTo: contentView.leadingAnchor),
            textField.trailingAnchor.constraint(equalTo: contentView.trailingAnchor),
            textField.bottomAnchor.constraint(equalTo: contentView.bottomAnchor)
        ])
    }
    
    func textFieldDidChangeSelection(_ textField: UITextField) {
        if textField.text != "" {
            textField.layer.borderWidth = 1
            textField.layer.borderColor = UIColor(named: "pink")?.cgColor
        } else {
            textField.layer.borderWidth = 1
            textField.layer.borderColor = UIColor(named: "gray")?.cgColor
        }
        updateCellWidth()
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        let maxLength = 40
        let currentString = (textField.text ?? "") as NSString
        let newString = currentString.replacingCharacters(in: range, with: string)

        return newString.count <= maxLength
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        delegate?.textFieldDidPressReturn(in: self)
        textField.resignFirstResponder()
        return true
    }
    

    func updateCellWidth() {
        let desiredWidth = textField.sizeThatFits(CGSize(width: CGFloat.greatestFiniteMagnitude, height: textField.frame.height)).width
        self.frame.size.width = desiredWidth
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
