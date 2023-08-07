package org.youcancook.gobong.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.youcancook.gobong.domain.authentication.entity.RefreshToken;
import org.youcancook.gobong.domain.authentication.entity.TemporaryToken;
import org.youcancook.gobong.domain.authentication.repository.RefreshTokenRepository;
import org.youcancook.gobong.domain.authentication.repository.TemporaryTokenRepository;
import org.youcancook.gobong.domain.authentication.service.TemporaryTokenService;
import org.youcancook.gobong.domain.user.dto.request.LoginRequest;
import org.youcancook.gobong.domain.user.entity.OAuthProvider;
import org.youcancook.gobong.domain.user.entity.User;
import org.youcancook.gobong.domain.user.repository.UserRepository;
import org.youcancook.gobong.global.error.ErrorCode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TemporaryTokenRepository temporaryTokenRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemporaryTokenService temporaryTokenService;

    @Test
    @DisplayName("임시 토큰 발급")
    void getTemporaryToken() throws Exception {
        // when
        ResultActions resultActions =
                mockMvc.perform(post("/api/users/temporary-token")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        List<TemporaryToken> temporaryTokens = temporaryTokenRepository.findAll();
        assertThat(temporaryTokens).hasSize(1);
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.temporaryToken").value(temporaryTokens.get(0).getToken()));
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        // given
        String temporaryToken = temporaryTokenService.saveTemporaryToken();
        User user = saveTestUser();

        // when
        LoginRequest loginRequest = new LoginRequest(user.getOAuthProvider().name(), user.getOAuthId(), temporaryToken);
        String request = objectMapper.writeValueAsString(loginRequest);
        ResultActions resultActions =
                mockMvc.perform(post("/api/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                        .andDo(print());

        // then
        List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();
        assertThat(refreshTokens).hasSize(1);

        List<TemporaryToken> temporaryTokens = temporaryTokenRepository.findAll();
        assertThat(temporaryTokens).hasSize(0);

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grantType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").value(refreshTokens.get(0).getRefreshToken()));
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 oAuthProvider")
    void loginFailByOAuthProvider() throws Exception {
        // given
        String temporaryToken = temporaryTokenService.saveTemporaryToken();
        User user = saveTestUser();

        // expect
        LoginRequest loginRequest = new LoginRequest(OAuthProvider.GOOGLE.name(), user.getOAuthId(), temporaryToken);
        String request = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()))
                .andDo(print());

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();
        assertThat(refreshTokens).hasSize(0);
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 oAuthId")
    void loginFailByOAuthId() throws Exception {
        // given
        String temporaryToken = temporaryTokenService.saveTemporaryToken();
        User user = saveTestUser();

        // expect
        LoginRequest loginRequest = new LoginRequest(user.getOAuthProvider().name(), user.getOAuthId() + "1", temporaryToken);
        String request = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()))
                .andDo(print());

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();
        assertThat(refreshTokens).hasSize(0);
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 temporaryToken")
    void loginFailByTemporaryToken() throws Exception {
        // given
        User user = saveTestUser();

        // expect
        LoginRequest loginRequest = new LoginRequest(user.getOAuthProvider().name(), user.getOAuthId(), "notFoundTemporaryToken");
        String request = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.TEMPORARY_TOKEN_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.TEMPORARY_TOKEN_NOT_FOUND.getMessage()))
                .andDo(print());

        List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();
        assertThat(refreshTokens).hasSize(0);
    }

    private User saveTestUser() {
        User user = User.builder()
                .oAuthId("12345678")
                .oAuthProvider(OAuthProvider.KAKAO)
                .nickname("nickname")
                .profileImageURL("profileImageURL")
                .build();
        return userRepository.save(user);
    }
}