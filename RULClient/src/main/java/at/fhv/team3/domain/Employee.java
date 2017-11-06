package at.fhv.team3.domain;

/**
 * Created by ClemensB on 05.11.17.
 */
public class Employee {

    private int _employeeId;
    private String _firstName;
    private String _lastName;
    private String _role;
    private String _userName;

    public Employee(){
    }

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
}
