package uz.salikhdev.shop_backend.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.salikhdev.shop_backend.dto.request.UserLoginRequest;
import uz.salikhdev.shop_backend.dto.request.UserRegisterRequest;
import uz.salikhdev.shop_backend.entity.User;
import uz.salikhdev.shop_backend.exception.AlreadyExistsException;
import uz.salikhdev.shop_backend.exception.NotFoundException;
import uz.salikhdev.shop_backend.repository.UserRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate verificationCodes;
    private final EmailService emailService;
    private final Random random;

    public void register(UserRegisterRequest request) {

        Optional<@NonNull User> optUser = userRepository.findByEmail(request.email());

        if (optUser.isPresent()) {
            User user = optUser.get();
            if (user.getStatus().equals(User.Status.UNVERIFIED)) {
                sendVerificationCode(request.email());
                return;
            } else {
                throw new AlreadyExistsException("User with email " + request.email() + " already exists");
            }
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .status(User.Status.UNVERIFIED)
                .build();

        sendVerificationCode(request.email());
        userRepository.save(user);
    }

    private void sendVerificationCode(String email) {

        Integer genCode = random.nextInt(1000, 9999);
        verificationCodes.opsForValue().set(email, genCode.toString());
        emailService.sendVerificationCode(email, genCode);
    }

    public void verification(String email, Integer code) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));

        String otp = verificationCodes.opsForValue().get(email);

        if (otp == null) {
            throw new NotFoundException("Verification code not found for email " + email);
        }

        if (!otp.equals(code.toString())) {
            throw new BadCredentialsException("Invalid verification code");
        }

        user.setStatus(User.Status.ACTIVE);
        userRepository.save(user);
        verificationCodes.delete(email);
    }

    public String login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("User with email " + request.email() + " not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (user.getStatus().equals(User.Status.UNVERIFIED)) {
            throw new NotFoundException("User with email " + request.email() + " is not verified");
        }

        if (user.getStatus().equals(User.Status.BLOCKED)) {
            throw new NotFoundException("User with email " + request.email() + " is blocked");
        }

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userRepository.save(user);

        return token;
    }

    public void resendVerificationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));

        if (!user.getStatus().equals(User.Status.UNVERIFIED)) {
            throw new NotFoundException("User with email " + email + " is already verified");
        }

        sendVerificationCode(email);
    }
}
