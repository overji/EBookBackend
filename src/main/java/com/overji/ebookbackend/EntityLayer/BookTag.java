package com.overji.ebookbackend.EntityLayer;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name="book_tag")
public class BookTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private String name;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "name", name
        );
    }
}
