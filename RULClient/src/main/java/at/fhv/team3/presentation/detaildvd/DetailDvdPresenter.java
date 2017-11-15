package at.fhv.team3.presentation.detaildvd;

import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.domain.dto.MagazineDTO;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaPresenter;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaView;
import at.fhv.team3.presentation.customermanagement.CustomerManagementView;
import at.fhv.team3.presentation.home.HomePresenter;
import at.fhv.team3.presentation.home.HomeView;
import at.fhv.team3.presentation.rentMedia.RentMediaPresenter;
import at.fhv.team3.presentation.rentMedia.RentMediaView;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class DetailDvdPresenter implements Initializable {
    ObservableList<BookDTO> _homebooks;
    ObservableList<DvdDTO> _homedvds;
    ObservableList<MagazineDTO> _homemagazines;
    ObservableList<DvdDTO> mediaDvds;


    public void initialize(URL location, ResourceBundle resources) {
        detailDvdTable.getColumns().clear();
        detailDvdTable.getColumns().addAll(dvdShelfPos,dvdStatus);
    }

    @FXML
    private TextField titel;

    @FXML
    private TextField regisseur;

    @FXML
    private Button DetailDvdBackButton;

    @FXML
    private TableView<DvdDTO> detailDvdTable;


    @FXML
    private TableColumn<DvdDTO, String> dvdShelfPos;

    @FXML
    private TableColumn<DvdDTO, String> dvdStatus;


    @FXML
    private ImageView pictureUrl;

    @FXML
    private Button CustomerManagementButton;

    @FXML
    private Button RentButton;

    @FXML
    private void handleDetailDvdBackButton() {
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) DetailDvdBackButton.getScene().getWindow();
        stage.setHeight(DetailDvdBackButton.getScene().getWindow().getHeight());
        stage.setWidth(DetailDvdBackButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        HomePresenter homePresenter = (HomePresenter) hv.getPresenter();
        homePresenter.reload(_homebooks, _homedvds, _homemagazines);
        stage.show();
    }

    @FXML
    private void handleButtonActionCustomerManagement(ActionEvent event) {
        CustomerManagementView cm = new CustomerManagementView();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(cm.getView()));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Ihre Eingaben gehen verloren", ButtonType.CANCEL, ButtonType.OK);
                alert.setTitle("Attention");
                alert.setHeaderText("Wollen Sie wirklich abbrechen?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    stage.close();
                } else {
                    event.consume();
                }
            }
        });
        stage.show();
    }

    public void setInfo(DvdDTO dvd){
        if (dvd.getTitle() != null) {
            titel.setText(dvd.getTitle());
        }
        if (dvd.getRegisseur() != null) {
            regisseur.setText(dvd.getRegisseur());
        }
        if(dvd.getPictureURL() != null && !(dvd.getPictureURL().isEmpty())){
            pictureUrl.setImage(new Image(dvd.getPictureURL()));
        }
        dvdShelfPos.setCellValueFactory(new PropertyValueFactory<>("shelfPos"));
        dvdStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        if(!titel.getText().isEmpty()) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

                ArrayList<DvdDTO> dvdArrayList = searchMedia.getDvdByTitle(titel.getText());

                // buch hashmap iterieren und daten holen
                mediaDvds = FXCollections.observableArrayList();
                for (int i = 0; i < dvdArrayList.size(); i++) {
                    HashMap<String, String> dvdResult = dvdArrayList.get(i).getAllData();
                    mediaDvds.add(new DvdDTO(Integer.parseInt(dvdResult.get("id")), dvdResult.get("title"), dvdResult.get("regisseur"),
                            dvdResult.get("pictureURL"), dvdResult.get("shelfPos"), dvdResult.get("available")));
                }
                detailDvdTable.setItems(mediaDvds);

            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{

        }
    }

    public void setLastSearch(ObservableList<BookDTO> books, ObservableList<DvdDTO> dvds,ObservableList<MagazineDTO> magazines){
        _homebooks = books;
        _homedvds = dvds;
        _homemagazines = magazines;
    }

    @FXML
    void clickBorrowDvd(MouseEvent event) {
        detailDvdTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    DvdDTO selectedItem = detailDvdTable.getSelectionModel().getSelectedItem();
                    System.out.println(selectedItem.getStatus());
                    if (selectedItem.getStatus().equals("Vorhanden")) {
                        BorrowMediaView bmp = new BorrowMediaView();
                        Stage stage = new Stage();
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.setScene(new Scene(bmp.getView()));
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Ihre Eingaben gehen verloren", ButtonType.CANCEL, ButtonType.OK);
                                alert.setTitle("Attention");
                                alert.setHeaderText("Wollen Sie wirklich abbrechen?");

                                Optional<ButtonType> result = alert.showAndWait();

                                if (result.get() == ButtonType.OK) {
                                    stage.close();
                                } else {
                                    event.consume();
                                }
                            }
                        });
                        stage.show();
                        BorrowMediaPresenter borrowMediaPresenter = (BorrowMediaPresenter) bmp.getPresenter();
                        borrowMediaPresenter.setDvdDTO(selectedItem);
                    }
                }
            }
        });
    }

    @FXML
    private void handleButtonActionRent() {
        Boolean oneItemAvailable = false;
        for (DvdDTO dvd: mediaDvds) {
            if(dvd.isAvailable() == true){
                oneItemAvailable = true;
            }
        }
        if(oneItemAvailable == true){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Es gibt noch Exemplare zum Ausleihen", ButtonType.OK);
            alert.setTitle("Achtung");
            alert.setHeaderText("Reservieren nicht möglich");
            alert.showAndWait();
        } else{
            RentMediaView rm = new RentMediaView();
            Stage newstage = new Stage();
            newstage.initModality(Modality.WINDOW_MODAL);
            newstage.setScene(new Scene(rm.getView()));
            newstage.setResizable(false);
            newstage.initModality(Modality.APPLICATION_MODAL);
            RentMediaPresenter rentMediaPresenter = (RentMediaPresenter) rm.getPresenter();
            rentMediaPresenter.setDvdDTO(mediaDvds.get(0));
            newstage.show();
        }
    }
}
