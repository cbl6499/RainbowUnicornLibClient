package at.fhv.team3.domain.dto;

import java.util.HashMap;

/**
 * Created by David on 10/30/2017.
 */
public class DvdDTO extends DTO{

    private int _dvdId;
    private String _title;
    private String _regisseur;
    private String _pictureURL;
    private String _shelfPos;
    private boolean _available;
    private String _status;

    public DvdDTO(int id, String title, String regisseur, String pictureURL, String shelfPos){
        _dvdId = id;
        _title = title;
        _regisseur = regisseur;
        _pictureURL = pictureURL;
        _shelfPos = shelfPos;
    }

    public DvdDTO(int id, String title, String regisseur, String pictureURL, String shelfPos, String status){
        _dvdId = id;
        _title = title;
        _regisseur = regisseur;
        _pictureURL = pictureURL;
        _shelfPos = shelfPos;
        _status = status;
    }

    public void setDvdId(int dvdId){
        _dvdId = dvdId;
    }

    public int getDvdId(){
        return _dvdId;
    }

    public void setTitle(String title){
        _title = title;
    }

    public String getTitle(){
        return _title;
    }

    public void setRegisseur(String regisseur){
        _regisseur = regisseur;
    }

    public String getRegisseur(){
        return _regisseur;
    }

    public void setPictureURL(String pictureURL){
        _pictureURL = pictureURL;
    }

    public String getPictureURL(){ return _pictureURL;}

    public void setShelfPos(String shelfPos){
        _shelfPos = shelfPos;
    }

    public String getShelfPos(){
        return _shelfPos;
    }

    public void setAvailable(boolean _available){
        this._available = _available;
        if(_available){
            _status = "Vorhanden";
        } else {
            _status = "Nicht vorhanden";
        }
    }

    public boolean isAvailable(){ return _available;}

    public void setStatus(String available){_status= _status;}

    public String getStatus(){ return _status;}

    public void setId(int id) {
        setDvdId(id);
    }

    public int getId() {
        return getDvdId();
    }

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_dvdId);
        allData.put("title", _title);
        allData.put("regisseur", _regisseur);
        allData.put("pictureURL", _pictureURL);
        allData.put("shelfPos", _shelfPos);
        allData.put("available", _status);
        return allData;
    }

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("title").equals(_title) && data.get("regisseur").equals(_regisseur)){
            return true;
        }
        return false;
    }
}
