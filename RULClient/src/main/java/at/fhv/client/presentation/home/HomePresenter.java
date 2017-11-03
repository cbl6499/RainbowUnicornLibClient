package at.fhv.client.presentation.home;

        import at.fhv.client.presentation.bibinfo.BibInfoView;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.stage.Stage;

        import java.net.URL;
        import java.util.ResourceBundle;

public class HomePresenter implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
    }


    @FXML
    private Button BibInfoButton;

    @FXML
    private void handleButtonActionBibInfo(ActionEvent event) {
        BibInfoView bi = new BibInfoView();
        Scene scene = new Scene(bi.getView());
        Stage stage = (Stage) BibInfoButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button searchButton;

    @FXML
    public void search(ActionEvent event) {

    }

}
