package at.fhv.team3.presentation.home;

        import at.fhv.team3.application.LoggedInUser;
        import at.fhv.team3.application.ServerIP;
        import at.fhv.team3.presentation.detailbook.DetailBookPresenter;
        import at.fhv.team3.presentation.detailbook.DetailBookView;
        import at.fhv.team3.presentation.detailmagazin.DetailMagazinPresenter;
        import at.fhv.team3.presentation.detailmagazin.DetailMagazinView;
        import at.fhv.team3.presentation.detaildvd.DetailDvdPresenter;
        import at.fhv.team3.presentation.detaildvd.DetailDvdView;
        import at.fhv.team3.presentation.message.MessageView;
        import at.fhv.team3.rmi.interfaces.RMIMediaSearch;
        import at.fhv.team3.domain.dto.BookDTO;
        import at.fhv.team3.domain.dto.DTO;
        import at.fhv.team3.domain.dto.DvdDTO;
        import at.fhv.team3.domain.dto.MagazineDTO;
        import at.fhv.team3.presentation.bibinfo.BibInfoView;
        import at.fhv.team3.presentation.customermanagement.CustomerManagementView;
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
        import javafx.scene.layout.GridPane;
        import javafx.scene.layout.Pane;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import javafx.stage.WindowEvent;

        import java.net.URL;
        import java.rmi.Naming;
        import java.rmi.registry.LocateRegistry;
        import java.rmi.registry.Registry;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Optional;
        import java.util.ResourceBundle;

public class HomePresenter implements Initializable {
    private LoggedInUser _loggedInUser = null;
    private ObservableList<BookDTO> _books;
    private ObservableList<DvdDTO> _dvds;
    private ObservableList<MagazineDTO> _magazines;
    private ServerIP serverIP;
    private String host;

    public void initialize(URL location, ResourceBundle resources) {
        bookTable.getColumns();
        bookTable.setPlaceholder(new Label("Bitte suchen!"));
        dvdTable.getColumns();
        dvdTable.setPlaceholder(new Label("Bitte suchen!"));
        magazineTable.getColumns();
        magazineTable.setPlaceholder(new Label("Bitte suchen!"));

        serverIP = ServerIP.getInstance();
        host = serverIP.getServer();

        //Login
        _loggedInUser = LoggedInUser.getInstance();
        if(_loggedInUser.isLoggedIn() == false){
            LogoutButton.setVisible(false);
            CustomerManagementButton.setVisible(false);
            MessagePane1.setVisible(false);
            MessagePane2.setVisible(false);
        }

    }

    @FXML
    private TabPane tabPane;

    @FXML
    private TableView<BookDTO> bookTable;

    @FXML
    private TableColumn<BookDTO, String> bookTitle;

    @FXML
    private TableColumn<BookDTO, String> bookAuthor;

    @FXML
    private TableColumn<BookDTO, String> bookIsbn;

    @FXML
    private TableView<DvdDTO> dvdTable;

    @FXML
    private TableColumn<DvdDTO, String> dvdTitle;

    @FXML
    private TableColumn<DvdDTO, String> dvdRegisseur;

    @FXML
    private TableView<MagazineDTO> magazineTable;

    @FXML
    private TableColumn<MagazineDTO, String> magazineTitle;

    @FXML
    private TableColumn<MagazineDTO, String> magazineEdition;

    @FXML
    private Button searchButton;

    @FXML
    private Button messageButton;

    @FXML
    private Label messageCounter;

    @FXML
    private TextField searchField;

    @FXML
    private Button BibInfoButton;

    @FXML
    private Button CustomerManagementButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private GridPane MessagePane1;

    @FXML
    private Pane MessagePane2;

    // Bibinfo und Login Seite wird geöffnet
    @FXML
    private void handleButtonActionBibInfo(ActionEvent event) {
        BibInfoView bi = new BibInfoView();
        Scene scene = new Scene(bi.getView());
        Stage stage = (Stage) BibInfoButton.getScene().getWindow();
        stage.setHeight(BibInfoButton.getScene().getWindow().getHeight());
        stage.setWidth(BibInfoButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }

    // Benutzer wird ausgelogged
    @FXML
    public void handleButtonActionLogout(){
        _loggedInUser.setUser(null);
        reload();
    }

    // Leitet den Benutzer zurück Nachrichten weiter
    @FXML
    private void handleButtonActionToMessage() {
        MessageView mv = new MessageView();
        Scene scene = new Scene(mv.getView());
        Stage stage = (Stage) messageButton.getScene().getWindow();
        stage.setHeight(messageButton.getScene().getWindow().getHeight());
        stage.setWidth(messageButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }

    // Kundenverwaltung wird geöffnet
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



    // Die Detailansicht eines ausgeqählten Buchs wird geöffnet
    @FXML
    public void clickdetailbook(){
        bookTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    BookDTO selectedItem = bookTable.getSelectionModel().getSelectedItem();
                    DetailBookView db = new DetailBookView();
                    Scene scene = new Scene(db.getView());
                    Stage stage = (Stage) bookTable.getScene().getWindow();
                    stage.setHeight(bookTable.getScene().getWindow().getHeight());
                    stage.setWidth(bookTable.getScene().getWindow().getWidth());
                    stage.setScene(scene);
                    DetailBookPresenter detailBookPresenter = (DetailBookPresenter) db.getPresenter();
                    detailBookPresenter.setInfo(selectedItem);
                    detailBookPresenter.setLastSearch(_books,_dvds,_magazines);
                    stage.show();
                }
            }
        });
    }

    // Die Detailansicht einer ausgeqählten DVD wird geöffnet
    @FXML
    public void clickdetaildvd(){
        dvdTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (/*event.isPrimaryButtonDown() &&*/ event.getClickCount() == 1) {
                    DvdDTO selectedItem = dvdTable.getSelectionModel().getSelectedItem();
                    DetailDvdView dd = new DetailDvdView();
                    Scene scene = new Scene(dd.getView());
                    Stage stage = (Stage) dvdTable.getScene().getWindow();
                    stage.setHeight(dvdTable.getScene().getWindow().getHeight());
                    stage.setWidth(dvdTable.getScene().getWindow().getWidth());
                    stage.setScene(scene);
                    DetailDvdPresenter detailDvdPresenter = (DetailDvdPresenter) dd.getPresenter();
                    detailDvdPresenter.setInfo(selectedItem);
                    detailDvdPresenter.setLastSearch(_books,_dvds,_magazines);
                    stage.show();
                }
            }
        });
    }

    // Die Detailansicht eines ausgeqählten Magazins wird geöffnet
    @FXML
    void clickdetailmagazine(MouseEvent event) {
        magazineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    MagazineDTO selectedItem = magazineTable.getSelectionModel().getSelectedItem();
                    DetailMagazinView db = new DetailMagazinView();
                    Scene scene = new Scene(db.getView());
                    Stage stage = (Stage) magazineTable.getScene().getWindow();
                    stage.setHeight(magazineTable.getScene().getWindow().getHeight());
                    stage.setWidth(magazineTable.getScene().getWindow().getWidth());
                    stage.setScene(scene);
                    DetailMagazinPresenter detailMagazinPresenter = (DetailMagazinPresenter) db.getPresenter();
                    detailMagazinPresenter.setInfo(selectedItem);
                    detailMagazinPresenter.setLastSearch(_books,_dvds,_magazines);
                    stage.show();
                }
            }
        });
    }

    // Durch das betätigen der Enter Taste wird die Suche gestartet
    @FXML
    public void searchTroughEnter() {
        searchButton.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    search();
                }
            }
        });
    }

    // Es wird nach einem Medium gesucht und es werden die Resultate in Tabelle angegeben
    @FXML
    public void search() {

        bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        dvdTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        dvdRegisseur.setCellValueFactory(new PropertyValueFactory<>("regisseur"));

        magazineTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        magazineEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));


        if(!searchField.getText().isEmpty() && !searchField.getText().equals(" ")) {
            try {
                Registry registry = LocateRegistry.getRegistry(host, 1099);
                RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

                    ArrayList<ArrayList<DTO>> allMedias = searchMedia.search(searchField.getText());

                    ArrayList<DTO> bookArrayList= allMedias.get(0);
                    ArrayList<DTO> dvdArrayList = allMedias.get(1);
                    ArrayList<DTO> magazineArrayList = allMedias.get(2);

                    _books = FXCollections.observableArrayList();
                    for (int i = 0; i < bookArrayList.size(); i++) {
                        HashMap<String, String> bookResult = bookArrayList.get(i).getAllData();
                        BookDTO tempBook = new BookDTO(Integer.parseInt(bookResult.get("id")), bookResult.get("title"), bookResult.get("publisher"), bookResult.get("author"),
                                bookResult.get("isbn"), bookResult.get("edition"), bookResult.get("pictureURL"), bookResult.get("shelfPos"));
                        Boolean bookfound = false;
                        for(int j = 0; j < _books.size(); j++){
                            if(_books.get(j).equals(tempBook)){
                                bookfound = true;
                            }
                        }
                        if(!bookfound){
                            _books.add(tempBook);
                        }
                    }

                    _dvds = FXCollections.observableArrayList();
                    for (int i = 0; i < dvdArrayList.size(); i++) {
                        HashMap<String, String> dvdResult = dvdArrayList.get(i).getAllData();
                        DvdDTO tempDvd = new DvdDTO(Integer.parseInt(dvdResult.get("id")), dvdResult.get("title"), dvdResult.get("regisseur"),
                                dvdResult.get("pictureURL"), dvdResult.get("shelfPos"));
                        Boolean dvdfound = false;
                        for(int j = 0; j < _dvds.size(); j++){
                            if(_dvds.get(j).equals(tempDvd)){
                                dvdfound = true;
                            }
                        }
                        if(!dvdfound){
                            _dvds.add(tempDvd);
                        }
                    }

                _magazines =  FXCollections.observableArrayList();
                    for (int i = 0; i < magazineArrayList.size(); i++) {
                        HashMap<String, String> magazineResult = magazineArrayList.get(i).getAllData();
                        MagazineDTO tempMagazine = new MagazineDTO(Integer.parseInt(magazineResult.get("id")), magazineResult.get("title"), magazineResult.get("edition"),
                                magazineResult.get("publisher"), magazineResult.get("pictureURL"), magazineResult.get("shelfPos"));
                        Boolean magazinefound = false;
                        for(int j = 0; j < _magazines.size(); j++){
                            if(_magazines.get(j).equals(tempMagazine)){
                                magazinefound = true;
                            }
                        }
                        if(!magazinefound){
                            _magazines.add(tempMagazine);
                        }
                    }

                    if(_books.isEmpty()){
                        bookTable.setPlaceholder(new Label("Es wurde kein Buch für diesen Suchbegriff gefunden!"));
                    }

                    if(_dvds.isEmpty()){
                        dvdTable.setPlaceholder(new Label("Es wurde keine DVD für diesen Suchbegriff gefunden!"));
                    }

                    if(_magazines.isEmpty()){
                        magazineTable.setPlaceholder(new Label("Es wurde kein Magazin für diesen Suchbegriff gefunden!"));
                    }

                    if(_books.isEmpty()){
                        tabPane.getSelectionModel().select(1);
                        if(_dvds.isEmpty()){
                            tabPane.getSelectionModel().select(2);
                            if(_magazines.isEmpty()){
                                tabPane.getSelectionModel().select(0);
                            }
                        }
                    }else if(!_books.isEmpty()){
                        tabPane.getSelectionModel().select(0);
                    }


                    if(bookTable.getColumns().isEmpty() && dvdTable.getColumns().isEmpty() && magazineTable.getColumns().isEmpty()){
                        bookTable.getColumns().setAll(bookTitle, bookAuthor, bookIsbn);
                        dvdTable.getColumns().setAll(dvdTitle, dvdRegisseur);
                        magazineTable.getColumns().setAll(magazineTitle, magazineEdition);
                    }
              
                    bookTable.setItems(_books);

                    dvdTable.setItems(_dvds);

                    magazineTable.setItems(_magazines);


            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }

        }else{
            bookTable.getColumns().clear();
            bookTable.setPlaceholder(new Label("Eine leere Suche ergibt kein Ergebnis! Bitte geben sie einen Suchbegriff ein!"));
            dvdTable.getColumns().clear();
            dvdTable.setPlaceholder(new Label("Eine leere Suche ergibt kein Ergebnis! Bitte geben sie einen Suchbegriff ein!"));
            magazineTable.getColumns().clear();
            magazineTable.setPlaceholder(new Label("Eine leere Suche ergibt kein Ergebnis! Bitte geben sie einen Suchbegriff ein!"));
        }
    }

    // Die Resultate der zuvor durchgeführten Suche werden angezeigt
    public void reload(ObservableList<BookDTO> books, ObservableList<DvdDTO> dvds,ObservableList<MagazineDTO> magazines){
        bookTable.getColumns();
        dvdTable.getColumns();
        magazineTable.getColumns();

        bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        dvdTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        dvdRegisseur.setCellValueFactory(new PropertyValueFactory<>("regisseur"));

        magazineTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        magazineEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));

        _magazines = magazines;
        _dvds = dvds;
        _books = books;

        bookTable.setItems(_books);
        dvdTable.setItems(_dvds);
        magazineTable.setItems(_magazines);
    }

    public void reload(){
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        HomePresenter hp = (HomePresenter) hv.getPresenter();
        hp.reload(_books,_dvds,_magazines);
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.setHeight(LogoutButton.getScene().getWindow().getHeight());
        stage.setWidth(LogoutButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }
}

