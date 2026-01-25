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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Long> getShelfStatistics(Long bookId) {
        List<Object[]> results = shelfRepository.countShelvesByBookId(bookId);

        Map<String, Long> stats = new HashMap<>();
        stats.put("Chcę przeczytać", 0L);
        stats.put("Teraz czytam", 0L);
        stats.put("Przeczytane", 0L);

        for (Object[] row : results) {
            String name = (String) row[0];
            Long count = (Long) row[1];
            if (stats.containsKey(name)) {
                stats.put(name, count);
            }
        }
        return stats;
    }

    public List<Shelf> getUserShelves(String username) {
        return shelfRepository.findAllByUser_Username(username);
    }

    @Transactional
    public void addBookToShelf(Long shelfId, Long bookId) {
        Shelf targetShelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new RuntimeException("Półka nie istnieje"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Książka nie istnieje"));

        Long userId = targetShelf.getUser().getId();

        List<Shelf> userShelves = shelfRepository.findAllByUserId(userId);

        for (Shelf shelf : userShelves) {
            if (shelf.getBooks().contains(book)) {
                shelf.getBooks().remove(book);
                shelfRepository.save(shelf);
            }
        }
        targetShelf.getBooks().add(book);
        shelfRepository.save(targetShelf);
    }
}