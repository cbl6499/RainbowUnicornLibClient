package at.fhv.team3.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by David on 11/15/2017.
 */

public class ValidationResult implements Serializable{

    private ArrayList<String> _errorMessages;

    public ValidationResult(){
        _errorMessages = new ArrayList<String>();
    }

    public ArrayList<String> getErrorMessages(){ return _errorMessages;}

    public void add(String s){
        _errorMessages.add(s);
    }

    public boolean hasErrors(){
        if(_errorMessages.isEmpty()){
            return false;
        }
        return true;
    }
}
