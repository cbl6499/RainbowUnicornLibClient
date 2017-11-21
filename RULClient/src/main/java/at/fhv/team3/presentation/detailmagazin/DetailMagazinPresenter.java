package at.fhv.team3.presentation.detailmagazin;

import at.fhv.team3.application.LoggedInUser;
import at.fhv.team3.application.ServerIP;
import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.domain.dto.MagazineDTO;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaPresenter;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaView;
import at.fhv.team3.presentation.customermanagement.CustomerManagementView;
import at.fhv.team3.presentation.detaildvd.DetailDvdPresenter;
import at.fhv.team3.presentation.detaildvd.DetailDvdView;
import at.fhv.team3.presentation.home.HomePresenter;
import at.fhv.team3.presentation.home.HomeView;
import at.fhv.team3.presentation.bookingMedia.BookingMediaPresenter;
import at.fhv.team3.presentation.bookingMedia.BookingMediaView;
import at.fhv.team3.presentation.returnOrExtend.ReturnOrExtendPresenter;
import at.fhv.team3.presentation.returnOrExtend.ReturnOrExtendView;
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

public class DetailMagazinPresenter implements Initializable {
    private LoggedInUser _loggedInUser = null;
    private DetailMagazinPresenter dmp = this;
    ObservableList<BookDTO> _homebook;
    ObservableList<DvdDTO> _homedvds;
    ObservableList<MagazineDTO> _homemagazines;
    ObservableList<MagazineDTO> mediaMagazines;
    private ServerIP serverIP;
    private String host;

    public void initialize(URL location, ResourceBundle resources) {
        detailMagazineTable.getColumns().clear();
        detailMagazineTable.getColumns().addAll(magazineShelfPos,magazineStatus);

        serverIP = ServerIP.getInstance();
        host = serverIP.getServer();

        //Login
        _loggedInUser = LoggedInUser.getInstance();
        if(_loggedInUser.isLoggedIn() == false){
            LogoutButton.setVisible(false);
            BookingButton.setVisible(false);
            CustomerManagementButton.setVisible(false);
        }
    }

    @FXML
    private TextField titel;

    @FXML
    private TextField verlag;

    @FXML
    private TextField edition;

    @FXML
    private ImageView pictureUrl;

    @FXML
    private TableView<MagazineDTO> detailMagazineTable;

    @FXML
    private TableColumn<MagazineDTO, String> magazineShelfPos;

    @FXML
    private TableColumn<MagazineDTO, String> magazineStatus;

    @FXML
    private Button DetailMagazineBackButton;

    @FXML
    private Button CustomerManagementButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button BookingButton;

    @FXML
    void handleDetailMagazineBackButton(ActionEvent event) {
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) DetailMagazineBackButton.getScene().getWindow();
        stage.setHeight(DetailMagazineBackButton.getScene().getWindow().getHeight());
        stage.setWidth(DetailMagazineBackButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        HomePresenter homePresenter = (HomePresenter) hv.getPresenter();
        homePresenter.reload(_homebook, _homedvds, _homemagazines);
        stage.show();
    }

    @FXML
    public void handleButtonActionLogout(){
        _loggedInUser.setUser(null);
        reload();
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

    public void setInfo(MagazineDTO magazine){
        if (magazine.getTitle() != null) {
            titel.setText(magazine.getTitle());
        }
        if (magazine.getPublisher() != null) {
            verlag.setText(magazine.getPublisher());
        }
        if (magazine.getEdition() != null) {
            edition.setText(magazine.getEdition());
        }
        if(magazine.getPictureURL() != null && !(magazine.getPictureURL().isEmpty())){
            pictureUrl.setImage(new Image(magazine.getPictureURL()));
        }
        magazineShelfPos.setCellValueFactory(new PropertyValueFactory<>("shelfPos"));
        magazineStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        if(!magazine.getTitle().isEmpty()) {
            try {
                Registry registry = LocateRegistry.getRegistry(host, 1099);
                RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

                ArrayList<MagazineDTO> magazineArrayList = searchMedia.getMagazinesByTitleAndEdition(titel.getText(), edition.getText());

                mediaMagazines = FXCollections.observableArrayList();
                for (int i = 0; i < magazineArrayList.size(); i++) {
                    HashMap<String, String> magazineResult = magazineArrayList.get(i).getAllData();
                    mediaMagazines.add(new MagazineDTO(Integer.parseInt(magazineResult.get("id")), magazineResult.get("title"), magazineResult.get("edition"),
                            magazineResult.get("publisher"), magazineResult.get("pictureURL"), magazineResult.get("shelfPos"), magazineResult.get("available")));

                }
                detailMagazineTable.setItems(mediaMagazines);

            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{

        }
    }

    public void setLastSearch(ObservableList<BookDTO> books, ObservableList<DvdDTO> dvds,ObservableList<MagazineDTO> magazines){
        _homebook = books;
        _homedvds = dvds;
        _homemagazines = magazines;
    }

    @FXML
    void clickBorrowMagazine(MouseEvent event) {
        if(_loggedInUser.isLoggedIn() == true) {
            detailMagazineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 1) {
                        MagazineDTO selectedItem = detailMagazineTable.getSelectionModel().getSelectedItem();
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
                            borrowMediaPresenter.setMagazineDTO(selectedItem);
                            borrowMediaPresenter.setMagazinPresnter(dmp);
                        }else{
                            ReturnOrExtendView rev = new ReturnOrExtendView();
                            Stage stage = new Stage();
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.setScene(new Scene(rev.getView()));
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            ReturnOrExtendPresenter returnOrExtendPresenter = (ReturnOrExtendPresenter) rev.getPresenter();
                            returnOrExtendPresenter.setMagazineDTO(selectedItem);
                            returnOrExtendPresenter.setMagazinPresnter(dmp);
                            returnOrExtendPresenter.setInfo();
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
                    }
                }
            });
        }
        //do nothing
    }

    @FXML
    private void handleButtonActionBooking() {
        Boolean oneItemAvailable = false;
        for (MagazineDTO magazine: mediaMagazines) {
            if(magazine.isAvailable() == true){
                oneItemAvailable = true;
            }
        }
        if(oneItemAvailable == true){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Es gibt noch Exemplare zum Ausleihen", ButtonType.OK);
            alert.setTitle("Achtung");
            alert.setHeaderText("Reservieren nicht möglich");
            alert.showAndWait();
        } else{
            BookingMediaView rm = new BookingMediaView();
            Stage newstage = new Stage();
            newstage.initModality(Modality.WINDOW_MODAL);
            newstage.setScene(new Scene(rm.getView()));
            newstage.setResizable(false);
            newstage.initModality(Modality.APPLICATION_MODAL);
            BookingMediaPresenter BookingMediaPresenter = (BookingMediaPresenter) rm.getPresenter();
            BookingMediaPresenter.setMagazineDTO(mediaMagazines.get(0));
            newstage.show();
        }
    }

    public void reload(){
        DetailMagazinView dmv = new DetailMagazinView();
        Scene scene = new Scene(dmv.getView());
        DetailMagazinPresenter ddp = (DetailMagazinPresenter) dmv.getPresenter();
        ddp.setInfo(mediaMagazines.get(0));
        ddp.setLastSearch(_homebook,_homedvds,_homemagazines);
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.setHeight(LogoutButton.getScene().getWindow().getHeight());
        stage.setWidth(LogoutButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }
}
