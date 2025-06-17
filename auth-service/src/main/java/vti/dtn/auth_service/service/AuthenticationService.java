package vti.dtn.auth_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vti.dtn.auth_service.dto.request.LoginRequest;
import vti.dtn.auth_service.dto.request.RegisterRequest;
import vti.dtn.auth_service.dto.response.LoginResponse;
import vti.dtn.auth_service.dto.response.RegisterResponse;
import vti.dtn.auth_service.dto.response.VerifyTokenResponse;
import vti.dtn.auth_service.entity.User;
import vti.dtn.auth_service.entity.enums.Role;
import vti.dtn.auth_service.repository.IUserRepository;

import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final int TOKEN_INDEX = 7;
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String userName = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String role = registerRequest.getRole();
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();

        Optional<User> userByEmail = userRepository.findByEmail(email);
        Optional<User> userByUsername = userRepository.findByUsername(userName);

        if (userByEmail.isPresent() && userByUsername.isPresent()) {
            return RegisterResponse.builder()
                    .status(400)
                    .message("User already exists!")
                    .build();
        }

        User user = User.builder()
                .username(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.toEnum(role))
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .status(200)
                .message("User created successfully")
                .build();
    }

    //TODO: Implement login logic
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Optional<User> userByUsername = userRepository.findByUsername(username);

        if (userByUsername.isPresent()) {
            User user = userByUsername.get();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            return LoginResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Login successful")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userId(user.getId())
                    .build();
        }else {
            return LoginResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid credentials")
                    .build();
        }
    }

    public LoginResponse refreshToken(String authHeader) {
        if(!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return LoginResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid token")
                    .build();
        }

        String refreshToken = authHeader.substring(TOKEN_INDEX);
        if (!jwtService.validateToken(refreshToken)) {
            return LoginResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid refresh token")
                    .build();
        }

        String username = jwtService.extractUsername(refreshToken);

        Optional<User> userFoundByUserName = userRepository.findByUsername(username);
        if (userFoundByUserName.isEmpty()) {
            return LoginResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Token revoked")
                    .build();
        }

        User user = userFoundByUserName.get();
        String accessToken = jwtService.generateAccessToken(user);
        String newAccessToken = jwtService.generateRefreshToken(user);

        user.setAccessToken(accessToken);
        user.setRefreshToken(newAccessToken);
        userRepository.save(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .message("Refresh token successfully")
                .status(HttpStatus.OK.value())
                .build();
    }

    public VerifyTokenResponse verifyToken(String authHeader) {
        log.info("verifyToken|authHeader={} ", authHeader);

        if(!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.error("verifyToken|Authorization header is missing or invalid");
            return VerifyTokenResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid refresh token")
                    .build();
        }

        String token = authHeader.substring(TOKEN_INDEX);
        if (!jwtService.validateToken(token)) {
            log.error("verifyToken|Invalid refresh token");
            return VerifyTokenResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid refresh token")
                    .build();
        }

        String username = jwtService.extractUsername(token);

        Optional<User> userFoundByUserName = userRepository.findByUsername(username);
        if (userFoundByUserName.isEmpty()) {
            log.error("verifyToken|User not found for username: {}", username);
            return VerifyTokenResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Token revoked")
                    .build();
        }

        String role = userFoundByUserName.get().getRole().toString();
        String userInfoStr = username + ";" + role;
        String xUserToken = Base64.getEncoder().encodeToString(userInfoStr.getBytes());

        log.info("verifyToken|X-User-Token: {}", xUserToken);
        return VerifyTokenResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .xUserToken(xUserToken)
                .build();
    }
}
