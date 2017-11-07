package at.fhv.team3.presentation.bibinfo;

import at.fhv.team3.presentation.home.HomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BibInfoPresenter implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button BibInfoBackButton;

    @FXML
    private void handleButtonActionBibInfoBackButton() {
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) BibInfoBackButton.getScene().getWindow();
        stage.setHeight(BibInfoBackButton.getScene().getWindow().getHeight());
        stage.setWidth(BibInfoBackButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }
}
