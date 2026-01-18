package pl.lukasz.blcservices.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryResponse(
        List<OpenLibraryDoc> docs
) {}