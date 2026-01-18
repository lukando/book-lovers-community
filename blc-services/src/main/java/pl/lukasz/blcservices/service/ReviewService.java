package pl.lukasz.blcservices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcdata.entity.Book;
import pl.lukasz.blcdata.entity.Review;
import pl.lukasz.blcdata.repository.BookRepository;
import pl.lukasz.blcdata.repository.ReviewRepository;
import pl.lukasz.blcdata.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addReview(Long bookId, String username, int rating, String content) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono książki!"));

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika!"));

        Review review = Review.builder()
                .book(book)
                .user(user)
                .rating(rating)
                .content(content)
                .build();

        reviewRepository.save(review);
    }
}