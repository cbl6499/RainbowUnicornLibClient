package at.fhv.team3.presentation.costumermanagement;

import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.CustomerDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.rmi.interfaces.RMICustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CostumerManagementPresenter implements Initializable {
    private ObservableList<CustomerDTO> _customers;

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Button CostumerManagementCancelButton;

    @FXML
    private TextField searchCostumerField;

    @FXML
    private Button searchCostumerButton;

    @FXML
    private ComboBox<CustomerDTO> resultCustomer;







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
    public void findCustomer(){
        if(!searchCostumerField.getText().isEmpty() && !searchCostumerField.getText().equals(" ")) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMICustomer findCustomer = (RMICustomer) registry.lookup("Customer");

                ArrayList<DTO> CustomersFound = (ArrayList)findCustomer.findCustomer(searchCostumerField.getText());
                _customers = FXCollections.observableArrayList();
                for (int i = 0; i < CustomersFound.size(); i++) {
                    _customers.add((CustomerDTO) CustomersFound.get(i));
                }
                resultCustomer.setItems(_customers);
                renderCustomder();
            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{

        }
    }

    public void renderCustomder(){
        // Define rendering of the list of values in ComboBox drop down.
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

        // Define rendering of selected value shown in ComboBox.
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
                return null; // No conversion fromString needed.
            }
        });
    }



    public void selectCombobox(){

    }
}
