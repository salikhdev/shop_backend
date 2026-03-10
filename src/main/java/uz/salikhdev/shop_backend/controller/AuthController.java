package uz.salikhdev.shop_backend.controller;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.shop_backend.dto.request.UserLoginRequest;
import uz.salikhdev.shop_backend.dto.request.UserRegisterRequest;
import uz.salikhdev.shop_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<@NonNull String> login(@RequestBody UserLoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<@NonNull String> register(@RequestBody UserRegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }


}
