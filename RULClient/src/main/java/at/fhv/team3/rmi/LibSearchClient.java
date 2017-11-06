package at.fhv.team3.rmi;

/**
 * Created by ClemensB on 06.11.17.
 */

import at.fhv.team3.rmi.interfaces.RMIMediaSearch;

import java.rmi.registry.*;

public class LibSearchClient {

    public static void main(String args[]) {

        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
