//
//  RecipeCell.swift
//  Gobong
//
//  Created by Ebbyy on 2023/08/06.
//

import UIKit

class RecipeCell: UITableViewCell {
    
    let stepLabel: UILabel = {
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = UIFont.boldSystemFont(ofSize: 12)
        label.textColor = UIColor(named: "darkGray")
        return label
    }()
    
    let stepLabelBackground: UIImageView = {
        let image = UIImageView()
        image.image = UIImage(named: "작성 단계")
        image.contentMode = .scaleAspectFit
        image.translatesAutoresizingMaskIntoConstraints = false
        image.tintColor = UIColor(named: "softGray")
        
        return image
    }()
    
    let timeLabel: PaddingLabel = {
        let label = PaddingLabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = UIFont.boldSystemFont(ofSize: 10)
        label.textColor = UIColor(named: "darkGray")
        label.backgroundColor = UIColor(named: "softGray")
        label.paddingLeft = 3
        label.paddingRight = 3
        return label
    }()
    
    let toolLabel: PaddingLabel = {
        let label = PaddingLabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = UIFont.boldSystemFont(ofSize: 10)
        label.textColor = UIColor(named: "darkGray")
        label.backgroundColor = UIColor(named: "softGray")
        label.paddingLeft = 3
        label.paddingRight = 3
        return label
    }()
    
    let descriptionLabel: PaddingLabel = {
        let label = PaddingLabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.font = UIFont.systemFont(ofSize: 14)
        label.textColor = .black
        label.numberOfLines = 0
        label.paddingBottom = 10
        label.paddingTop = 10
        return label
    }()
    
    let UIimage: UIImageView = {
        let image = UIImageView()
        image.contentMode = .scaleAspectFill
        image.layer.cornerRadius = 4
        image.translatesAutoresizingMaskIntoConstraints = false
        return image
    }()
    
    let firstLineView = UIView()
    
    // Other UI elements can be added similarly
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        setupUI()
    }
    
    private func setupUI(){
        let mainUIView = UIView()
        mainUIView.translatesAutoresizingMaskIntoConstraints = false
        
        let infoStackView = UIStackView()
        infoStackView.axis = .vertical
        infoStackView.spacing = 0
        infoStackView.distribution = .fill
        infoStackView.translatesAutoresizingMaskIntoConstraints = false
        
        let subMainView = UIView()
        subMainView.translatesAutoresizingMaskIntoConstraints = false
        
        let stepView = UIView()
        stepView.translatesAutoresizingMaskIntoConstraints = false
        
        let informationView = UIView()
        informationView.translatesAutoresizingMaskIntoConstraints = false
        informationView.layer.borderWidth = 1
        informationView.layer.borderColor = UIColor(named: "darkGray")?.cgColor
        
        firstLineView.translatesAutoresizingMaskIntoConstraints = false
        
        subMainView.addSubview(stepView)
        subMainView.addSubview(informationView)
        
        stepView.addSubview(stepLabelBackground)
        stepView.addSubview(stepLabel)
        
        informationView.addSubview(infoStackView)
        
        infoStackView.addArrangedSubview(firstLineView)
        
        firstLineView.addSubview(timeLabel)
        firstLineView.addSubview(toolLabel)
        
        infoStackView.addArrangedSubview(UIimage)
        infoStackView.addArrangedSubview(descriptionLabel)
        
        descriptionLabel.backgroundColor = .white
        
        NSLayoutConstraint.activate([
            stepLabelBackground.leadingAnchor.constraint(equalTo: stepView.leadingAnchor),
            stepLabelBackground.topAnchor.constraint(equalTo: stepView.topAnchor),
            stepLabelBackground.trailingAnchor.constraint(equalTo: stepView.trailingAnchor),
            stepLabelBackground.bottomAnchor.constraint(equalTo: stepView.bottomAnchor),
            
            stepLabel.centerXAnchor.constraint(equalTo: stepView.centerXAnchor, constant: -3),
            stepLabel.centerYAnchor.constraint(equalTo: stepView.centerYAnchor),
            // Add more constraints as needed
        ])
        
        //-=======
        
        NSLayoutConstraint.activate([
            stepView.leadingAnchor.constraint(equalTo: subMainView.leadingAnchor, constant: 0),
            stepView.topAnchor.constraint(equalTo: subMainView.topAnchor, constant: 0),
            stepView.widthAnchor.constraint(equalToConstant: 48),
            
            informationView.leadingAnchor.constraint(equalTo: stepView.trailingAnchor, constant: 15),
            informationView.topAnchor.constraint(equalTo: subMainView.topAnchor, constant: 0),
            informationView.trailingAnchor.constraint(equalTo: subMainView.trailingAnchor, constant: 0),
            informationView.bottomAnchor.constraint(equalTo: subMainView.bottomAnchor, constant: 15),
            
            infoStackView.topAnchor.constraint(equalTo: informationView.topAnchor, constant: 0),
            infoStackView.bottomAnchor.constraint(equalTo: informationView.bottomAnchor),
            
            firstLineView.leadingAnchor.constraint(equalTo: infoStackView.leadingAnchor),
            firstLineView.trailingAnchor.constraint(equalTo: infoStackView.trailingAnchor),
            firstLineView.topAnchor.constraint(equalTo: infoStackView.topAnchor),
        ])
        
        
        NSLayoutConstraint.activate([
            timeLabel.leadingAnchor.constraint(equalTo: firstLineView.leadingAnchor, constant: 0),
            timeLabel.topAnchor.constraint(equalTo: firstLineView.topAnchor, constant: 12),
            timeLabel.heightAnchor.constraint(equalToConstant: 16),
            
            toolLabel.leadingAnchor.constraint(equalTo: timeLabel.trailingAnchor, constant: 4),
            toolLabel.topAnchor.constraint(equalTo: firstLineView.topAnchor, constant: 12),
            toolLabel.heightAnchor.constraint(equalToConstant: 16),
            
            UIimage.leadingAnchor.constraint(equalTo: informationView.leadingAnchor, constant: 12),
            UIimage.trailingAnchor.constraint(equalTo: informationView.trailingAnchor, constant: -12),
            UIimage.heightAnchor.constraint(lessThanOrEqualToConstant: 130),
            
        ])
        
        mainUIView.addSubview(subMainView)
        NSLayoutConstraint.activate([
            subMainView.leadingAnchor.constraint(equalTo: mainUIView.leadingAnchor, constant: 16),
            subMainView.topAnchor.constraint(equalTo: mainUIView.topAnchor, constant: 5),
            subMainView.trailingAnchor.constraint(equalTo: mainUIView.trailingAnchor, constant: -16),
            subMainView.bottomAnchor.constraint(equalTo: mainUIView.bottomAnchor, constant: -24)
        ])
        
        contentView.addSubview(mainUIView)
        
        NSLayoutConstraint.activate([
            mainUIView.leadingAnchor.constraint(equalTo: contentView.leadingAnchor, constant: 0),
            mainUIView.topAnchor.constraint(equalTo: contentView.topAnchor, constant: 0),
            mainUIView.trailingAnchor.constraint(equalTo: contentView.trailingAnchor, constant: 0),
            mainUIView.bottomAnchor.constraint(equalTo: contentView.bottomAnchor, constant: 0)
        ])
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func calculateHeightForData(_ data: dummyHowTo) -> CGFloat {
        let timeLabelHeight : CGFloat = 16.0
        let descriptionLabelHeight = descriptionLabel.intrinsicContentSize.height
        
        var imageHeight: CGFloat = 0
        if let image = UIimage.image {
            // Calculate the proportional height based on the aspect ratio
            let aspectRatio = image.size.width / image.size.height
            imageHeight = contentView.frame.width / aspectRatio
        }
        let totalHeight = timeLabelHeight + imageHeight + descriptionLabelHeight
        
        return totalHeight
    }
    
    func configuration(step: Int, time: Int, tool: String, image: String?, description: String, isFolded: Bool) {
        stepLabel.text = "\(step)단계"
        timeLabel.text = "\(time)분"
        toolLabel.text = tool
        if !isFolded{
            if image != nil{
                UIimage.isHidden = false
                UIimage.image = UIImage(named: image!)
                firstLineView.heightAnchor.constraint(equalToConstant: 28).isActive = false
            } else {
                UIimage.isHidden = true
                firstLineView.heightAnchor.constraint(equalToConstant: 28).isActive = true
            }
        } else {
            UIimage.isHidden = true
            firstLineView.heightAnchor.constraint(equalToConstant: 28).isActive = true
        }
        descriptionLabel.text = description
    }
    
    func toggleImageViewVisibility(isFolded: Bool, image: String?){
        if !isFolded {
            if image != nil{
                UIimage.isHidden = false
                UIimage.image = UIImage(named: image ?? "dummyImg")
                firstLineView.heightAnchor.constraint(equalToConstant: 28).isActive = false
            } else {
                UIimage.isHidden = true
                firstLineView.heightAnchor.constraint(equalToConstant: 28).isActive = true
            }
        } else {
            UIimage.isHidden = true
            firstLineView.heightAnchor.constraint(equalToConstant: 28).isActive = true
        }
    }

}

