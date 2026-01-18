package pl.lukasz.blcservices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lukasz.blcdata.repository.JdbcStatisticsRepository;
import pl.lukasz.blcservices.dto.LibraryStats;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final JdbcStatisticsRepository statsRepo;

    public LibraryStats getStats() {
        return new LibraryStats(
                statsRepo.countBooks(),
                statsRepo.countAuthors(),
                statsRepo.countReviews(),
                statsRepo.getAverageRating()
        );
    }
}