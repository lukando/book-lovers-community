package pl.lukasz.blcservices.dto;

public record LibraryStats(
        int bookCount,
        int authorCount,
        int reviewCount,
        double averageRating
) {}