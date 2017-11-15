package at.fhv.team3.domain.dto;

import java.util.ArrayList;

/**
 * Created by David on 11/15/2017.
 */
public class ValidationResult {

    public ArrayList<String> _errorMessages;

    public ValidationResult(){
        _errorMessages = new ArrayList<String>();
    }

    public ArrayList<String> getErrorMessages(){ return _errorMessages;}

    public boolean hasErrors(){
        if(_errorMessages.isEmpty()){
            return false;
        }
        return true;
    }
}
