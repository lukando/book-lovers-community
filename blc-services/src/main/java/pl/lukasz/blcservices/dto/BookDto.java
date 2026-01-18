package pl.lukasz.blcservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookDto(
        @NotBlank(message = "Tytuł nie może być pusty")
        @Size(min = 2, max = 100, message = "Tytuł musi mieć od 2 do 100 znaków")
        String title,

        @NotBlank(message = "ISBN jest wymagany")
        String isbn,

        String description
) {}