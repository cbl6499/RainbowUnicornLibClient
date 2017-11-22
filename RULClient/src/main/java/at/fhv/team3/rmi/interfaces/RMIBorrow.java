package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.ValidationResult;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by David on 11/8/2017.
 */
public interface RMIBorrow  extends Remote {

    public ValidationResult handOut(DTO media, CustomerDTO customer) throws RemoteException;
    public ValidationResult handIn(DTO media) throws RemoteException;
    public ValidationResult extend(DTO media) throws RemoteException;
    public DTO getCustomerByMedia(DTO media) throws RemoteException;
}
