package pl.lukasz.blcservices.dto;

import org.junit.jupiter.api.Test;
import pl.lukasz.blcdata.entity.*;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;

class PojoCoverageTest {

    @Test
    void shouldCoverEntitiesAndDtos() {

        Book book = Book.builder()
                .id(1L)
                .title("Java")
                .isbn("123")
                .description("Opis")
                .readCount(5)
                .authors(new HashSet<>())
                .build();

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Java");
        assertThat(book.toString()).isNotBlank();

        Author author = new Author(1L, "Bloch", new HashSet<>());
        author.setName("Joshua Bloch");
        assertThat(author.getName()).isEqualTo("Joshua Bloch");

        AppUser user = AppUser.builder()
                .id(1L)
                .username("lukasz")
                .password("pass")
                .email("a@a.pl")
                .build();
        assertThat(user.getEmail()).isEqualTo("a@a.pl");

        BookDto dto = new BookDto("Tytuł", "ISBN", "Opis");
        assertThat(dto.title()).isEqualTo("Tytuł");

        LibraryStats stats = new LibraryStats(10, 5, 2, 4.5);
        assertThat(stats.bookCount()).isEqualTo(10);
    }
}