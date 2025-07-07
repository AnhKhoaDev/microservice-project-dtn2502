package vti.dtn.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vti.dtn.auth_service.entity.User;
import vti.dtn.auth_service.repository.IUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
}
