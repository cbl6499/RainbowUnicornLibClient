package at.fhv.team3.domain.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by David on 10/31/2017.
 */
public abstract class DTO implements Serializable{

    protected int _id;
    protected boolean _available = true;

    public abstract void setId(int id);

    public abstract int getId();

    public abstract HashMap<String, String> getAllData();

    public abstract boolean equals(DTO dto);

    public void setAvailability(boolean b){
        _available = b;
    }

    public boolean isAvailable(){
        return _available;
    }
}
