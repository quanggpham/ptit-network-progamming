
package RMI;

import java.io.Serializable;


public class Book implements Serializable{
    private String id;
    private String title;
    private String author;
    private int yearPublished;
    private int pageCount;
    private String code;
    private static final long serialVersionUID = 20241123L;

    public Book(String id, String title, String author, int yearPublished, int pageCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.pageCount = pageCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
    
    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", author=" + author + ", yearPublished=" + yearPublished + ", pageCount=" + pageCount + ", code=" + code + '}';
    }
    
    
}
