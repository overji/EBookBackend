package com.overji.ebookbackend.EntityLayer;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private String title;

    @Lob
    @Column
    private String author;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Long price;

    @Column
    @Lob
    private String cover;

    @Column
    private Long sales;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartItems = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Long getSales() {
        return sales;
    }

    public void setTags(List<BookTag> tags) {
        this.tags = tags;
    }

    public List<BookTag> getTags() {
        return tags;
    }

    public void addTag(BookTag tag) {
        tags.add(tag);
        tag.setBook(this);
    }

    public void removeTag(BookTag tag) {
        tags.remove(tag);
        tag.setBook(null);
    }

    public void setTagList(List<BookTag> tags) {
        this.tags = tags;
    }

    public List<BookTag> getTagList() {
        return tags;
    }

    public void setTag(BookTag tag) {
        this.tags.add(tag);
        tag.setBook(this);
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "title", title,
                "author", author,
                "description", description,
                "price", price,
                "cover", cover,
                "sales", sales,
                "tags", tags.stream().map(BookTag::toMap).toList()
        );
    }

    public Long getCommentId() {
        return (long) this.comments.size();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setBook(null);
    }
}
