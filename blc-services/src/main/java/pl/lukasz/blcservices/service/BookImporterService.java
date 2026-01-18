package pl.lukasz.blcservices.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.lukasz.blcdata.entity.Author;
import pl.lukasz.blcdata.entity.Book;
import pl.lukasz.blcdata.repository.AuthorRepository;
import pl.lukasz.blcdata.repository.BookRepository;
import pl.lukasz.blcservices.dto.OpenLibraryResponse;
import pl.lukasz.blcservices.dto.OpenLibraryDoc;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookImporterService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // Pobieramy książki o Javie
    private static final String API_URL = "https://openlibrary.org/search.json?q=java&limit=50";

    @Scheduled(initialDelay = 5000, fixedRate = 3600000)
    @Transactional
    public void importBooks() {
        log.info("🚀 Rozpoczynam import książek z OpenLibrary...");

        try {
            OpenLibraryResponse response = restTemplate.getForObject(API_URL, OpenLibraryResponse.class);

            if (response == null || response.docs() == null) {
                log.error("❌ Brak danych z API OpenLibrary (odpowiedź jest null).");
                return;
            }

            log.info("✅ API zwróciło {} wyników. Rozpoczynam przetwarzanie...", response.docs().size());

            // Pętla przetwarzająca każdą książkę
            response.docs().forEach(doc -> {
                String title = doc.title();

                // TU JEST ZMIANA: Jak nie ma ISBN, to generujemy sztuczny, żeby zapisać książkę za wszelką cenę
                String isbn;
                if (doc.isbnList() != null && !doc.isbnList().isEmpty()) {
                    isbn = doc.isbnList().get(0);
                } else {
                    isbn = "NO-ISBN-" + UUID.randomUUID().toString(); // Sztuczny identyfikator
                }

                log.info("Sprawdzam książkę: '{}', ISBN: {}", title, isbn);

                // Zapisujemy, jeśli jest tytuł (ISBN mamy już ogarnięty, nawet sztuczny)
                if (title != null) {
                    saveBookIfNotExists(doc, isbn);
                }
            });

            log.info("🏁 Zakończono import książek.");

        } catch (Exception e) {
            log.error("❌ Błąd krytyczny podczas importu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveBookIfNotExists(OpenLibraryDoc doc, String isbn) {
        if (bookRepository.findByIsbn(isbn).isPresent()) {
            log.info("ℹ️ Książka już istnieje w bazie: {}", doc.title());
            return;
        }

        // 2. Obsługa Autorów
        Set<Author> authors = new HashSet<>();
        if (doc.authorNames() != null) {
            for (String authorName : doc.authorNames()) {
                Author author = authorRepository.findByName(authorName)
                        .orElseGet(() -> authorRepository.save(
                                Author.builder().name(authorName).build()
                        ));
                authors.add(author);
            }
        }

        String description = (doc.firstSentence() != null && !doc.firstSentence().isEmpty())
                ? doc.firstSentence().get(0)
                : "Brak opisu w API OpenLibrary.";

        Book book = Book.builder()
                .title(doc.title())
                .isbn(isbn)
                .description(description)
                .authors(authors)
                .readCount(0)
                .build();

        bookRepository.save(book);
        log.info("💾 ZAPISANO NOWĄ KSIĄŻKĘ: {}", book.getTitle());
    }
}