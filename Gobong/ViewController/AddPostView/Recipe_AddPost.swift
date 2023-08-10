//
//  Recipe_AddPost.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/09.
//

import Foundation
import UIKit

extension AddPostViewController: UITableViewDelegate, UITableViewDataSource, AddedRecipeCellDelegate, AddRecipeCellDelegate{
    
    func addTapped(cell: AddRecipeCell) {
        guard let indexPath = tableView.indexPath(for: cell) else { return }
        tableView(tableView, didSelectRowAt: indexPath)
    }
    func collectionViewTapped(sender: AddedRecipeCell) {
        guard let indexPath = tableView.indexPath(for: sender) else { return }
        tableView(tableView, didSelectRowAt: indexPath)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return recipes.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.item == recipes.count {
            let cell = tableView.dequeueReusableCell(withIdentifier: "AddRecipeCell") as! AddRecipeCell
            cell.selectionStyle = .none
            cell.stepLabel.text = "\(recipes.count+1)단계"
            cell.delegate = self
            
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "AddedRecipeCell") as! AddedRecipeCell
            
            let data = recipes[indexPath.item]
            cell.configuration(step: indexPath.item + 1, time: data.time, tool: data.tool, image: data.img, description: data.description, isFolded: isFolded[indexPath.item])
            
            if !isFolded[indexPath.item] {
                cell.toggleImageViewVisibility(isFolded: isFolded[indexPath.item], image: data.img)
                cell.informationView.layer.borderColor = UIColor(named: "pink")?.cgColor
                cell.dottedLine.backgroundColor = UIColor(named: "pink")
                cell.stepLabelBackground.tintColor = UIColor(named: "pink")
                cell.stepLabel.textColor = .white
            } else {
                cell.toggleImageViewVisibility(isFolded: isFolded[indexPath.item], image: data.img)
                cell.informationView.layer.borderColor = UIColor(named: "gray")?.cgColor
                cell.dottedLine.backgroundColor = UIColor(named: "gray")
                cell.stepLabelBackground.tintColor = UIColor(named: "softGray")
                cell.stepLabel.textColor = UIColor(named: "gray")
            }
            
            cell.delegate = self
            cell.selectionStyle = .none
            cell.isUserInteractionEnabled = true
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.item == recipes.count {
            tableViewCellHeight[indexPath.item] = 127
            return 127
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "AddedRecipeCell") as! AddedRecipeCell
            cell.layoutIfNeeded()
            let data = recipes[indexPath.item]
            
            var firstline = data.time.map({calculateLabelSize(text: $0).width}).reduce(0, +)
            firstline += data.tool.map({calculateLabelSize(text: $0).width}).reduce(0, +)
            firstline += CGFloat(data.time.count * 12) + 32
            firstline += CGFloat(data.tool.count * 12) + 32
            
            if firstline/(view.bounds.width/1.6) > 1 {
                let line = firstline/(view.bounds.width/1.8)
                firstline = ceil(line) * 31
            } else {
                firstline = 30
            }
            
            var imageHeight: CGFloat = 0
            let last = calculateLabelSizeRecipe(text: data.description).height
            if !isFolded[indexPath.item] {
                if data.img != nil {
                    if let image = UIImage(named: data.img!) {
                        let maxWidth: CGFloat = CGFloat(view.bounds.width/1.5)
                        let maxHeight: CGFloat = 130
                        let aspectRatio: CGFloat = 16 / 9  // 1.91:1
                        
                        let imageWidth = image.size.width
                        imageHeight = min(maxHeight, min(imageWidth * aspectRatio, maxWidth))
                    }
                    tableViewCellHeight[indexPath.item] = CGFloat(firstline) + imageHeight + last + 77
                    
                    reloadHeight()
                    return CGFloat(firstline) + imageHeight + last + 77
                }
            }
            tableViewCellHeight[indexPath.item] = CGFloat(firstline) + imageHeight + last + 47
            
            reloadHeight()
            return CGFloat(firstline) + imageHeight + last + 47
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.item == recipes.count {
            performSegue(withIdentifier: "showAddDetailPost", sender: self)
        } else {
            let allTrueValues = Array(repeating: true, count: isFolded.count)
            let lastFolded = isFolded.firstIndex(where: {$0 == false})
            
            isFolded = allTrueValues
            isFolded[indexPath.item].toggle()
            
            if let lastFolded = lastFolded {
                tableView.reloadRows(at: [indexPath, IndexPath(row: lastFolded, section: 0)], with: .automatic)
            } else {
                tableView.reloadRows(at: [indexPath], with: .automatic)
            }
        }
    }
    
    func reloadHeight(){
        tableViewHeight.constant = tableViewCellHeight.reduce(0, +)
    }
    
    
    func calculateLabelSizeRecipe(text: String) -> CGSize {
        let label = UILabel()
        label.numberOfLines = 0
        label.text = text
        label.font = UIFont.systemFont(ofSize: 14)
        
        let options: NSStringDrawingOptions = [.usesLineFragmentOrigin, .usesFontLeading]
        let attributes = [NSAttributedString.Key.font: label.font!]
        let size = CGSize(width: CGFloat(view.bounds.width/1.5), height: .greatestFiniteMagnitude)
        
        let boundingRect = (text as NSString).boundingRect(
            with: size,
            options: options,
            attributes: attributes,
            context: nil
        )
        
        let calculatedSize = boundingRect.size
        return calculatedSize
    }
    
    func calculateLabelSize(text: String) -> CGSize {
        let label = UILabel()
        label.numberOfLines = 0
        label.text = text
        label.font = UIFont.systemFont(ofSize: 10)
        label.sizeToFit()
        let calculatedSize = label.frame.size
        return calculatedSize
    }
    
}
