package pl.lukasz.blcservices.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lukasz.blcdata.entity.Book;
import pl.lukasz.blcdata.repository.BookRepository;
import pl.lukasz.blcservices.dto.BookDto;
import pl.lukasz.blcservices.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono książki o ID: " + id));
    }

    public List<Book> searchBooks(String query) {
        if (query == null || query.isBlank()) {
            return getAllBooks();
        }
        return bookRepository.searchBooks(query);
    }


    @Transactional
    public Book createBook(BookDto dto) {
        Book book = Book.builder()
                .title(dto.title())
                .isbn(dto.isbn())
                .description(dto.description())
                .build();
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, BookDto dto) {
        Book book = getBookById(id);
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setDescription(dto.description());
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nie można usunąć. Książka nie istnieje.");
        }
        bookRepository.deleteById(id);
    }
}