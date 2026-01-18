package pl.lukasz.blcweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lukasz.blcdata.entity.Book;
import pl.lukasz.blcdata.entity.Shelf;
import pl.lukasz.blcservices.service.BookService;
import pl.lukasz.blcservices.service.ReviewService;
import pl.lukasz.blcservices.service.ShelfService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookWebController {

    private final BookService bookService;
    private final ReviewService reviewService;
    private final ShelfService shelfService;

    @GetMapping("/")
    public String home() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String getAllBooksPage(@RequestParam(required = false) String query, Model model) {
        List<Book> books;
        if (query != null && !query.isBlank()) {
            books = bookService.searchBooks(query);
            model.addAttribute("query", query);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/books/{id}")
    public String getBookDetails(@PathVariable Long id, Model model, Principal principal) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);

        if (principal != null) {
            List<Shelf> userShelves = shelfService.getUserShelves(principal.getName());
            model.addAttribute("shelves", userShelves);
        }

        return "book-details";
    }

    @PostMapping("/shelves/add")
    public String addBookToShelf(@RequestParam Long bookId,
                                 @RequestParam Long shelfId,
                                 Principal principal) {
        if (principal != null) {
            shelfService.addBookToShelf(shelfId, bookId, principal.getName());
        }
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/books/{id}/review")
    public String showReviewForm(@PathVariable Long id, Model model) {
        model.addAttribute("bookId", id);
        return "review-form";
    }

    @PostMapping("/books/{id}/review")
    public String addReview(@PathVariable Long id,
                            @RequestParam int rating,
                            @RequestParam String content,
                            Principal principal) {
        String username = principal.getName();
        reviewService.addReview(id, username, rating, content);
        return "redirect:/books/" + id;
    }

    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}