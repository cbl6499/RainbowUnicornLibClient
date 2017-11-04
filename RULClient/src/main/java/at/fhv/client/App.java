package at.fhv.client;

import at.fhv.client.presentation.detailbook.DetailBookView;
import at.fhv.client.presentation.detaildvd.DetailDvdView;
import at.fhv.client.presentation.detailmagazin.DetailMagazinView;
import at.fhv.client.presentation.home.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        //load RosterManager
        //load PersonManager
        //load WorkManager
        //load InstrumentationManager

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        HomeView appView = new HomeView();
        //DetailBookView appView = new DetailBookView();
        //DetailDvdView appView = new DetailDvdView();
        //DetailMagazinView appView = new DetailMagazinView();

        Scene scene = new Scene(appView.getView());
        stage.setTitle("RUL");
        stage.setMinWidth(820);
        stage.setMinHeight(690);
        stage.setScene(scene);
        stage.getIcons().add(new Image("logo.png"));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        //Injector.forgetAll();
        //ShutDown.closeSessionFactory();
    }

}
