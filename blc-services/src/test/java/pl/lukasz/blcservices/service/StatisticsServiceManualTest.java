package pl.lukasz.blcservices.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lukasz.blcdata.repository.JdbcStatisticsRepository;
import pl.lukasz.blcservices.dto.LibraryStats;

class JdbcStatisticsRepositoryStub extends JdbcStatisticsRepository {

    public JdbcStatisticsRepositoryStub() {
        super(null);
    }

    @Override
    public int countBooks() {
        return 100;
    }

    @Override
    public int countAuthors() {
        return 50;
    }

    @Override
    public int countReviews() {
        return 20;
    }

    @Override
    public double getAverageRating() {
        return 9.5;
    }
}

class StatisticsServiceManualTest {

    @Test
    void shouldReturnStatsFromStub() {
        JdbcStatisticsRepositoryStub stubRepo = new JdbcStatisticsRepositoryStub();

        StatisticsService service = new StatisticsService(stubRepo);

        LibraryStats stats = service.getStats();

        Assertions.assertEquals(100, stats.bookCount());
        Assertions.assertEquals(50, stats.authorCount());
        Assertions.assertEquals(9.5, stats.averageRating());

        System.out.println("✅ Test Manualny (Stub) zaliczony! Statystyki się zgadzają.");
    }
}