package com.thc.back14th.controller;

import com.thc.back14th.domain.User;

import com.thc.back14th.dto.login.GoogleLoginRequest;
import com.thc.back14th.dto.login.LoginResponse;
import com.thc.back14th.service.GoogleAuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final GoogleAuthService googleAuthService;
    public static final String SESSION_USER_ID = "LOGIN_USER_ID";

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(
            @Valid @RequestBody GoogleLoginRequest request,
            HttpSession session
    ) {
        User user = googleAuthService.loginWithGoogleIdToken(request.getIdToken());
        //구글이 준 idToken이 진짜인지 확인.

        //  세션에 로그인 정보 저장
        session.setAttribute(SESSION_USER_ID, user.getId());

        return ResponseEntity.ok(
                new LoginResponse(user.getId(), user.getEmail(), user.getName())
        );
    }

    //현재 로그인 상태
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Object userId = session.getAttribute(SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(401).body("NOT_LOGGED_IN");
        }
        return ResponseEntity.ok(userId);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();//세션 자체를 폐기해버림
        return ResponseEntity.ok().build();
    }
}
