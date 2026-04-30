package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.AuthRequest;
import by.darkvelton69.inputproject.dto.AuthResponse;
import by.darkvelton69.inputproject.dto.ChangePasswordRequest;
import by.darkvelton69.inputproject.dto.RegistrationRequest;
import by.darkvelton69.inputproject.entity.Client;
import by.darkvelton69.inputproject.entity.Role;
import by.darkvelton69.inputproject.entity.User;
import by.darkvelton69.inputproject.entity.VerificationToken;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.repository.ClientRepository;
import by.darkvelton69.inputproject.repository.UserRepository;
import by.darkvelton69.inputproject.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    @Transactional
    public AuthResponse register(RegistrationRequest request) {
        try {
            User user = new User();

            user.setAge(request.age());
            user.setEmail(request.email());
            user.setPassword(passwordEncoder.encode(request.password()));
            user.setRole(Role.PATIENT);
            user.setLastName(request.lastName());
            user.setFirstName(request.firstName());
            user.setMiddleName(request.middleName());

            user.setRole(Role.UNVERIFIED);

            userRepository.save(user);

            Client client = new Client();
            client.setPhone(request.phone());
            client.setUser(user);

            clientRepository.save(client);

            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(user);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
            tokenRepository.save(verificationToken);

            emailService.sendVerificationEmail(user.getEmail(), token);

            return new AuthResponse("Регистрация успешна. Проверьте почту для активации аккаунта.");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка в сохранении пользователя", e);
        }


    }

    @Transactional
    public String confirmEmail(String token){
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if(verificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Ссылка устарела. Запросите новую.");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);

        return "Email успешно подтвержден! Теперь вы можете войти в систему.";
    }

    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(), authRequest.password()
                )
        );


        User user = userRepository.findByEmail(authRequest.email()).orElseThrow(() ->
                new NotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);


    }

    @Transactional
    public AuthResponse editPassword(ChangePasswordRequest changePasswordRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("Пользователь не найден")
        );

        if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Неверный текущий пароль");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));


        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}
