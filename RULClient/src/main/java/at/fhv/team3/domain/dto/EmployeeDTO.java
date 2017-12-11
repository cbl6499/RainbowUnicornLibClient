package at.fhv.team3.domain.dto;

import java.util.HashMap;

/**
 * Created by David on 10/30/2017.
 */
public class EmployeeDTO extends DTO{

    private int _employeeId;
    private String _firstName;
    private String _lastName;
    private String _role;
    private String _userName;
    private boolean _loggedIn;

    public EmployeeDTO(){}

    public void setEmployeeId(int id){
        _employeeId = id;
    }

    public int getEmployeeId(){
        return _employeeId;
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

    public void setRole(String role){
        _role = role;
    }

    public String getRole(){
        return _role;
    }

    public void setUsername(String username){
        _userName = username;
    }

    public String getUsername(){
        return _userName;
    }

    public void setId(int id) {
        setEmployeeId(id);
    }

    public int getId() {
        return getEmployeeId();
    }

    public void setLoggedIn(boolean b){
        _loggedIn = b;
    }

    public boolean isLoggedIn(){
        return _loggedIn;
    }

    public HashMap<String, String> getAllData() {
        HashMap<String, String> allData = new HashMap<String, String>();
        allData.put("id", ""+_employeeId);
        allData.put("firstname", _firstName);
        allData.put("lastname", _lastName);
        allData.put("role", _role);
        allData.put("username", _userName);
        return allData;
    }

    public boolean equals(DTO dto) {
        HashMap<String, String> data = dto.getAllData();
        if(data.get("firstname").equals(_firstName) && data.get("lastname").equals(_lastName) && data.get("role").equals(_role) && data.get("username").equals(_userName)){
            return true;
        }
        return false;
    }

    public String toString() {
        HashMap<String, String> map = getAllData();
        StringBuilder sb = new StringBuilder();
        sb.append(map.get("id") + " ");
        sb.append(map.get("firstname") + " ");
        sb.append(map.get("lastname") + " ");
        sb.append(map.get("role") + " ");
        sb.append(map.get("username"));
        return sb.toString();
    }
}
