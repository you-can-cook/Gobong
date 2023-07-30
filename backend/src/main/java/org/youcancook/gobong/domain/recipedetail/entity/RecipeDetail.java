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

    private String imageURL;
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IconType iconType;

    private String iconDescription;

    @Column(nullable = false)
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Builder
    public RecipeDetail(String imageURL, String content, IconType iconType, String iconDescription, Integer order, Recipe recipe) {
        this.imageURL = imageURL;
        this.content = content;
        this.iconType = iconType;
        this.iconDescription = iconDescription;
        this.order = order;
        this.recipe = recipe;
    }
}
