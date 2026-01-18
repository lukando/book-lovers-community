package pl.lukasz.blcservices.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcdata.entity.Role;
import pl.lukasz.blcdata.repository.RoleRepository;
import pl.lukasz.blcdata.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private ShelfService shelfService;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterNewUser() {
        String username = "lukasz";
        String password = "secretPassword";
        String email = "lukasz@test.pl";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role(1L, "ROLE_USER")));
        when(passwordEncoder.encode(password)).thenReturn("hashed_secret");

        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.registerUser(username, password, email);

        verify(userRepository).save(any(AppUser.class));
        verify(shelfService).createDefaultShelves(any(AppUser.class));
    }

    @Test
    void shouldUpdateUserProfile() {
        String username = "lukasz";
        AppUser existingUser = new AppUser();
        existingUser.setUsername(username);
        existingUser.setBio("Old Bio");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        userService.updateProfile(username, "New Bio", "avatar.png");

        verify(userRepository).save(existingUser);
        assert existingUser.getBio().equals("New Bio");
    }

    @Test
    void shouldDeleteAccount() {
        String username = "lukasz";
        AppUser existingUser = new AppUser();
        existingUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        userService.deleteAccount(username);

        verify(userRepository).delete(existingUser);
    }
}