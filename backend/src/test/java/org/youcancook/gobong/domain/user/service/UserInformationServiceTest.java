package org.youcancook.gobong.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.youcancook.gobong.domain.user.dto.response.UserInformationResponse;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.DuplicationNicknameException;
import org.youcancook.gobong.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInformationServiceTest {

    @InjectMocks
    private UserInformationService userInformationService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("회원정보 조회")
    void checkInformation() {
        // given
        User user = createTestUser();
        ReflectionTestUtils.setField(user, "id", 1L);
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        // when
        UserInformationResponse result = userInformationService.getUserInformation(user.getId());

        // then
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getOauthProvider()).isEqualTo(user.getOAuthProvider().name());
        assertThat(result.getProfileImageURL()).isEqualTo(user.getProfileImageURL());
    }

    @Test
    @DisplayName("회원정보 수정 성공")
    void updateInformationSuccess() {
        // given
        String newNickname = "newNickname";
        String newProfileImageURL = "newProfileImageURL";
        User user = createTestUser();
        Long userId = 1L;
        when(userRepository.existsByNickname(newNickname))
                .thenReturn(false);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        // when
        userInformationService.updateInformation(userId, newNickname, newProfileImageURL);

        // then
        assertThat(user.getNickname()).isEqualTo(newNickname);
        assertThat(user.getProfileImageURL()).isEqualTo(newProfileImageURL);
    }

    @Test
    @DisplayName("회원정보 수정 실패 - 중복된 닉네임")
    void updateInformationFailByDuplicatedNickname() {
        // given
        User user = createTestUser();
        Long userId = 1L;
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        String newNickname = "newNickname";
        when(userRepository.existsByNickname(newNickname))
                .thenReturn(true);

        // expect
        assertThrows(DuplicationNicknameException.class,
                () -> userInformationService.updateInformation(1L, newNickname, "newProfileImageURL"));

    }

    private User createTestUser() {
        return User.builder()
                .nickname("nickname")
                .oAuthProvider(OAuthProvider.KAKAO)
                .oAuthId("oauthid")
                .profileImageURL("profileImageURL")
                .build();
    }
}