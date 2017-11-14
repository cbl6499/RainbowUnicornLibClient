package at.fhv.team3.rmi.interfaces;


import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.domain.dto.MagazineDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 11/2/2017.
 */
public interface RMIMediaSearch extends Remote{

    public ArrayList<ArrayList<DTO>> search(String searchTerm) throws RemoteException;
    public List<DTO> getAllBookDTOs() throws RemoteException;
    public List<DTO> getAllDvdDTOs() throws RemoteException;
    public List<DTO> getAllMagazineDTOs() throws RemoteException;
    public ArrayList<BookDTO> getBooksByISBN(String isbn) throws RemoteException;
    public ArrayList<DvdDTO> getDvdByTitle(String title) throws RemoteException;
    public ArrayList<MagazineDTO> getMagazinesByTitleAndEdition(String title, String edition) throws RemoteException;
}
