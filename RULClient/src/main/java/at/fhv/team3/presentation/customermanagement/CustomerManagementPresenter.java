package at.fhv.team3.presentation.customermanagement;

import at.fhv.team3.application.ServerIP;
import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.presentation.detailbook.DetailBookPresenter;
import at.fhv.team3.presentation.detailbook.DetailBookView;
import at.fhv.team3.rmi.interfaces.RMICustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerManagementPresenter implements Initializable {
    private ObservableList<CustomerDTO> _customers;
    private Label placeholder;
    CustomerDTO selectedItemfromComboBox;
    private ServerIP serverIP;
    private String host;

    public void initialize(URL location, ResourceBundle resources) {
        placeholder = new Label("Bitte suchen!");
        resultCustomer.setPlaceholder(placeholder);

        serverIP = ServerIP.getInstance();
        host = serverIP.getServer();

        resultCustomer.setOnAction((event) -> {
            selectedItemfromComboBox = resultCustomer.getSelectionModel().getSelectedItem();
            setInfo();
        });
    }

    @FXML
    private Button CostumerManagementCancelButton;

    @FXML
    private TextField searchCostumerField;

    @FXML
    private TextField fistname;

    @FXML
    private TextField secondname;

    @FXML
    private TextField tel;

    @FXML
    private TextField email;

    @FXML
    private TextField subscription;

    @FXML
    private Button searchCostumerButton;

    @FXML
    private ComboBox<CustomerDTO> resultCustomer;

    // Kundenverwaltung wird abgebrochen
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

    @FXML
    public void findCustomerTroughEnter() {
        searchCostumerButton.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    findCustomer();
                }
            }
        });
    }

    // Es wird durch das betätigen der Enter Taste, die Kundensuche gestartet.
    @FXML
    public void findCustomer(){
        if((!(searchCostumerField.getText().trim().equals("")))) {
            try {
                Registry registry = LocateRegistry.getRegistry(host, 1099);
                RMICustomer rmiCustomer = (RMICustomer) registry.lookup("Customer");

                ArrayList<DTO> CustomersFound = rmiCustomer.findCustomer(searchCostumerField.getText());

                _customers = FXCollections.observableArrayList();
                for (int i = 0; i < CustomersFound.size(); i++) {
                    HashMap<String, String> customerResult = CustomersFound.get(i).getAllData();
                    CustomerDTO tempCustomer = new CustomerDTO(Integer.parseInt(customerResult.get("id")), customerResult.get("firstname"), customerResult.get("lastname"),
                            Boolean.parseBoolean(customerResult.get("subscription")), customerResult.get("email"), customerResult.get("phonenumber"));

                    _customers.add(tempCustomer);
                }
                resultCustomer.setItems(null);
                resultCustomer.setItems(_customers);
                renderCustomer();
            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            resultCustomer.setItems(null);
            placeholder = new Label("Falsche Eingabe!");
            resultCustomer.setPlaceholder(placeholder);
            resultCustomer.show();
        }
    }

    // Alle gefundenen Kunden werden im Dropdown angezeigt
    public void renderCustomer(){
        if(resultCustomer.getItems().isEmpty()){
            resultCustomer.setItems(null);
            placeholder = new Label("Keine Ergebnisse!");
            resultCustomer.setPlaceholder(placeholder);
            resultCustomer.show();
        } else {
            resultCustomer.setCellFactory((ComboBox) -> {
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
            resultCustomer.setConverter(new StringConverter<CustomerDTO>() {
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
            resultCustomer.show();
        }
    }

    // Die Informationen des Kunden werden in den dazugehörigen Feldern angezeigt
    public void setInfo(){
        if(selectedItemfromComboBox != null) {
            if (selectedItemfromComboBox.getFirstName() != null) {
                fistname.setText(selectedItemfromComboBox.getFirstName());
            }
            if (selectedItemfromComboBox.getLastName() != null) {
                secondname.setText(selectedItemfromComboBox.getLastName());
            }
            if (selectedItemfromComboBox.getPhoneNumber() != null) {
                tel.setText(selectedItemfromComboBox.getPhoneNumber());
            }
            if (selectedItemfromComboBox.getEmail() != null) {
                email.setText(selectedItemfromComboBox.getEmail());
            }
            if (selectedItemfromComboBox.getSubscription() == true) {
                subscription.setText("Aktiv");
            }
            if (selectedItemfromComboBox.getSubscription() == false) {
                subscription.setText("InAktiv");
            }
        }
    }

}
