package org.youcancook.gobong.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.youcancook.gobong.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String thumbnailURL;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private CookingTime cookingTime;

    @Enumerated(EnumType.STRING)
    private Cookware cookware;

    @Builder
    public Recipe(String title, User user, String thumbnailURL, Difficulty difficulty, CookingTime cookingTime, Cookware cookware) {
        this.title = title;
        this.user = user;
        this.thumbnailURL = thumbnailURL;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
        this.cookware = cookware;
    }
}
