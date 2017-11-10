package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.interfaces.Borrowable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by David on 11/8/2017.
 */
public interface RMIBorrow  extends Remote {

    public void handOut(Borrowable media, CustomerDTO customer) throws RemoteException;
    public void handIn(Borrowable media) throws RemoteException;
}
