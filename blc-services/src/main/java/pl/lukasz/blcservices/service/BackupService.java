package pl.lukasz.blcservices.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lukasz.blcdata.entity.AppUser;
import pl.lukasz.blcdata.repository.UserRepository;
import pl.lukasz.blcservices.dto.UserProfileExport;
import pl.lukasz.blcservices.exception.ResourceNotFoundException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BackupService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public byte[] generateJsonBackup(String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<UserProfileExport.ReviewExportDto> reviewDtos = user.getReviews().stream()
                .map(r -> new UserProfileExport.ReviewExportDto(
                        r.getBook().getTitle(),
                        r.getRating(),
                        r.getContent(),
                        r.getCreatedAt().toString()
                ))
                .toList();

        UserProfileExport exportData = new UserProfileExport(
                user.getUsername(),
                user.getEmail(),
                reviewDtos.size(),
                reviewDtos
        );

        try {
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(exportData);
            return jsonString.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas generowania backupu", e);
        }
    }
}