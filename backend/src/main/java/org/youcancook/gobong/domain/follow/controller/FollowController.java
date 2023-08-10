package org.youcancook.gobong.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youcancook.gobong.domain.follow.service.FollowService;
import org.youcancook.gobong.global.resolver.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class FollowController {

    private final FollowService followService;

    @PostMapping("follow/{folloeeId}")
    public ResponseEntity<Void> follow(@LoginUserId Long loginUserId,
                                       @PathVariable Long folloeeId) {
        followService.follow(loginUserId, folloeeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("unfollow/{folloeeId}")
    public ResponseEntity<Void> unfollow(@LoginUserId Long loginUserId,
                                         @PathVariable Long folloeeId) {
        followService.unfollow(loginUserId, folloeeId);
        return ResponseEntity.ok().build();
    }
}
