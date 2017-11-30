package at.fhv.team3.presentation.message;

import at.fhv.team3.application.LoggedInUser;
import at.fhv.team3.application.ServerIP;
import at.fhv.team3.domain.dto.*;
import at.fhv.team3.presentation.bibinfo.BibInfoView;
import at.fhv.team3.presentation.customermanagement.CustomerManagementView;
import at.fhv.team3.presentation.detailbook.DetailBookPresenter;
import at.fhv.team3.presentation.detailbook.DetailBookView;
import at.fhv.team3.presentation.detaildvd.DetailDvdPresenter;
import at.fhv.team3.presentation.detaildvd.DetailDvdView;
import at.fhv.team3.presentation.detailmagazin.DetailMagazinPresenter;
import at.fhv.team3.presentation.detailmagazin.DetailMagazinView;
import at.fhv.team3.presentation.home.HomeView;
import at.fhv.team3.rmi.interfaces.RMIMediaSearch;
import at.fhv.team3.rmi.interfaces.RMIMessageConsumer;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class MessagePresenter implements Initializable {
    private LoggedInUser _loggedInUser = null;
    private ServerIP serverIP;
    private String host;
    private Boolean MessagesAvailable = true;

    public void initialize(URL location, ResourceBundle resources) {
        serverIP = ServerIP.getInstance();
        host = serverIP.getServer();
        //Login
        _loggedInUser = LoggedInUser.getInstance();
        //Message
        reloadMessagesCount();
        if(MessagesAvailable == false){
            MessageLoadButton.setDisable(false);
        }

        if(_loggedInUser.isLoggedIn() == false){
            HomeView hv = new HomeView();
            Scene scene = new Scene(hv.getView());
            Stage stage = (Stage) MessageBackButton.getScene().getWindow();
            stage.setHeight(MessageBackButton.getScene().getWindow().getHeight());
            stage.setWidth(MessageBackButton.getScene().getWindow().getWidth());
            stage.setScene(scene);
            stage.show();
        }

    }


    @FXML
    private Button LogoutButton;

    @FXML
    private Button MessageBackButton;

    @FXML
    private Label messageCounter;

    @FXML
    private Button MessageLoadButton;

    @FXML
    private  TextField nameField;

    @FXML
    private  TextField phoneNrField;

    @FXML
    private  TextField emailField;

    @FXML
    private  TextField contractStatusField;

    @FXML
    private TextField accountData;

    @FXML
    private TextArea TextAreaMessage;


    // Leitet den Benutzer zurück auf die Home Ansicht
    @FXML
    private void handleButtonActionMessageBackButton() {

        Alert alert = new Alert(Alert.AlertType.WARNING, "Ihre Nachricht geht verloren", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle("Attention");
        alert.setHeaderText("Wollen Sie wirklich die Seite verlassen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            HomeView hv = new HomeView();
            Scene scene = new Scene(hv.getView());
            Stage stage = (Stage) MessageBackButton.getScene().getWindow();
            stage.setHeight(MessageBackButton.getScene().getWindow().getHeight());
            stage.setWidth(MessageBackButton.getScene().getWindow().getWidth());
            stage.setScene(scene);
            stage.show();
        }
    }

    // Benutzer wird ausgelogged
    @FXML
    public void handleButtonActionLogout(){

        Alert alert = new Alert(Alert.AlertType.WARNING, "Ihre Nachricht geht verloren", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle("Attention");
        alert.setHeaderText("Wollen Sie wirklich die Seite verlassen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            _loggedInUser.setUser(null);
            HomeView hv = new HomeView();
            Scene scene = new Scene(hv.getView());
            Stage stage = (Stage) LogoutButton.getScene().getWindow();
            stage.setHeight(LogoutButton.getScene().getWindow().getHeight());
            stage.setWidth(LogoutButton.getScene().getWindow().getWidth());
            stage.setScene(scene);
            stage.show();
        }
    }

    public void reloadMessagesCount(){
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            RMIMessageConsumer rmc = (RMIMessageConsumer) registry.lookup("MessageConsumer");
            int i = rmc.getMessageCount();
            String MessageCount = "";
            if(i > 99){
                MessageCount = "99+";
            } else {
                MessageCount = "" + i;
                if(MessageCount.equals("0")){
                    MessagesAvailable = false;
                }
            }
            messageCounter.setText(MessageCount);
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadMessage(){
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            RMIMessageConsumer rmc = (RMIMessageConsumer) registry.lookup("MessageConsumer");
            MessageDTO message = (MessageDTO) rmc.pull();

            if(!(message.getMessage().equals("No Messages found."))){
                //Set Cotumer Lib
                setLibCustomerInfo(message);
                //Set Message
                if(message.getMessage() != null || !(message.getMessage().trim().equals(""))){
                    TextAreaMessage.setText(message.getMessage());
                } else{
                    TextAreaMessage.setText("keine Information vorhanden");
                }
                //reload
                reloadMessagesCount();
            }
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void setLibCustomerInfo(MessageDTO message){
        if(message.getCustomer() != null){
            CustomerDTO customer = message.getCustomer();
            //setNAME
            if(customer.getFirstName() != null || !(customer.getFirstName().trim().equals(""))){
                String name = customer.getFirstName();
                nameField.setText(name);
                if(customer.getLastName() != null || !(customer.getLastName().trim().equals(""))){
                    name = name + " " + customer.getLastName();
                    nameField.setText(name);
                }
            } else{
                nameField.setText("keine Information vorhanden");
            }
            //setPHONE
            if(customer.getPhoneNumber() != null || !(customer.getPhoneNumber().trim().equals(""))){
                phoneNrField.setText(customer.getPhoneNumber());
            } else {
                phoneNrField.setText("keine Information vorhanden");
            }
            //setEMAIL
            if(customer.getEmail() != null || !(customer.getEmail().trim().equals(""))){
                emailField.setText(customer.getEmail());
            } else {
                emailField.setText("keine Information vorhanden");
            }
            //setSub
            if (customer.getSubscription() == true) {
                contractStatusField.setText("Aktiv");
            }
            if (customer.getSubscription() == false) {
                contractStatusField.setText("InAktiv");
            }
            //setAccountData
            accountData.setText("keine Information vorhanden");
        } else if(message.getLib() != null){
            ExternalLibDTO lib = message.getLib();
            //setNAME
            if(lib.getName() != null || !(lib.getName().trim().equals(""))){
                nameField.setText(lib.getName());
            } else{
                nameField.setText("keine Information vorhanden");
            }
            //setPHONE
            phoneNrField.setText("keine Information vorhanden");
            //setEMAIL
            emailField.setText("keine Information vorhanden");
            //setSub
            contractStatusField.setText("keine Information vorhanden");
            //setAccountData
            if(lib.getAccountData() != null || !(lib.getAccountData().trim().equals(""))){
                accountData.setText(lib.getAccountData());
            } else{
                accountData.setText("keine Information vorhanden");
            }
        } else {
            nameField.setText("keine Information vorhanden");
            phoneNrField.setText("keine Information vorhanden");
            emailField.setText("keine Information vorhanden");
            contractStatusField.setText("keine Information vorhanden");
            accountData.setText("keine Information vorhanden");
        }
    }



}

