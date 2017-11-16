package at.fhv.team3.presentation.bibinfo;

import at.fhv.team3.application.EasyCrypt;
import at.fhv.team3.application.LoggedInUser;
import at.fhv.team3.domain.dto.EmployeeDTO;
import at.fhv.team3.presentation.home.HomeView;
import at.fhv.team3.rmi.interfaces.RMILdap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.PublicKey;
import java.util.Optional;
import java.util.ResourceBundle;

public class BibInfoPresenter implements Initializable {
    private LoggedInUser _loggedInUser = null;


    public void initialize(URL location, ResourceBundle resources) {
        //Login
        _loggedInUser = LoggedInUser.getInstance();
        if(_loggedInUser.isLoggedIn() == false){
            LogoutButton.setVisible(false);
        }
    }

    @FXML
    private Button BibInfoBackButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button LoginButton;

    @FXML
    private Button LogoutButton;


    @FXML
    private void handleButtonActionBibInfoBackButton() {
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) BibInfoBackButton.getScene().getWindow();
        stage.setHeight(BibInfoBackButton.getScene().getWindow().getHeight());
        stage.setWidth(BibInfoBackButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleButtonActionLogout(){
        _loggedInUser.setUser(null);
        reload();
    }

    @FXML
    public void loginTroughEnter() {
        LoginButton.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    handleButtonActionLogin();
                }
            }
        });
    }

    @FXML
    private void handleButtonActionLogin(){
        Registry registry = null;
        if(false == _loggedInUser.isLoggedIn()) {
            try {
                registry = LocateRegistry.getRegistry(1099);
                RMILdap rmiEmployee = (RMILdap) registry.lookup("Ldap");
                EmployeeDTO empoyeeToLoggin = null;
                PublicKey pk = rmiEmployee.getPublicKey().getPublicKey();
                EasyCrypt ecPub = new EasyCrypt(pk, "RSA");
                if(!(username.getText().isEmpty()) || !(password.getText().isEmpty())){
                   empoyeeToLoggin =  (EmployeeDTO) rmiEmployee.authenticateUser(ecPub.encrypt(username.getText()),ecPub.encrypt(password.getText()));
                }
                if (empoyeeToLoggin != null && empoyeeToLoggin.isLoggedIn()) {
                    _loggedInUser.setUser((EmployeeDTO)empoyeeToLoggin);

                    Alert alert = new Alert(Alert.AlertType.WARNING, "Login Erfolgreich", ButtonType.OK);
                    alert.setTitle("Login Warnung");
                    alert.setHeaderText("Login Erfolgreich");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        alert.close();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Username oder Passwort ist Falsch", ButtonType.OK);
                    alert.setTitle("Login Warnung");
                    alert.setHeaderText("Username oder Passwort ist Falsch");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        alert.close();
                    }
                }
                reload();
            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bereits angemeldet", ButtonType.OK);
            alert.setTitle("Login Warnung");
            alert.setHeaderText("Bereits angemeldet");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
            }
        }
    }

    public void reload(){
            BibInfoView bi = new BibInfoView();
            Scene scene = new Scene(bi.getView());
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            stage.setHeight(LoginButton.getScene().getWindow().getHeight());
            stage.setWidth(LoginButton.getScene().getWindow().getWidth());
            stage.setScene(scene);
            stage.show();
    }

}
