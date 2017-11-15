package at.fhv.team3.presentation.rentMedia;

import at.fhv.team3.domain.BookedItem;
import at.fhv.team3.domain.dto.*;
import at.fhv.team3.rmi.interfaces.RMIBooking;
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
import javafx.util.StringConverter;

import java.net.URL;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class RentMediaPresenter implements Initializable {

    private ObservableList<CustomerDTO> _customer;
    private Label placeholder;
    CustomerDTO selectedItemfromComboBox;
    private int _id;
    private BookDTO bookDTO;
    private DvdDTO dvdDTO;
    private MagazineDTO magazineDTO;
    ObservableList<BookedItemDTO> _bookings;

    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(new PropertyValueFactory<>("start"));

        placeholder = new Label("Bitte suchen!");
        customerDropdown.setPlaceholder(placeholder);

        //setBookingTable();

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
    private Button rentButton;

    @FXML
    private TableView<BookedItemDTO> bookingTable;

    @FXML
    private TableColumn<BookedItemDTO, String> name;

    @FXML
    private TableColumn<BookedItemDTO, String> date;

    @FXML
    void bookingMediaAction() {
        if (selectedItemfromComboBox.getSubscription()) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMIBooking rmiBooking = (RMIBooking) registry.lookup("Booking");

                if (bookDTO != null) {
                    rmiBooking.bookItem(bookDTO, selectedItemfromComboBox);
                } else if (dvdDTO != null) {
                    rmiBooking.bookItem(dvdDTO, selectedItemfromComboBox);
                } else if (magazineDTO != null) {
                    rmiBooking.bookItem(magazineDTO, selectedItemfromComboBox);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Erfolgreich Medium reserviert", ButtonType.OK);
                alert.setTitle("Success");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Stage stage = (Stage) borrowMediaCancelButton.getScene().getWindow();
                    stage.close();
                }

            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Kunde hat keine laufende Subscription", ButtonType.OK);
            alert.setTitle("Subscription Alert");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                alert.close();
            }
        }
    }

    public void setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
        setBookingTable();
    }

    public void setDvdDTO(DvdDTO dvdDTO) {
        this.dvdDTO = dvdDTO;
        setBookingTable();
    }

    public void setMagazineDTO(MagazineDTO magazineDTO) {
        this.magazineDTO = magazineDTO;
        setBookingTable();
    }


    @FXML
    void borrowMediaCancelAction(ActionEvent event) {
        Stage stage = (Stage) borrowMediaCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void searchTroughEnter() {
        customerSearchButton.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    customerSearch();
                }
            }
        });
    }

    @FXML
    void customerSearch() {
        if ((!(customerSearchField.getText().trim().equals("")))) {
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
        } else {
            customerDropdown.setItems(null);
            placeholder = new Label("Falsche Eingabe!");
            customerDropdown.setPlaceholder(placeholder);
            customerDropdown.show();
        }
    }

    @FXML
    void handleSelectedCustomer(MouseEvent event) {

    }

    public void renderCustomer() {
        // placeholder
        if (customerDropdown.getItems().isEmpty()) {
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

    public void setInfo() {
        if (selectedItemfromComboBox != null) {
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

    public void setBookingTable() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            RMIBooking rmiBookings = (RMIBooking) registry.lookup("Booking");

            ArrayList<DTO> bookingslist = null;
            if (bookDTO != null) {
                bookingslist = (ArrayList<DTO>) rmiBookings.getBookingsForMedia(bookDTO);
            } else if (dvdDTO != null) {
                bookingslist = (ArrayList<DTO>) rmiBookings.getBookingsForMedia(dvdDTO);
            } else if (magazineDTO != null) {
                bookingslist = (ArrayList<DTO>) rmiBookings.getBookingsForMedia(magazineDTO);
            }
            if(bookingslist != null) {
                _bookings = FXCollections.observableArrayList();
                for (int i = 0; i < bookingslist.size(); i++) {
                    BookedItemDTO tempBookings = (BookedItemDTO) bookingslist.get(i);
                    _bookings.add(tempBookings);
                }
                bookingTable.setItems(_bookings);
            } else {
               bookingTable.setItems(null);
            }
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

}
