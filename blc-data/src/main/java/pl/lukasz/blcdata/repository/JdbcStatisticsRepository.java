package pl.lukasz.blcdata.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcStatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public int countBooks() {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM books", Integer.class);
        return result != null ? result : 0;
    }

    public int countAuthors() {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM authors", Integer.class);
        return result != null ? result : 0;
    }

    public int countReviews() {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reviews", Integer.class);
        return result != null ? result : 0;
    }

    public double getAverageRating() {
        Double result = jdbcTemplate.queryForObject("SELECT COALESCE(AVG(rating), 0.0) FROM reviews", Double.class);
        return result != null ? result : 0.0;
    }
}