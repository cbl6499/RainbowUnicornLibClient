package at.fhv.team3.applicationbean.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.ValidationResult;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.List;

@Remote
public interface RemoteBookingBeanFace extends Serializable{

    public List<DTO> getAllBookings();

    public List<DTO> getBookingsForMedia(DTO media);

    public ValidationResult bookItem(DTO dto, CustomerDTO customerDto);
}
