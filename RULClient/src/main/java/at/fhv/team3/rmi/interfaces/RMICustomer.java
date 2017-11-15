package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 11/9/2017.
 */

public interface RMICustomer extends Remote {

        public ArrayList<DTO> findCustomer(String term) throws RemoteException;
        public void saveNewCustomer(CustomerDTO dto) throws RemoteException;

    }

