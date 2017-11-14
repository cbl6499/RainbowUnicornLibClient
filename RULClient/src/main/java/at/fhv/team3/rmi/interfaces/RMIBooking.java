package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by David on 11/14/2017.
 */
public interface RMIBooking extends Remote {

    public void bookItem(DTO item, CustomerDTO customer) throws RemoteException;

}
