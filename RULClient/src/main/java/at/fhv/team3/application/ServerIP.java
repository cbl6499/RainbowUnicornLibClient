package at.fhv.team3.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ClemensB on 20.11.17.
 */
public class ServerIP {
    private static ServerIP serverIP;
    private String server;

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

            input = new FileInputStream("config.properties");

            prop.load(input);

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
        if(server.equals(null)){
            server = "1099";
        }
        return server;
    }
}
