package at.fhv.client.domain;

import java.util.HashMap;

/**
 * Created by ClemensB on 05.11.17.
 */
public class Book extends DTO{

    private int _bookId;
    private String _title;
    private String _publisher;
    private String _author;
    private String _isbn;
    private String _edition;
    private String _pictureURL;
    private String _shelfPos;

    public Book(int id, String title, String publisher, String author, String isbn, String edition, String pictureURL, String shelfPos){
        _bookId = id;
        _title = title;
        _publisher = publisher;
        _author = author;
        _isbn = isbn;
        _edition = edition;
        _pictureURL = pictureURL;
        _shelfPos = shelfPos;
    }

    public void setBookId(int id){
        _bookId = id;
    }

    public int getBookId(){
        return _bookId;
    }

    public void setTitle(String title){
        _title = title;
    }

    public String getTitle(){
        return _title;
    }

    public void setPublisher(String publisher){
        _publisher = publisher;
    }

    public String getPublisher(){
        return _publisher;
    }

    public void setAuthor(String author){
        _author = author;
    }

    public String getAuthor(){
        return _author;
    }

    public void setIsbn(String isbn){
        _isbn = isbn;
    }

    public String getIsbn(){
        return _isbn;
    }

    public void setEdition(String edition){
        _edition = edition;
    }

    public void setPictureURL(String pictureURL){
        _pictureURL = pictureURL;
    }

    public void setShelfPos(String shelfPos){
        _shelfPos = shelfPos;
    }

    public String getShelfPos(){
        return _shelfPos;
    }

    public void setId(int id) {
        setBookId(id);
    }

    public int getId() {
        return getBookId();
    }

    @Override
    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_bookId);
        allData.put("title", _title);
        allData.put("publisher", _publisher);
        allData.put("author", _author);
        allData.put("isbn", _isbn);
        allData.put("edition", _edition);
        allData.put("pictureURL", _pictureURL);
        allData.put("shelfPos", _shelfPos);

        return allData;
    }

    @Override
    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("title").equals(_title) && data.get("publisher").equals(_publisher) && data.get("author").equals(_author) && data.get("isbn").equals(_isbn) && data.get("edition").equals(_edition) && data.get("pictureURL").equals(_pictureURL) && data.get("shelfPos").equals(_shelfPos)){
            return true;
        }
        return false;
    }
}
