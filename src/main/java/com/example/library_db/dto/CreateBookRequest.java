package com.example.library_db.dto;

import com.example.library_db.model.Author;
import com.example.library_db.model.Book;
import com.example.library_db.model.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    private String name;
    @NotNull
    private Genre genre;
    private Integer pages;
    @NotBlank
    private String autherName;
    private String autherCountry;
    @NotBlank
    private String email;

    public Book to() {
        return Book.builder()
                .name(this.name)
                .pages(this.pages)
                .genre(this.genre)
                .my_author(Author.builder().name(this.autherName).country(this.autherCountry).email(this.email).build())
                .build();
    }
}
