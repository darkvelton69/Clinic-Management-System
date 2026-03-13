package by.darkvelton69.inputproject.controller;

import by.darkvelton69.inputproject.dto.AuthRequest;
import by.darkvelton69.inputproject.dto.AuthResponse;
import by.darkvelton69.inputproject.dto.ChangePasswordRequest;
import by.darkvelton69.inputproject.dto.RegistrationRequest;
import by.darkvelton69.inputproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/polyclinic34/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegistrationRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PatchMapping("/editPassword")
    public ResponseEntity<AuthResponse> editPassword(@RequestBody ChangePasswordRequest cpr){
        return ResponseEntity.ok(authService.editPassword(cpr));
    }
}
