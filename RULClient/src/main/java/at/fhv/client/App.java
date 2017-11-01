package at.fhv.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        //load RosterManager
        //load PersonManager
        //load WorkManager
        //load InstrumentationManager
    }

    @Override
    public void start(Stage stage) throws Exception {
        //LoginView appView = new LoginView();

        //Scene scene = new Scene(appView.getView());
        //stage.setTitle("OMAS");
        //stage.setScene(scene);
        //stage.getIcons().add(new Image("logo-omas.png"));
        //stage.show();
    }

    @Override
    public void stop() throws Exception {
        //Injector.forgetAll();
        //ShutDown.closeSessionFactory();
    }

}
