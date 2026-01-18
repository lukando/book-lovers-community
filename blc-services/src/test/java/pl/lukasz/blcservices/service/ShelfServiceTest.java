package pl.lukasz.blcservices.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcdata.entity.Shelf;
import pl.lukasz.blcdata.repository.ShelfRepository;
import pl.lukasz.blcdata.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShelfServiceTest {

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShelfService shelfService;

    @Test
    void shouldCreateDefaultShelvesForUser() {
        AppUser user = new AppUser();
        user.setUsername("testuser");

        shelfService.createDefaultShelves(user);

        verify(shelfRepository, times(3)).save(any(Shelf.class));
    }
}