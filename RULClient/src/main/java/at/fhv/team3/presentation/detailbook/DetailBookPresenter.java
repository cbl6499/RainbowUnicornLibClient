package at.fhv.team3.presentation.detailbook;

import at.fhv.team3.application.ConnectionType;
import at.fhv.team3.application.EJBConnect;
import at.fhv.team3.application.LoggedInUser;
import at.fhv.team3.application.ServerIP;
import at.fhv.team3.applicationbean.interfaces.RemoteSearchBeanFace;
import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.domain.dto.MagazineDTO;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaPresenter;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaView;
import at.fhv.team3.presentation.customermanagement.CustomerManagementView;
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

public class DetailBookPresenter implements Initializable {
    private LoggedInUser _loggedInUser = null;
    private DetailBookPresenter dbp = this;
    ObservableList<BookDTO> _homebooks;
    ObservableList<DvdDTO> _homedvds;
    ObservableList<MagazineDTO> _homemagazines;
    ObservableList<BookDTO> mediaBooks;
    private ConnectionType connectionType;
    private String connection;
    private ServerIP serverIP;
    private String host;

    public void initialize(URL location, ResourceBundle resources) {
        detailBookTable.getColumns().clear();
        detailBookTable.getColumns().addAll(bookEdition,bookShelfPos,bookStatus);

        connectionType = ConnectionType.getInstance();
        connection = connectionType.getConnection();

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
    private TextField author;

    @FXML
    private TextField isbn;

    @FXML
    private Button DetailBookBackButton;

    @FXML
    private TableView<BookDTO> detailBookTable;

    @FXML
    private TableColumn<BookDTO, String> bookEdition;

    @FXML
    private TableColumn<BookDTO, String> bookShelfPos;

    @FXML
    private TableColumn<BookDTO, String> bookStatus;

    @FXML
    private ImageView pictureUrl;

    @FXML
    private Button CustomerManagementButton;

    @FXML
    private Button BookingButton;

    @FXML
    private Button LogoutButton;

    // Es wird von der Detailansicht auf den Home View gewechselt
    @FXML
    private void handleDetailBookBackButton() {
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) DetailBookBackButton.getScene().getWindow();
        stage.setHeight(DetailBookBackButton.getScene().getWindow().getHeight());
        stage.setWidth(DetailBookBackButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        HomePresenter homePresenter = (HomePresenter) hv.getPresenter();
        homePresenter.reload(_homebooks, _homedvds, _homemagazines);
        stage.show();
    }


    // Der Benutzer wird ausgelogged
    @FXML
    public void handleButtonActionLogout(){
        _loggedInUser.setUser(null);
        reload();
    }

    // Kundenverwaltung wird gestartet
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

    // Alle Informationen zu einem bestimmten, zuvor ausgewählten Buch werden in einer Tabelle angezeit
    public void setInfo(BookDTO book){
        if (book.getTitle() != null) {
            titel.setText(book.getTitle());
        }
        if (book.getPublisher() != null) {
            verlag.setText(book.getPublisher());
        }
        if (book.getAuthor() != null) {
            author.setText(book.getAuthor());
        }
        if (book.getIsbn() != null) {
            isbn.setText(book.getIsbn());
        }
        if(book.getPictureURL() != null && !(book.getPictureURL().isEmpty())){
            pictureUrl.setImage(new Image(book.getPictureURL()));
        }
        bookEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        bookShelfPos.setCellValueFactory(new PropertyValueFactory<>("shelfPos"));
        bookStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        if(!isbn.getText().isEmpty()) {
            try {
                ArrayList<BookDTO> bookArrayList = new ArrayList<>();
                if (connection.equals("RMI")) {
                    Registry registry = LocateRegistry.getRegistry(host, 1099);
                    RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");
                    bookArrayList = searchMedia.getBooksByISBN(isbn.getText());
                } else if (connection.equals("EJB")) {
                    RemoteSearchBeanFace remoteSearchBeanFace = (RemoteSearchBeanFace) EJBConnect.connect("SearchEJB");
                    bookArrayList = remoteSearchBeanFace.getBooksByISBN(isbn.getText());
                }

                mediaBooks = FXCollections.observableArrayList();

                // buch hashmap iterieren und daten holen
                for (int i = 0; i < bookArrayList.size(); i++) {
                    HashMap<String, String> bookResult = bookArrayList.get(i).getAllData();
                    mediaBooks.add(new BookDTO(Integer.parseInt(bookResult.get("id")), bookResult.get("title"), bookResult.get("publisher"), bookResult.get("author"),
                            bookResult.get("isbn"), bookResult.get("edition"), bookResult.get("pictureURL"), bookResult.get("shelfPos"), bookResult.get("available")));
                }
                detailBookTable.setItems(mediaBooks);

            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{

        }
    }

    // Die letzten Ergebnisse bei der Suche auf dem Home View werden angegeben.
    public void setLastSearch(ObservableList<BookDTO> books, ObservableList<DvdDTO> dvds,ObservableList<MagazineDTO> magazines){
        _homebooks = books;
        _homedvds = dvds;
        _homemagazines = magazines;
    }

    // Es wird der Ausleihvorgang für ein ausgewähltes Buch gestartet
    @FXML
    void clickBorrowBook(MouseEvent event) {
        if(_loggedInUser.isLoggedIn() == true) {
            detailBookTable.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 1) {
                        BookDTO selectedItem = detailBookTable.getSelectionModel().getSelectedItem();
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
                            borrowMediaPresenter.setBookDTO(selectedItem);
                            borrowMediaPresenter.setBookPresenter(dbp);
                        }else{
                            ReturnOrExtendView rev = new ReturnOrExtendView();
                            Stage stage = new Stage();
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.setScene(new Scene(rev.getView()));
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            ReturnOrExtendPresenter returnOrExtendPresenter = (ReturnOrExtendPresenter) rev.getPresenter();
                            returnOrExtendPresenter.setBookDTO(selectedItem);
                            returnOrExtendPresenter.setBookPresenter(dbp);
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

    // Der Reservierungsvorgang wird gestartet
    @FXML
    private void handleButtonActionBooking() {
        Boolean oneItemAvailable = false;
        for (BookDTO book: mediaBooks) {
            if(book.isAvailable() == true){
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
            BookingMediaPresenter.setBookDTO(mediaBooks.get(0));
            newstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Der Reservierungsvorgang wird abgebrochen, alle eigegebenen Daten gehen verloren", ButtonType.CANCEL, ButtonType.OK);
                    alert.setTitle("Attention");
                    alert.setHeaderText("Wollen Sie wirklich abbrechen?");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        newstage.close();
                    } else {
                        event.consume();
                    }
                }
            });
            newstage.show();
        }
    }

    public void reload(){
        DetailBookView dbv = new DetailBookView();
        Scene scene = new Scene(dbv.getView());
        DetailBookPresenter dbp = (DetailBookPresenter) dbv.getPresenter();
        dbp.setInfo(mediaBooks.get(0));
        dbp.setLastSearch(_homebooks,_homedvds,_homemagazines);
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.setHeight(LogoutButton.getScene().getWindow().getHeight());
        stage.setWidth(LogoutButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }
}
