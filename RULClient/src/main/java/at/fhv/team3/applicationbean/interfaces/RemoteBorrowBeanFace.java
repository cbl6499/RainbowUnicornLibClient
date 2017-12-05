package at.fhv.team3.applicationbean.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.ValidationResult;

import javax.ejb.Remote;
import java.io.Serializable;

/**
 * Created by ClemensB on 03.12.17.
 */

@Remote
public interface RemoteBorrowBeanFace extends Serializable {

    public ValidationResult handOut(DTO media, CustomerDTO customer);

    public ValidationResult handIn(DTO media);

    public ValidationResult extend(DTO media);

    public DTO getCustomerByMedia(DTO media);
}
