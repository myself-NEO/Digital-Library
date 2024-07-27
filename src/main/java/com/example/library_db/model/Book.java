package com.example.library_db.model;

import com.example.library_db.model.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Enumerated(value = EnumType.STRING)

    private Genre genre;

    private Integer pages;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")
    private Auther my_auther;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("bookList")
    private Student my_student;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @OneToMany(mappedBy = "my_book")
    @JsonIgnoreProperties("my_book")
    List<Transaction> transactionList;
}
