package com.example.qltv.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BookModel extends RealmObject {

    @PrimaryKey
    public String id;
    public String name;
    public String author;
    public String kind;
    public int amount;
    public int currentAmount;
    public boolean status;
    public int coverBook;
    public int thumballBook;
    public String description;
    public boolean trending;
    public boolean visible;

    public BookModel() {
    }

    public BookModel(String name, int thumballBook) {
        this.name = name;
        this.thumballBook = thumballBook;
    }

    public BookModel(String id, String name, String author, String kind, int amount, int currentAmount, boolean status, int coverBook, int thumballBook, String description, boolean trending, boolean visible) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.kind = kind;
        this.amount = amount;
        this.currentAmount = currentAmount;
        this.status = status;
        this.coverBook = coverBook;
        this.thumballBook = thumballBook;
        this.description = description;
        this.trending = trending;
        this.visible = visible;
    }

    public boolean isTrending() {
        return trending;
    }

    public void setTrending(boolean trending) {
        this.trending = trending;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getCoverBook() {
        return coverBook;
    }

    public void setCoverBook(int coverBook) {
        this.coverBook = coverBook;
    }

    public int getThumballBook() {
        return thumballBook;
    }

    public void setThumballBook(int thumballBook) {
        this.thumballBook = thumballBook;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
