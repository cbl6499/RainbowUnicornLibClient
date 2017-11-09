package at.fhv.team3.presentation.borrowMedia;

import at.fhv.team3.domain.Customer;
import at.fhv.team3.domain.dto.*;
import at.fhv.team3.rmi.interfaces.RMICustomer;
import at.fhv.team3.rmi.interfaces.RMIMediaSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class BorrowMediaPresenter implements Initializable {

    private ObservableList<CustomerDTO> _customer;

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
    public void searchTroughEnter() {
        customerSearchButton.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    customerSearch();
                }
            }
        });
    }

    @FXML
    void customerSearch() {
        customerDropdown.setCellFactory(new PropertyValueFactory("firstname" + " " + "lastname"));
        if(!customerSearchField.getText().isEmpty() && !customerSearchField.getText().equals(" ")) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMICustomer rmiCustomer = (RMICustomer) registry.lookup("Customer");

                List<DTO> allMedias = rmiCustomer.findCustomer(customerSearchField.getText());

                _customer = FXCollections.observableArrayList();
                for (int i = 0; i < allMedias.size(); i++) {
                    HashMap<String, String> customerResult = allMedias.get(i).getAllData();
                    CustomerDTO tempCustomer = new CustomerDTO(Integer.parseInt(customerResult.get("id")), customerResult.get("firstname"), customerResult.get("lastname"),
                            Boolean.parseBoolean(customerResult.get("subscription")), customerResult.get("email"), customerResult.get("phonenumber"));

                    _customer.add(tempCustomer);
                }
                customerDropdown.setItems(_customer);
            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }

        }else{
            customerDropdown.setPlaceholder(new Label("A man has no name"));
        }
    }

    @FXML
    void handleSelectedCustomer(MouseEvent event) {

    }

    public void setInfo(DTO media){

    }


}
