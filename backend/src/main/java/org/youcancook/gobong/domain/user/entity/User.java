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

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, name = "oauth_id")
    private String oAuthId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "oauth_provider")
    private OAuthProvider oAuthProvider;

    private String profileImageURL;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Follow> following = new ArrayList<>();

    @Builder
    public User(String nickname, String oAuthId, OAuthProvider oAuthProvider, String profileImageURL) {
        this.nickname = nickname;
        this.oAuthId = oAuthId;
        this.oAuthProvider = oAuthProvider;
        this.profileImageURL = profileImageURL;
    }
}
