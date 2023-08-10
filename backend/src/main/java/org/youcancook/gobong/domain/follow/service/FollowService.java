package org.youcancook.gobong.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.follow.entity.Follow;
import org.youcancook.gobong.domain.follow.exception.AlreadyFollowException;
import org.youcancook.gobong.domain.follow.exception.NotFollowException;
import org.youcancook.gobong.domain.follow.repository.FollowRepository;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.exception.UserNotFoundException;
import org.youcancook.gobong.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(UserNotFoundException::new);
        User followee = userRepository.findById(followeeId)
                .orElseThrow(UserNotFoundException::new);

        validateAlreadyFollow(follower, followee);

        Follow follow = Follow.builder()
                .follower(follower)
                .followee(followee)
                .build();
        followRepository.save(follow);
    }

    private void validateAlreadyFollow(User follower, User followee) {
        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new AlreadyFollowException();
        }
    }

    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(UserNotFoundException::new);
        User followee = userRepository.findById(followeeId)
                .orElseThrow(UserNotFoundException::new);

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(NotFollowException::new);
        followRepository.delete(follow);
    }
}
