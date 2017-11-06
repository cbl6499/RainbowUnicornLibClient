package at.fhv.team3.domain.dto;

import java.util.HashMap;

/**
 * Created by David on 10/30/2017.
 */
public class ExternalLibDTO extends DTO{

    private int _libId;
    private String _name;
    private String _accountData;

    public ExternalLibDTO(){

    }

    public void setLibId(int id){
        _libId = id;
    }

    public int getLibId(){
        return _libId;
    }

    public void setName(String name){
        _name = name;
    }

    public String getName(){
        return _name;
    }

    public void setAccountData(String accountData){
        _accountData = accountData;
    }

    public String getAccountData(){
        return _accountData;
    }

    public void setId(int id) {
        setLibId(id);
    }

    public int getId() {
        return getLibId();
    }

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_libId);
        allData.put("name", _name);
        allData.put("accountData", _accountData);
        return allData;
    }

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("name").equals(_name) && data.get("accountData").equals(_accountData)){
            return true;
        }
        return false;
    }
}
