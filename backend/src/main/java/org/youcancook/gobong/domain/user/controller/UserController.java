package org.youcancook.gobong.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcancook.gobong.domain.authentication.service.TemporaryTokenService;
import org.youcancook.gobong.domain.user.dto.request.LoginRequest;
import org.youcancook.gobong.domain.user.dto.response.LoginResponse;
import org.youcancook.gobong.domain.user.dto.response.TemporaryTokenIssueResponse;
import org.youcancook.gobong.domain.user.service.UserLoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserLoginService userLoginService;
    private final TemporaryTokenService temporaryTokenService;

    @PostMapping("temporary-token")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TemporaryTokenIssueResponse> getTemporaryToken() {
        String temporaryToken = temporaryTokenService.saveTemporaryToken();
        TemporaryTokenIssueResponse response = new TemporaryTokenIssueResponse(temporaryToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        temporaryTokenService.validateTemporaryToken(request.getTemporaryToken());
        LoginResponse response = userLoginService.login(request.getProvider(), request.getOauthId());
        return ResponseEntity.ok(response);
    }
}
