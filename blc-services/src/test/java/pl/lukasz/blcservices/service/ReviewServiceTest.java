package pl.lukasz.blcservices.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcdata.entity.Book;
import pl.lukasz.blcdata.entity.Review;
import pl.lukasz.blcdata.repository.BookRepository;
import pl.lukasz.blcdata.repository.ReviewRepository;
import pl.lukasz.blcdata.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private BookRepository bookRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void shouldAddReview() {
        String username = "lukasz";
        Long bookId = 100L;

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new AppUser()));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));

        reviewService.addReview(bookId, username, 5, "Super Książka!");

        verify(reviewRepository).save(any(Review.class));
    }
}