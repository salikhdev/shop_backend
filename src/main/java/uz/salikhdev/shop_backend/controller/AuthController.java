package uz.salikhdev.shop_backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.shop_backend.dto.request.ResendCodeRequest;
import uz.salikhdev.shop_backend.dto.request.UserLoginRequest;
import uz.salikhdev.shop_backend.dto.request.UserRegisterRequest;
import uz.salikhdev.shop_backend.dto.request.VerifyRequest;
import uz.salikhdev.shop_backend.dto.response.SuccessResponse;
import uz.salikhdev.shop_backend.dto.response.TokenResponse;
import uz.salikhdev.shop_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<@NonNull TokenResponse> login(@RequestBody UserLoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<@NonNull SuccessResponse> register(@RequestBody UserRegisterRequest request) {
        authService.register(request);
        return SuccessResponse.ok("User registered successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<@NonNull SuccessResponse> verify(@RequestBody VerifyRequest request) {
        authService.verification(request.email(), request.code());
        return SuccessResponse.ok("Email verified successfully");
    }

    @PostMapping("/resend-code")
    public ResponseEntity<@NonNull SuccessResponse> resendCode(@RequestBody @NonNull ResendCodeRequest request) {
        authService.resendVerificationCode(request.email());
        return SuccessResponse.ok("Verification code resent successfully");
    }
}
