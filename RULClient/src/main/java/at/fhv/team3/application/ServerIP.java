package at.fhv.team3.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by ClemensB on 20.11.17.
 */
public class ServerIP {
    private static ServerIP serverIP;
    private String server;
    private String config = "config.properties";

    private ServerIP() {
    }

    public static ServerIP getInstance() {
        if (serverIP == null) {
            serverIP = new ServerIP();
        }
        return serverIP;
    }

    public String getServer(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = getClass().getClassLoader().getResourceAsStream(config);
            URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
            URL urlproperties = new URL(url, config);
            prop.load(urlproperties.openStream());

            server = prop.getProperty("serverIP");

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
        if(server == null){
            server = "10.0.51.93";
        }
        return server;
    }
}
