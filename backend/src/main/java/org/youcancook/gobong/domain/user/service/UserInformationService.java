package org.youcancook.gobong.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.user.dto.response.UserInformationResponse;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.DuplicationNicknameException;
import org.youcancook.gobong.domain.user.exception.UserNotFoundException;
import org.youcancook.gobong.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInformationService {

    private final UserRepository userRepository;

    public UserInformationResponse getUserInformation(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return UserInformationResponse.from(user);
    }

    @Transactional
    public void updateInformation(Long userId, String nickname, String profileImageURL) {
        validateDuplicateNickname(nickname);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.updateNicknameAndProfileImageURL(nickname, profileImageURL);
    }

    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicationNicknameException();
        }
    }

}
