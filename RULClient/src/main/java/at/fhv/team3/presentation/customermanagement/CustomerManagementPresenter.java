package at.fhv.team3.presentation.customermanagement;

import at.fhv.team3.domain.dto.DTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerManagementPresenter implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private Button CostumerManagementCancelButton;

    @FXML
    private void handleButtonActionCostumerManagementCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Ihre Eingaben gehen verloren", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle("Attention");
        alert.setHeaderText("Wollen Sie wirklich abbrechen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) CostumerManagementCancelButton.getScene().getWindow();
            stage.close();
        }

    }

    public void setInfo(DTO media){

    }


}
