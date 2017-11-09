package at.fhv.team3.domain;

import at.fhv.team3.domain.dto.DTO;

import java.util.HashMap;

/**
 * Created by ClemensB on 05.11.17.
 */
public class Customer {
    private int _customerId;
    private String _firstName;
    private String _lastName;
    private boolean _subscription;
    private String _email;
    private String _phoneNumber;

    public Customer(){

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

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("firstname").equals(_firstName) && data.get("lastname").equals(_lastName) && data.get("email").equals(_email) && data.get("phonenumber").equals(_phoneNumber)){
            return true;
        }
        return false;
    }
}
