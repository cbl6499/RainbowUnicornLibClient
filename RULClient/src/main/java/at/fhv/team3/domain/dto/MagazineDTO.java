package at.fhv.team3.domain.dto;

import java.util.HashMap;

/**
 * Created by David on 10/30/2017.
 */
public class MagazineDTO extends DTO{

    private int _magazineId;
    private String _title;
    private String _edition;
    private String _publisher;
    private String _pictureURL;
    private String _shelfPos;
    private boolean _available;

    public MagazineDTO(int id, String title, String edition, String publisher, String pictureURL, String shelfPos){
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

    public void setAvailable(boolean available){
        _available = available;
    }

    public boolean isAvailable(){ return _available;}

    public void setId(int id) {
        setMagazineId(id);
    }

    public int getId() {
        return getMagazineId();
    }

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_magazineId);
        allData.put("title", _title);
        allData.put("edition", _edition);
        allData.put("publisher", _publisher);
        allData.put("pictureURL", _pictureURL);
        allData.put("shelfPos", _shelfPos);
        return allData;
    }

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("title").equals(_title) && data.get("edition").equals(_edition) && data.get("publisher").equals(_publisher) && data.get("pictureURL").equals(_pictureURL) && data.get("shelfPos").equals(_shelfPos)){
            return true;
        }
        return false;
    }
}
