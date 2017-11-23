package at.fhv.team3.presentation.message;

import at.fhv.team3.application.LoggedInUser;
import at.fhv.team3.application.ServerIP;
import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.domain.dto.MagazineDTO;
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

    public void initialize(URL location, ResourceBundle resources) {
        serverIP = ServerIP.getInstance();
        host = serverIP.getServer();
        //Login
        _loggedInUser = LoggedInUser.getInstance();
        if(_loggedInUser.isLoggedIn() == false){
            LogoutButton.setVisible(false);
        }

    }


    @FXML
    private Button LogoutButton;

    @FXML
    private Button MessageBackButton;


    // Leitet den Benutzer zurück auf die Home Ansicht
    @FXML
    private void handleButtonActionMessageBackButton() {
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) MessageBackButton.getScene().getWindow();
        stage.setHeight(MessageBackButton.getScene().getWindow().getHeight());
        stage.setWidth(MessageBackButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }

    // Benutzer wird ausgelogged
    @FXML
    public void handleButtonActionLogout(){
        _loggedInUser.setUser(null);
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.setHeight(LogoutButton.getScene().getWindow().getHeight());
        stage.setWidth(LogoutButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }


    public void reload(){
        MessageView hv = new MessageView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.setHeight(LogoutButton.getScene().getWindow().getHeight());
        stage.setWidth(LogoutButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }
}

