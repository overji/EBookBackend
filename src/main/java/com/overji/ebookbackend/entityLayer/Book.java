package com.overji.ebookbackend.entityLayer;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private String title = "";

    @Lob
    @Column
    private String author = "";

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Long price = 0L;

    @Column
    private Long stock = 10000L;

    @Column
    private String isbn = "";

    @Column
    @Lob
    private String cover = "";

    @Column
    private Long sales = 0L;

    @Column
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

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
        if (tags != null) {
            tags.remove(tag);
        }
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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("author", author);
        map.put("description", description);
        map.put("price", price);
        map.put("cover", cover);
        map.put("sales", sales);
        map.put("tags", tags.stream().map(BookTag::toMap).toList());
        map.put("isbn", isbn);
        map.put("stock", stock);
        map.put("isDeleted", isDeleted);
        return map;
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
