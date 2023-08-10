package org.youcancook.gobong.domain.follow.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.follow.entity.Follow;
import org.youcancook.gobong.domain.follow.repository.FollowRepository;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.util.token.TokenManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;

    private static int testUserCount = 0;

    @Test
    @DisplayName("팔로우 성공")
    void followSuccess() throws Exception {
        // given
        User loginUser = saveTestUser();
        String accessToken = tokenManager.createTokenDto(loginUser.getId()).getAccessToken();
        User followee = saveTestUser();

        // when
        mockMvc.perform(post("/api/follow/" + followee.getId())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        List<Follow> follows = followRepository.findAll();
        assertThat(follows).hasSize(1);
        assertThat(follows.get(0).getFollower()).isEqualTo(loginUser);
        assertThat(follows.get(0).getFollowee()).isEqualTo(followee);
    }

    @Test
    @DisplayName("팔로우 실패 - 이미 팔로잉된 유저")
    void followFailByAlreadyFollowing() throws Exception {
        // given
        User loginUser = saveTestUser();
        String accessToken = tokenManager.createTokenDto(loginUser.getId()).getAccessToken();
        User followee = saveTestUser();
        followRepository.save(Follow.builder()
                .follower(loginUser)
                .followee(followee)
                .build());

        // when
        mockMvc.perform(post("/api/follow/" + followee.getId())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.ALREADY_FOLLOWING.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.ALREADY_FOLLOWING.getMessage()))
                .andDo(print());
    }

    @Test
    @DisplayName("언팔로우 성공")
    void unfollowSuccess() throws Exception {
        // given
        User loginUser = saveTestUser();
        String accessToken = tokenManager.createTokenDto(loginUser.getId()).getAccessToken();
        User followee = saveTestUser();
        followRepository.save(Follow.builder()
                .follower(loginUser)
                .followee(followee)
                .build());

        // when
        mockMvc.perform(post("/api/unfollow/" + followee.getId())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        List<Follow> follows = followRepository.findAll();
        assertThat(follows).hasSize(0);
    }

    @Test
    @DisplayName("팔로우 실패 - 팔로우하지 않은 유저")
    void unfollowFailByNotFollowing() throws Exception {
        // given
        User loginUser = saveTestUser();
        String accessToken = tokenManager.createTokenDto(loginUser.getId()).getAccessToken();
        User followee = saveTestUser();

        // when
        mockMvc.perform(post("/api/unfollow/" + followee.getId())
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOLLOWING.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.NOT_FOLLOWING.getMessage()))
                .andDo(print());
    }

    private User saveTestUser() {
        testUserCount++;
        User user = User.builder()
                .oAuthId("12345678" + testUserCount)
                .oAuthProvider((testUserCount % 2 == 0) ? OAuthProvider.KAKAO : OAuthProvider.GOOGLE)
                .nickname("nickname" + testUserCount)
                .profileImageURL("profileImageURL" + testUserCount)
                .build();
        return userRepository.save(user);
    }
}