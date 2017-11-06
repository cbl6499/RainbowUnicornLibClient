package at.fhv.team3.domain.dto.simple;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by David on 11/6/2017.
 */
public class SimpleBook {
    SimpleStringProperty id ;

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public SimpleStringProperty publisherProperty() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getIsbn() {
        return isbn.get();
    }

    public SimpleStringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getEdition() {
        return edition.get();
    }

    public SimpleStringProperty editionProperty() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition.set(edition);
    }

    public String getPictureURL() {
        return pictureURL.get();
    }

    public SimpleStringProperty pictureURLProperty() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL.set(pictureURL);
    }

    public String getShelfPos() {
        return shelfPos.get();
    }

    public SimpleStringProperty shelfPosProperty() {
        return shelfPos;
    }

    public void setShelfPos(String shelfPos) {
        this.shelfPos.set(shelfPos);
    }

    SimpleStringProperty title;
    SimpleStringProperty publisher;
    SimpleStringProperty author;
    SimpleStringProperty isbn;
    SimpleStringProperty edition;
    SimpleStringProperty pictureURL;
    SimpleStringProperty shelfPos;

    public SimpleBook(SimpleStringProperty id, SimpleStringProperty title, SimpleStringProperty publisher, SimpleStringProperty author, SimpleStringProperty isbn, SimpleStringProperty edition, SimpleStringProperty pictureURL, SimpleStringProperty shelfPos){
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;

        this.isbn = isbn;
        this.edition = edition;
        this.pictureURL = pictureURL;
        this.shelfPos = shelfPos;
    }

    public SimpleBook(SimpleStringProperty title, SimpleStringProperty author, SimpleStringProperty isbn){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}
