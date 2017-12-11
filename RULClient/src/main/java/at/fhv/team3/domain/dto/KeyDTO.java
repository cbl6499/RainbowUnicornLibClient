package at.fhv.team3.domain.dto;

import java.security.PublicKey;
import java.util.HashMap;

public class KeyDTO extends DTO{
    private PublicKey publickey = null;

    public void setId(int id) {

    }

    public int getId() {
        return 0;
    }

    public HashMap<String, String> getAllData() {
        return null;
    }

    public boolean equals(DTO dto) {
        return false;
    }

    public String toString() {
        return null;
    }

    public PublicKey getPublicKey() {
        return publickey;
    }

    public  void setPublicKey(PublicKey pk){
        publickey = pk;
    }

}
