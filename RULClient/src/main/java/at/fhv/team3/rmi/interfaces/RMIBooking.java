package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.ValidationResult;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by David on 11/14/2017.
 */
public interface RMIBooking extends Remote{

    public ValidationResult bookItem(DTO item, CustomerDTO customer) throws RemoteException;
    public List<DTO> getBookingsForMedia(DTO media) throws  RemoteException;
    public List<DTO> getAllBookings() throws RemoteException;

}
