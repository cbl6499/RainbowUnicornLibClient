package at.fhv.team3;

import at.fhv.team3.applicationbean.interfaces.RemoteSearchBeanFace;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.presentation.home.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import java.util.ArrayList;
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
    }

    @Override
    public void stop() throws Exception { }
}


