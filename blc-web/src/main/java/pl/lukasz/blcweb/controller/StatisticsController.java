package pl.lukasz.blcweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lukasz.blcservices.dto.LibraryStats;
import pl.lukasz.blcservices.service.StatisticsService;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<LibraryStats> getLibraryStatistics() {
        return ResponseEntity.ok(statisticsService.getStats());
    }
}