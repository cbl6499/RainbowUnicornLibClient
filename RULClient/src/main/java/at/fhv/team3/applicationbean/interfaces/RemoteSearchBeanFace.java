package at.fhv.team3.applicationbean.interfaces;

import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.domain.dto.MagazineDTO;
import at.fhv.team3.domain.interfaces.Borrowable;
import at.fhv.team3.domain.interfaces.Searchable;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Remote
public interface RemoteSearchBeanFace extends Serializable {

    public List<DTO> getAllBookDTOs();

    public List<DTO> getAllDvdDTOs();

    public List<DTO> getAllMagazineDTOs();

    public ArrayList<ArrayList<Searchable>> searchMedias(String searchTerm);

    public ArrayList<ArrayList<DTO>> search(String searchTerm);

    public ArrayList<BookDTO> getBooksByISBN(String isbn);

    public ArrayList<DvdDTO> getDvdByTitle(String title);

    public ArrayList<MagazineDTO> getMagazinesByTitleAndEdition(String title, String edition);
}
