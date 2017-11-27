package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.dto.MessageDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by David on 11/24/2017.
 */
public interface RMIMessageConsumer extends Remote {

    public int getMessageCount() throws RemoteException;
    public MessageDTO pull() throws RemoteException;
}
