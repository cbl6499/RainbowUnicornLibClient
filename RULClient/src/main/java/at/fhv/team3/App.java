package at.fhv.team3;

import at.fhv.team3.applicationbean.interfaces.RemoteSearchBeanFace;
import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.presentation.home.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.awt.print.Book;
import java.util.List;
import java.util.Properties;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        HomeView appView = new HomeView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle("RUL");
        stage.setMinWidth(820);
        stage.setMinHeight(690);
        stage.setScene(scene);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();

        connect();
    }

    @Override
    public void stop() throws Exception {

    }

    private void connect() {
        try {
            //TODO: import jars
            Properties props = new Properties();
            props.setProperty("java.naming.factory.initial",
                    "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs",
                    "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state",
                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); //3700 Glassfish default port

            InitialContext ctx = new InitialContext(props);

            RemoteSearchBeanFace remoteInterface = (RemoteSearchBeanFace) ctx.lookup("java:global/RainbowUnicornLib/at.fhv.team3.applicationbean.interfaces.RemoteSearchBeanFace");
            System.out.println(remoteInterface.getBooksByISBN(""));
            List<DTO> dto = remoteInterface.getAllBookDTOs();
            for(DTO d : dto){
                System.out.println(d.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
