package at.fhv.team3.presentation.borrowMedia;

import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BorrowMediaPresenter implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private TextField customerSearchField;

    @FXML
    private Button customerSearchButton;

    @FXML
    private ComboBox<CustomerDTO> customerDropdown;

    @FXML
    private TextField contractStatusField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNrField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private Button borrowMediaCancelButton;

    @FXML
    private Button borrowButton;

    @FXML
    void borrowMediaAction(ActionEvent event) {

    }

    @FXML
    void borrowMediaCancelAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Ihre Eingaben gehen verloren", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle("Attention");
        alert.setHeaderText("Wollen Sie wirklich abbrechen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) borrowMediaCancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void customerSea(ActionEvent event) {

    }

    @FXML
    void handleSelectedCustomer(MouseEvent event) {

    }

    public void setInfo(DTO media){

    }


}
