package at.fhv.team3.domain.dto;

import java.util.HashMap;

/**
 * Created by David on 10/30/2017.
 */
public class CustomerDTO extends DTO{

    private int _customerId;
    private String _firstName;
    private String _lastName;
    private boolean _subscription;
    private String _email;
    private String _phoneNumber;

    public CustomerDTO(){

    }

    public CustomerDTO(int id, String firstName, String lastname, Boolean subscription, String email, String phoneNumber){
        _customerId = id;
        _firstName = firstName;
        _lastName = lastname;
        _subscription = subscription;
        _email = email;
        _phoneNumber = phoneNumber;
    }

    public void setCustomerId(int id){
        _customerId = id;
    }

    public int getCustomerId(){
        return _customerId;
    }

    public void setFirstName(String firstname){
        _firstName = firstname;
    }

    public String getFirstName(){
        return _firstName;
    }

    public void setLastName(String lastname){
        _lastName = lastname;
    }

    public String getLastName(){
        return _lastName;
    }

    public void setSubscription(boolean subscription){
        _subscription = subscription;
    }

    public boolean getSubscription(){
        return _subscription;
    }

    public void setEmail(String email){
        _email = email;
    }

    public String getEmail(){
        return _email;
    }

    public void setPhoneNumber(String phoneNumber){
        _phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return _phoneNumber;
    }

    public void setId(int id) {
        setCustomerId(id);
    }

    public int getId() {
        return getCustomerId();
    }

    public HashMap<String, String> getAllData() {
        String subscription;
        if(_subscription){
            subscription = "true";
        } else {
            subscription = "false";
        }
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_customerId);
        allData.put("firstname", _firstName);
        allData.put("lastname", _lastName);
        allData.put("subscription", subscription);
        allData.put("email", _email);
        allData.put("phonenumber", _phoneNumber);
        return allData;
    }

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("firstname").equals(_firstName) && data.get("lastname").equals(_lastName) && data.get("email").equals(_email) && data.get("phonenumber").equals(_phoneNumber)){
            return true;
        }
        return false;
    }
}
