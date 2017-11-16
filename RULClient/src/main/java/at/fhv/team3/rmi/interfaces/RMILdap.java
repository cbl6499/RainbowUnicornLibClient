package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.dto.EmployeeDTO;

import javax.naming.NamingException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by David on 11/15/2017.
 */
public interface RMILdap extends Remote {
    public EmployeeDTO authenticateUser(String name, String password) throws NamingException, RemoteException;
}
