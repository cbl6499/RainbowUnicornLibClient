package at.fhv.team3.application;

import at.fhv.team3.domain.dto.EmployeeDTO;

public class LoggedInUser {
    private static LoggedInUser _loggedInUser;
    private EmployeeDTO _user;


    private LoggedInUser() {
    }

    public static LoggedInUser getInstance() {
        if (_loggedInUser == null) {
            _loggedInUser = new LoggedInUser();
        }
        return _loggedInUser;
    }

    public void setUser(EmployeeDTO user) {
        _user = user;
    }

    public EmployeeDTO getUser() {
        return this._user;
    }

    public Boolean isLoggedIn() {
        if (_user != null) {
            return true;
        }
        return false;
    }

}
