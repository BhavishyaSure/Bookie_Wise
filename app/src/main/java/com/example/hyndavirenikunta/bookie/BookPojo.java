package com.example.hyndavirenikunta.bookie;

/**
 * Created by hyndavirenikunta on 10/04/18.
 */

public class BookPojo {
    String bookName;
    String bookAuthor;
    String bookId;
    String bookCount;

    public BookPojo() {
    }

    public BookPojo(String bookName, String bookAuthor, String bookId, String bookCount) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookId = bookId;
        this.bookCount = bookCount;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    @Override
    public String toString() {
        return "BookPojo{" +
                "bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookCount='" + bookCount + '\'' +
                '}';
    }

}
