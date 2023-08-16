package org.youcancook.gobong.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcancook.gobong.domain.authentication.service.TemporaryTokenService;
import org.youcancook.gobong.domain.user.dto.SignupDto;
import org.youcancook.gobong.domain.user.dto.request.LoginRequest;
import org.youcancook.gobong.domain.user.dto.request.SignupRequest;
import org.youcancook.gobong.domain.user.dto.request.UpdateUserInformationRequest;
import org.youcancook.gobong.domain.user.dto.response.LoginResponse;
import org.youcancook.gobong.domain.user.dto.response.SignupResponse;
import org.youcancook.gobong.domain.user.dto.response.TemporaryTokenIssueResponse;
import org.youcancook.gobong.domain.user.dto.response.UserInformationResponse;
import org.youcancook.gobong.domain.user.service.UserInformationService;
import org.youcancook.gobong.domain.user.service.UserLoginService;
import org.youcancook.gobong.domain.user.service.UserSignupService;
import org.youcancook.gobong.global.resolver.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final TemporaryTokenService temporaryTokenService;
    private final UserLoginService userLoginService;
    private final UserSignupService userSignupService;
    private final UserInformationService userInformationService;

    @PostMapping("temporary-token")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TemporaryTokenIssueResponse> getTemporaryToken() {
        String temporaryToken = temporaryTokenService.saveTemporaryToken();
        TemporaryTokenIssueResponse response = new TemporaryTokenIssueResponse(temporaryToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        Long temporaryTokenId = temporaryTokenService.findTemporaryTokenId(request.getTemporaryToken());
        LoginResponse response = userLoginService.login(request.getProvider(), request.getOauthId());
        temporaryTokenService.deleteTemporaryToken(temporaryTokenId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest request) {
        Long temporaryTokenId = temporaryTokenService.findTemporaryTokenId(request.getTemporaryToken());
        SignupResponse response = userSignupService.signup(SignupDto.from(request));
        temporaryTokenService.deleteTemporaryToken(temporaryTokenId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<UserInformationResponse> getUserInformation(@LoginUserId Long loginUserId) {
        UserInformationResponse response = userInformationService.getUserInformation(loginUserId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<Void> updateUserInformation(@LoginUserId Long loginUserId,
                                                      @RequestBody @Valid UpdateUserInformationRequest request) {
        userInformationService.updateInformation(loginUserId, request.getNickname(), request.getProfileImageURL());
        return ResponseEntity.ok().build();
    }
}
