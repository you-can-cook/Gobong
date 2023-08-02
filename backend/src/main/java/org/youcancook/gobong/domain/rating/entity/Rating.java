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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "recipe_id"})
})
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
    private Integer score;

    @Builder
    public Rating(User user, Recipe recipe, Integer score) {
        this.user = user;
        this.recipe = recipe;
        this.score = score;
    }
    public void updateScore(Integer score) {
        this.score = score;
    }
}
