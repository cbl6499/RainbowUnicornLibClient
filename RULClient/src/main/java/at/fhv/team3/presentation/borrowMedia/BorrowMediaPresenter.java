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
       /* if(!customerSearchField.getText().isEmpty() && !customerSearchField.getText().equals(" ")) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMICustomer rmiCustomer = (RMICustomer) registry.lookup("Search");

               List<Customer> allMedias = rmiCustomer.findCustomer(customerSearchField.getText());

                List<DTO> customerList= allMedias.get(0);

                _customer = FXCollections.observableArrayList();
                for (int i = 0; i < bookArrayList.size(); i++) {
                    HashMap<String, String> bookResult = bookArrayList.get(i).getAllData();
                    CustomerDTO tempCustomer = new CustomerDTO(Integer.parseInt(bookResult.get("id")), bookResult.get("title"), bookResult.get("publisher"), bookResult.get("author"),
                            bookResult.get("isbn"), bookResult.get("edition"), bookResult.get("pictureURL"), bookResult.get("shelfPos"));
                    _customer.add(tempCustomer);
                }
            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }

        }else{
            customerDropdown.setPlaceholder(new Label("A man has no name"));
        }*/
    }

    @FXML
    void handleSelectedCustomer(MouseEvent event) {

    }

    public void setInfo(DTO media){

    }


}
