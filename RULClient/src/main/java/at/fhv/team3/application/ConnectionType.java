package at.fhv.team3.application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ConnectionType {
    private static ConnectionType connectionType;
    private String connection;
    private String config = "config.properties";

    private ConnectionType() { }

    public static ConnectionType getInstance() {
        if (connectionType == null) {
            connectionType = new ConnectionType();
        }
        return connectionType;
    }

    public String getConnection(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = getClass().getClassLoader().getResourceAsStream(config);
            URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
            URL urlproperties = new URL(url, config);
            prop.load(urlproperties.openStream());

            connection = prop.getProperty("connection");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(connection == null){
            connection = "RMI";
        }
        return connection;
    }
}
