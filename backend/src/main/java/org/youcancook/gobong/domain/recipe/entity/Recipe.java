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
@Table(name = "recipe")
public class Recipe extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
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

    @Column(nullable = false)
    private Long cookwares;

    @Column(nullable = false)
    private Integer totalCookTimeInSeconds;

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
        cookwares = 0L;
        totalCookTimeInSeconds = 0;
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

    public void addCookware(Long cookware) {
        this.cookwares |= cookware;
    }

    public void addCookTime(Integer cookTimeInSeconds) {
        this.totalCookTimeInSeconds += cookTimeInSeconds;
    }

    public void clearDetails() {
        this.cookwares = 0L;
        this.totalCookTimeInSeconds = 0;
    }
}
