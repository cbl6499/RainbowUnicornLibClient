package at.fhv.client.domain;

/**
 * Created by ClemensB on 05.11.17.
 */
public class ExternalLib {

    private int _libId;
    private String _name;
    private String _accountData;

    public ExternalLib(){

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
}
