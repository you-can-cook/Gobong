package org.youcancook.gobong.domain.recipedetail.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.youcancook.gobong.domain.recipe.entity.Recipe;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String imageURL;

    @Column(nullable = false)
    private Integer cookTimeInSeconds;

    @Column(nullable = false)
    private Long cookware;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private Integer step;

    @Builder
    public RecipeDetail(String content, String imageURL, Integer cookTimeInSeconds, Long cookware, Recipe recipe) {
        this.content = content;
        this.imageURL = imageURL;
        this.cookTimeInSeconds = cookTimeInSeconds;
        this.cookware = cookware;
        this.recipe = recipe;
        recipe.getRecipeDetails().add(this);
    }
}