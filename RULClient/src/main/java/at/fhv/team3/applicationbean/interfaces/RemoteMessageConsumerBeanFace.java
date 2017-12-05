package at.fhv.team3.applicationbean.interfaces;

import at.fhv.team3.domain.dto.MessageDTO;

import javax.ejb.Remote;
import java.io.Serializable;

/**
 * Created by ClemensB on 03.12.17.
 */

@Remote
public interface RemoteMessageConsumerBeanFace extends Serializable {

    public int getMessageCount();

    public MessageDTO pull();
}
