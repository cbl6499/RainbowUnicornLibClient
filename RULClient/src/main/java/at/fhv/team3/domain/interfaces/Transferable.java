package at.fhv.team3.domain.interfaces;

import at.fhv.team3.domain.dto.DTO;

/**
 * Created by David on 10/31/2017.
 */
public interface Transferable {

    public DTO createDataTransferObject();
    public void fillFromDTO(DTO dto);
}
