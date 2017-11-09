package at.fhv.team3.rmi.interfaces;


import at.fhv.team3.domain.dto.DTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ClemensB on 06.11.17.
 */
public interface RMIMediaSearch extends Remote{
    public ArrayList<ArrayList<DTO>> search(String searchTerm) throws RemoteException;
    public List<DTO> getAllBookDTOs() throws RemoteException;
    public List<DTO> getAllDvdDTOs() throws RemoteException;
    public List<DTO> getAllMagazineDTOs() throws RemoteException;
}
