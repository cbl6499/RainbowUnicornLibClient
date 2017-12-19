package at.fhv.team3.application;

import at.fhv.team3.applicationbean.interfaces.RemoteSearchBeanFace;
import at.fhv.team3.domain.dto.DTO;

import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Properties;

public class EJBConnect {

    static private void connect(String ejbName) {
        try {
            //TODO: import jars
            Properties props = new Properties();
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            props.setProperty("java.naming.factory.initial",
                    "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs",
                    "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state",
                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            // props.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
            // props.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); //3700 Glassfish default port

            InitialContext ctx = new InitialContext(props);
            System.out.println("InitialContext done");
            RemoteSearchBeanFace remoteInterface = (RemoteSearchBeanFace) ctx.lookup(ejbName);
            System.out.println("Remote access done");
            System.out.println(remoteInterface.getClass());
            ArrayList<ArrayList<DTO>> dto = remoteInterface.search("Das");
            for (ArrayList<DTO> d : dto) {
                for (DTO c : d) {
                    System.out.println(c.getId());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
