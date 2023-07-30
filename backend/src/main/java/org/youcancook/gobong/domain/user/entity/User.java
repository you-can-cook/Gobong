package org.youcancook.gobong.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.youcancook.gobong.domain.follow.entity.Follow;
import org.youcancook.gobong.domain.recipe.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String profileImageURL;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> following = new ArrayList<>();

    @Builder
    public User(String nickname, String email, String profileImageURL) {
        this.nickname = nickname;
        this.email = email;
        this.profileImageURL = profileImageURL;
    }
}
