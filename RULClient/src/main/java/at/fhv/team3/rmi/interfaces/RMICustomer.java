package at.fhv.team3.rmi.interfaces;

import at.fhv.team3.domain.Customer;

import java.rmi.Remote;
import java.util.List;

/**
 * Created by David on 11/8/2017.
 */
public interface RMICustomer extends Remote {

    public List<Customer> findCustomer(String term);

}
