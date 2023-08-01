package org.youcancook.gobong.domain.rating.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.youcancook.gobong.domain.recipe.entity.Recipe;
import org.youcancook.gobong.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private Long score;

    @Builder
    public Rating(User user, Recipe recipe, Long score) {
        this.user = user;
        this.recipe = recipe;
        this.score = score;
    }
}
