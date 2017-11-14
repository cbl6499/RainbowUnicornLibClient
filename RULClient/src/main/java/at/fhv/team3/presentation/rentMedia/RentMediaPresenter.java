package at.fhv.team3.presentation.rentMedia;

import at.fhv.team3.domain.dto.*;
import at.fhv.team3.rmi.interfaces.RMICustomer;
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
import javafx.util.StringConverter;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class RentMediaPresenter implements Initializable {

    private ObservableList<CustomerDTO> _customer;
    private Label placeholder;
    CustomerDTO selectedItemfromComboBox;
    private int _id;

    public void initialize(URL location, ResourceBundle resources) {
        placeholder = new Label("Bitte suchen!");
        customerDropdown.setPlaceholder(placeholder);

        customerDropdown.setOnAction((event) -> {
            selectedItemfromComboBox = customerDropdown.getSelectionModel().getSelectedItem();
            setInfo(null);
        });
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
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            RMICustomer rmiCustomer = (RMICustomer) registry.lookup("Borrow");

            // BorrowedItemDTO borrowedItemDTO = new BorrowedItemDTO(())
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
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
        if((!(customerSearchField.getText().trim().equals("")))) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMICustomer rmiCustomer = (RMICustomer) registry.lookup("Customer");

                ArrayList<DTO> CustomersFound = rmiCustomer.findCustomer(customerSearchField.getText());

                _customer = FXCollections.observableArrayList();
                for (int i = 0; i < CustomersFound.size(); i++) {
                    HashMap<String, String> customerResult = CustomersFound.get(i).getAllData();
                    CustomerDTO tempCustomer = new CustomerDTO(Integer.parseInt(customerResult.get("id")), customerResult.get("firstname"), customerResult.get("lastname"),
                            Boolean.parseBoolean(customerResult.get("subscription")), customerResult.get("email"), customerResult.get("phonenumber"));

                    _customer.add(tempCustomer);
                }
                customerDropdown.setItems(null);
                customerDropdown.setItems(_customer);
                renderCustomer();
            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            customerDropdown.setItems(null);
            placeholder = new Label("Falsche Eingabe!");
            customerDropdown.setPlaceholder(placeholder);
            customerDropdown.show();
        }
    }

    @FXML
    void handleSelectedCustomer(MouseEvent event) {

    }

    public void renderCustomer(){
        // placeholder
        if(customerDropdown.getItems().isEmpty()){
            customerDropdown.setItems(null);
            placeholder = new Label("Keine Ergebnisse!");
            customerDropdown.setPlaceholder(placeholder);
            customerDropdown.show();
        } else {
            // Define rendering of the list of values in ComboBox drop down.
            customerDropdown.setCellFactory((ComboBox) -> {
                return new ListCell<CustomerDTO>() {
                    @Override
                    protected void updateItem(CustomerDTO item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getFirstName() + " " + item.getLastName());
                        }
                    }
                };
            });
            // Define rendering of selected value shown in ComboBox.
            customerDropdown.setConverter(new StringConverter<CustomerDTO>() {
                @Override
                public String toString(CustomerDTO person) {
                    if (person == null) {
                        return null;
                    } else {
                        return person.getFirstName() + " " + person.getLastName();
                    }
                }

                @Override
                public CustomerDTO fromString(String personString) {
                    return null; // No conversion fromString needed.
                }
            });
            customerDropdown.show();
        }
    }

    public void setInfo(DTO dto){
        if(selectedItemfromComboBox != null) {
            if (selectedItemfromComboBox.getFirstName() != null) {
                firstNameField.setText(selectedItemfromComboBox.getFirstName());
            }
            if (selectedItemfromComboBox.getLastName() != null) {
                lastNameField.setText(selectedItemfromComboBox.getLastName());
            }
            if (selectedItemfromComboBox.getPhoneNumber() != null) {
                phoneNrField.setText(selectedItemfromComboBox.getPhoneNumber());
            }
            if (selectedItemfromComboBox.getEmail() != null) {
                emailField.setText(selectedItemfromComboBox.getEmail());
            }
            if (selectedItemfromComboBox.getSubscription() == true) {
                contractStatusField.setText("Aktiv");
            }
            if (selectedItemfromComboBox.getSubscription() == false) {
                contractStatusField.setText("InAktiv");
            }
        }

        _id = dto.getId();

    }


}
