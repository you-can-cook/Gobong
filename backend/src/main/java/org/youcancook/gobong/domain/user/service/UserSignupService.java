package org.youcancook.gobong.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.authentication.service.RefreshTokenService;
import org.youcancook.gobong.domain.user.dto.SignupDto;
import org.youcancook.gobong.domain.user.dto.response.SignupResponse;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.DuplicationNicknameException;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.util.token.TokenDto;
import org.youcancook.gobong.global.util.token.TokenManager;

@Service
@RequiredArgsConstructor
public class UserSignupService {

    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final TokenManager tokenManager;

    @Transactional
    public SignupResponse signup(SignupDto signupDto) {
        if (isDuplicateNickname(signupDto.getNickname())) {
            throw new DuplicationNicknameException();
        }

        User user = createUser(signupDto);
        userRepository.save(user);

        TokenDto tokenDto = tokenManager.createTokenDto(user.getId());
        refreshTokenService.saveRefreshToken(user.getId(), tokenDto);
        return SignupResponse.of(tokenDto);
    }

    private User createUser(SignupDto signupDto) {
        OAuthProvider oAuthProvider = OAuthProvider.from(signupDto.getOAuthProvider());
        return User.builder()
                .oAuthProvider(oAuthProvider)
                .oAuthId(signupDto.getOAuthId())
                .nickname(signupDto.getNickname())
                .profileImageURL(signupDto.getProfileImageURL())
                .build();
    }

    public boolean isDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
