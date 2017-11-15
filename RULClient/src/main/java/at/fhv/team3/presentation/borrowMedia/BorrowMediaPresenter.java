package at.fhv.team3.presentation.borrowMedia;

import at.fhv.team3.domain.dto.*;
import at.fhv.team3.rmi.interfaces.RMIBorrow;
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

public class BorrowMediaPresenter implements Initializable {

    private ObservableList<CustomerDTO> _customer;
    private Label placeholder;
    CustomerDTO selectedItemfromComboBox;
    private int _id;
    private BookDTO bookDTO;
    private DvdDTO dvdDTO;
    private MagazineDTO magazineDTO;
    private Boolean borrowState = false;

    public void initialize(URL location, ResourceBundle resources) {
        placeholder = new Label("Bitte suchen!");
        customerDropdown.setPlaceholder(placeholder);

        customerDropdown.setOnAction((event) -> {
            selectedItemfromComboBox = customerDropdown.getSelectionModel().getSelectedItem();
            setInfo();
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
        if(selectedItemfromComboBox.getSubscription()) {

            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMIBorrow rmiBorrow = (RMIBorrow) registry.lookup("Borrow");

                if (bookDTO != null) {
                    borrowState = rmiBorrow.handOut(bookDTO, selectedItemfromComboBox).hasErrors();
                } else if (dvdDTO != null) {
                    borrowState = rmiBorrow.handOut(dvdDTO, selectedItemfromComboBox).hasErrors();
                } else if (magazineDTO != null) {
                    borrowState = rmiBorrow.handOut(magazineDTO, selectedItemfromComboBox).hasErrors();
                }

                if (borrowState) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Erfolgreich Medium ausgeliehen", ButtonType.OK);
                    alert.setTitle("Success");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        Stage stage = (Stage) borrowMediaCancelButton.getScene().getWindow();
                        stage.close();
                    }
                }

            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Kunde hat keine laufende Subscription", ButtonType.OK);
            alert.setTitle("Subscription Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                alert.close();
            }
        }
        borrowState = false;
    }

    @FXML
    void borrowMediaCancelAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Erfolgreich Medium ausgeliehen", ButtonType.OK);
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

    public void setInfo(){
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

        System.out.println(_id);
    }

    public void setBookDTO(BookDTO bookDTO){
        this.bookDTO = bookDTO;
    }

    public void setDvdDTO(DvdDTO dvdDTO){
        this.dvdDTO = dvdDTO;
    }

    public void setMagazineDTO(MagazineDTO magazineDTO){
        this.magazineDTO = magazineDTO;
    }
}
