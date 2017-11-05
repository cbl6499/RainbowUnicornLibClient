package at.fhv.client.domain;

/**
 * Created by ClemensB on 05.11.17.
 */
public class Magazine {

    private int _magazineId;
    private String _title;
    private String _edition;
    private String _publisher;
    private String _pictureURL;
    private String _shelfPos;

    public Magazine(int id, String title, String edition, String publisher, String pictureURL, String shelfPos){
        _magazineId = id;
        _title = title;
        _edition = edition;
        _publisher = publisher;
        _pictureURL = pictureURL;
        _shelfPos = shelfPos;
    }

    public void setMagazineId(int id){
        _magazineId = id;
    }

    public int getMagazineId(){
        return _magazineId;
    }

    public void setTitle(String title){
        _title = title;
    }

    public String getTitle(){
        return _title;
    }

    public void setEdition(String edition){
        _edition = edition;
    }

    public String getEdition(){
        return _edition;
    }

    public void setPublisher(String publisher){
        _publisher = publisher;
    }

    public String getPublisher(){
        return _publisher;
    }

    public void setPictureURL(String pictureURL){
        _pictureURL = pictureURL;
    }

    public String getPictureURL(){
        return _pictureURL;
    }

    public void setShelfPos(String shelfPos){
        _shelfPos = shelfPos;
    }

    public String getShelfPos(){
        return _shelfPos;
    }

    public void setId(int id) {
        setMagazineId(id);
    }

    public int getId() {
        return getMagazineId();
    }
}
