package org.youcancook.gobong.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.youcancook.gobong.domain.BaseTime.BaseTime;
import org.youcancook.gobong.domain.rating.entity.Rating;
import org.youcancook.gobong.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    private String introduction;

    private String ingredients;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String thumbnailURL;

    @OneToMany(mappedBy = "recipe")
    private List<Rating> rating = new ArrayList<>();

    @Builder
    public Recipe(User user, String title, String introduction, String ingredients, Difficulty difficulty,
                  String thumbnailURL) {
        this.user = user;
        this.title = title;
        this.introduction = introduction;
        this.ingredients = ingredients;
        this.difficulty = difficulty;
        this.thumbnailURL = thumbnailURL;
    }

    public void addRating(Rating rating){
        this.rating.add(rating);
    }

    public double getAverageRating(){
        return rating.stream()
                .mapToDouble(Rating::getScore)
                .average()
                .orElse(0.0);
    }

    public void updateProperties(String title, String introduction, String ingredients,
                                 Difficulty difficulty, String thumbnailURL) {
        this.title = title;
        this.introduction = introduction;
        this.ingredients = ingredients;
        this.difficulty = difficulty;
        this.thumbnailURL = thumbnailURL;
    }
}
