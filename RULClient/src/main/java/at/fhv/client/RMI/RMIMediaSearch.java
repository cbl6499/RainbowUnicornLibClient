package at.fhv.client.RMI;

import at.fhv.client.domain.DTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by ClemensB on 06.11.17.
 */
public interface RMIMediaSearch extends Remote{
    public ArrayList<ArrayList<DTO>> search(String searchTerm) throws RemoteException;
}
