package pl.lukasz.blcservices.dto;

import java.util.List;

public record UserProfileExport(
        String username,
        String email,
        int totalReviews,
        List<ReviewExportDto> reviews
) {
    public record ReviewExportDto(
            String bookTitle,
            int rating,
            String content,
            String date
    ) {}
}