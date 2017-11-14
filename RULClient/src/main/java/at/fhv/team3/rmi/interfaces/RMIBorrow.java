package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.Customer;
import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.interfaces.Borrowable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by David on 11/8/2017.
 */
public interface RMIBorrow  extends Remote {

    public boolean handOut(DTO media, CustomerDTO customer) throws RemoteException;
    public boolean handIn(DTO media) throws RemoteException;
}
