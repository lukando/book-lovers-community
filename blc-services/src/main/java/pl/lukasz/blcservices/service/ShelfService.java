package pl.lukasz.blcservices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcdata.entity.Book;
import pl.lukasz.blcdata.entity.Shelf;
import pl.lukasz.blcdata.repository.BookRepository;
import pl.lukasz.blcdata.repository.ShelfRepository;
import pl.lukasz.blcdata.repository.UserRepository;
import pl.lukasz.blcservices.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelfService {

    private final ShelfRepository shelfRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void createDefaultShelves(AppUser user) {
        createShelf(user, "Chcę przeczytać");
        createShelf(user, "Teraz czytam");
        createShelf(user, "Przeczytane");
    }

    private void createShelf(AppUser user, String name) {
        Shelf shelf = Shelf.builder().name(name).user(user).build();
        shelfRepository.save(shelf);
    }

    public List<Shelf> getUserShelves(String username) {
        return shelfRepository.findAllByUser_Username(username);
    }

    @Transactional
    public void addBookToShelf(Long shelfId, Long bookId, String username) {
        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono półki"));

        if (!shelf.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Nie twoja półka!");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono książki"));

        shelf.getBooks().add(book);
        shelfRepository.save(shelf);
    }
}