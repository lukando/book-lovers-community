package pl.lukasz.blcservices.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryDoc(
        String title,

        @JsonProperty("author_name")
        List<String> authorNames,

        @JsonProperty("isbn")
        List<String> isbnList,

        @JsonProperty("first_sentence")
        List<String> firstSentence
) {}