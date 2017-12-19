package at.fhv.team3.applicationbean.interfaces;

import at.fhv.team3.domain.dto.EmployeeDTO;
import at.fhv.team3.domain.dto.KeyDTO;

import javax.ejb.Remote;
import javax.naming.NamingException;
import java.io.Serializable;

@Remote
public interface RemoteLdapConnectionFace extends Serializable {

    static final long serialVersionUID = 6L;

    public EmployeeDTO authenticateUser(String name, String password) throws NamingException;

    public KeyDTO getPublicKey();
}
