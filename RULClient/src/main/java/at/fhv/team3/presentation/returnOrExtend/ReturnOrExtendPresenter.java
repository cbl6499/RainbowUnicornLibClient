package at.fhv.team3.presentation.returnOrExtend;

import at.fhv.team3.domain.dto.*;
import at.fhv.team3.presentation.detailbook.DetailBookPresenter;
import at.fhv.team3.presentation.detaildvd.DetailDvdPresenter;
import at.fhv.team3.presentation.detailmagazin.DetailMagazinPresenter;
import at.fhv.team3.rmi.interfaces.RMIBorrow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReturnOrExtendPresenter implements Initializable {

    private ObservableList<CustomerDTO> _customer;
    private Label placeholder;
    CustomerDTO customerDTO;
    private int _id;
    private BookDTO bookDTO;
    private DvdDTO dvdDTO;
    private MagazineDTO magazineDTO;
    private Boolean borrowState = false;
    private DetailMagazinPresenter dmp = null;
    private DetailDvdPresenter ddp = null;
    private DetailBookPresenter dbp = null;
    private ValidationResult validationResult;

    public void initialize(URL location, ResourceBundle resources) {
    }

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
    private Button borrowMediaExtendButton;

    @FXML
    private Button borrowMediaReturnButton;


    @FXML
    void borrowMediaCancelAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Medium verlänger oder zurückbringen abbrechen", ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle("Attention");
        alert.setHeaderText("Wollen Sie wirklich abbrechen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) borrowMediaCancelButton.getScene().getWindow();
            stage.close();
        }
    }


    public void setInfo(){
        setCustomerDTO();
        if(customerDTO != null) {
            if (customerDTO.getFirstName() != null) {
                firstNameField.setText(customerDTO.getFirstName());
            }
            if (customerDTO.getLastName() != null) {
                lastNameField.setText(customerDTO.getLastName());
            }
            if (customerDTO.getPhoneNumber() != null) {
                phoneNrField.setText(customerDTO.getPhoneNumber());
            }
            if (customerDTO.getEmail() != null) {
                emailField.setText(customerDTO.getEmail());
            }
            if (customerDTO.getSubscription() == true) {
                contractStatusField.setText("Aktiv");
            }
            if (customerDTO.getSubscription() == false) {
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

    public void setCustomerDTO(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            RMIBorrow rmiBorrow = (RMIBorrow) registry.lookup("Borrow");

            if (bookDTO != null) {
                customerDTO = (CustomerDTO) rmiBorrow.getCustomerByMedia(bookDTO);
            } else if (dvdDTO != null) {
                customerDTO = (CustomerDTO) rmiBorrow.getCustomerByMedia(dvdDTO);
            } else if (magazineDTO != null) {
                customerDTO = (CustomerDTO) rmiBorrow.getCustomerByMedia(magazineDTO);
            }
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void borrowMediaExtendAction(ActionEvent event) {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            RMIBorrow rmiBorrow = (RMIBorrow) registry.lookup("Borrow");

            if (bookDTO != null) {
                validationResult = rmiBorrow.extend(bookDTO);
            } else if (dvdDTO != null) {
                validationResult = rmiBorrow.extend(dvdDTO);
            } else if (magazineDTO != null) {
                validationResult = rmiBorrow.extend(magazineDTO);
            }

            if (!validationResult.hasErrors()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Erfolgreich Medium verlängert", ButtonType.OK);
                alert.setTitle("Success");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Stage stage = (Stage) borrowMediaCancelButton.getScene().getWindow();
                    stage.close();
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
    }

    @FXML
    void borrowMediaReturnAction(ActionEvent event) {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            RMIBorrow rmiBorrow = (RMIBorrow) registry.lookup("Borrow");

            if (bookDTO != null) {
                validationResult = rmiBorrow.handIn(bookDTO);
            } else if (dvdDTO != null) {
                validationResult = rmiBorrow.handIn(dvdDTO);
            } else if (magazineDTO != null) {
                validationResult = rmiBorrow.handIn(magazineDTO);
            }

            if (!validationResult.hasErrors()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Erfolgreich Medium zurückgegeben", ButtonType.OK);
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
    }

    public String setErrorMessage(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < validationResult.getErrorMessages().size(); i++){
            sb.append(validationResult.getErrorMessages().get(i).toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
