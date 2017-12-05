package at.fhv.team3.applicationbean.interfaces;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ClemensB on 03.12.17.
 */
@Remote
public interface RemoteCustomerBeanFace extends Serializable {

    public ArrayList<DTO> findCustomer(String term);

    public void saveCustomer(CustomerDTO dto);
}
