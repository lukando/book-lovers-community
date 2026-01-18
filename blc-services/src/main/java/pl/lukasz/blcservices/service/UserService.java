package pl.lukasz.blcservices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcdata.entity.Role;
import pl.lukasz.blcdata.repository.RoleRepository;
import pl.lukasz.blcdata.repository.UserRepository;
import pl.lukasz.blcservices.exception.ResourceNotFoundException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShelfService shelfService;

    @Transactional
    public void registerUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Użytkownik o takiej nazwie już istnieje!");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

        AppUser user = AppUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(userRole))
                .build();

        AppUser savedUser = userRepository.save(user);
        shelfService.createDefaultShelves(savedUser);
    }

    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Użytkownik nie istnieje"));
    }

    @Transactional
    public void updateProfile(String username, String bio, String avatarUrl) {
        AppUser user = getUserByUsername(username);
        user.setBio(bio);
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(String username) {
        AppUser user = getUserByUsername(username);
        userRepository.delete(user);
    }
}