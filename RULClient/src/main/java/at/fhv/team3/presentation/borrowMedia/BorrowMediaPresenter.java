package at.fhv.team3.presentation.borrowMedia;

import at.fhv.team3.application.ServerIP;
import at.fhv.team3.domain.dto.*;
import at.fhv.team3.presentation.detailbook.DetailBookPresenter;
import at.fhv.team3.presentation.detaildvd.DetailDvdPresenter;
import at.fhv.team3.presentation.detailmagazin.DetailMagazinPresenter;
import at.fhv.team3.rmi.interfaces.RMIBorrow;
import at.fhv.team3.rmi.interfaces.RMICustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
    private DetailMagazinPresenter dmp = null;
    private DetailDvdPresenter ddp = null;
    private DetailBookPresenter dbp = null;
    private ValidationResult validationResult;
    private ServerIP serverIP;
    private String host;

    public void initialize(URL location, ResourceBundle resources) {
        placeholder = new Label("Bitte suchen!");
        customerDropdown.setPlaceholder(placeholder);

        serverIP = ServerIP.getInstance();
        host = serverIP.getServer();

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

    // Das zuvor ausgeqählte Medium wird ausgeliehen
    @FXML
    void borrowMediaAction(ActionEvent event) {
        if(selectedItemfromComboBox.getSubscription()) {

            try {
                Registry registry = LocateRegistry.getRegistry(host, 1099);
                RMIBorrow rmiBorrow = (RMIBorrow) registry.lookup("Borrow");

                if (bookDTO != null) {
                    validationResult = rmiBorrow.handOut(bookDTO, selectedItemfromComboBox);
                } else if (dvdDTO != null) {
                    validationResult = rmiBorrow.handOut(dvdDTO, selectedItemfromComboBox);
                } else if (magazineDTO != null) {
                    validationResult = rmiBorrow.handOut(magazineDTO, selectedItemfromComboBox);
                }

                if (!validationResult.hasErrors()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Erfolgreich Medium ausgeliehen", ButtonType.OK);
                    alert.setTitle("Success");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        Stage stage = (Stage) borrowMediaCancelButton.getScene().getWindow();
                        stage.close();
                    }
                    if(dbp != null){
                        dbp.reload();
                    }else if(ddp != null){
                        ddp.reload();
                    }else if(dmp != null){
                        dmp.reload();
                    }
                }else{
                    String errorString = setErrorMessage();
                    Alert alert = new Alert(Alert.AlertType.WARNING, errorString, ButtonType.OK);
                    alert.setTitle("Warning");
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

    // Der Ausleihvorgang wird abgebrochen
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

    // Es wird durch das betätigen der Enter Taste, die Kundensuche gestartet.
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

    // Es wird nach einem Kunden gesucht.
    @FXML
    void customerSearch() {
        if((!(customerSearchField.getText().trim().equals("")))) {
            try {
                Registry registry = LocateRegistry.getRegistry(host, 1099);
                RMICustomer rmiCustomer = (RMICustomer) registry.lookup("Customer");

                ArrayList<DTO> customersFound = rmiCustomer.findCustomer(customerSearchField.getText());

                _customer = FXCollections.observableArrayList();
                for (int i = 0; i < customersFound.size(); i++) {
                    HashMap<String, String> customerResult = customersFound.get(i).getAllData();
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

    // Alle gefundenen Kunden werden im Dropdown angezeigt
    public void renderCustomer(){
        if(customerDropdown.getItems().isEmpty()){
            customerDropdown.setItems(null);
            placeholder = new Label("Keine Ergebnisse!");
            customerDropdown.setPlaceholder(placeholder);
            customerDropdown.show();
        } else {
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
                    return null;
                }
            });
            customerDropdown.show();
        }
    }

    // Die Informationen des Kunden werden in den dazugehörigen Feldern angezeigt
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

    public void setBookPresenter(DetailBookPresenter p){ dbp = p;}

    public void setDvdPresenter(DetailDvdPresenter p){ ddp = p;}

    public void setMagazinPresnter(DetailMagazinPresenter p){ dmp = p;}

    // Fehlermeldungen werden umgewandelt und weitergegeben.
    public String setErrorMessage(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < validationResult.getErrorMessages().size(); i++){
            sb.append(validationResult.getErrorMessages().get(i).toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

