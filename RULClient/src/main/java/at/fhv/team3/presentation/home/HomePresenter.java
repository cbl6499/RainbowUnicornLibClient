package at.fhv.team3.presentation.home;

        import at.fhv.team3.presentation.detailbook.DetailBookPresenter;
        import at.fhv.team3.presentation.detailbook.DetailBookView;
        import at.fhv.team3.presentation.detailmagazin.DetailMagazinPresenter;
        import at.fhv.team3.presentation.detailmagazin.DetailMagazinView;
        import at.fhv.team3.presentation.detaildvd.DetailDvdPresenter;
        import at.fhv.team3.presentation.detaildvd.DetailDvdView;
        import at.fhv.team3.rmi.interfaces.RMIMediaSearch;
        import at.fhv.team3.domain.dto.BookDTO;
        import at.fhv.team3.domain.dto.DTO;
        import at.fhv.team3.domain.dto.DvdDTO;
        import at.fhv.team3.domain.dto.MagazineDTO;
        import at.fhv.team3.presentation.bibinfo.BibInfoView;
        import at.fhv.team3.presentation.costumermanagement.CostumerManagementView;
        import javafx.beans.property.SimpleStringProperty;
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

public class HomePresenter implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
        bookTable.getColumns();
        bookTable.setPlaceholder(new Label("Bitte suchen!"));
        dvdTable.getColumns();
        dvdTable.setPlaceholder(new Label("Bitte suchen!"));
        magazineTable.getColumns();
        magazineTable.setPlaceholder(new Label("Bitte suchen!"));
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
    private TextField searchField;

    @FXML
    private Button BibInfoButton;

    @FXML
    private Button CostumerManagementButton;



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


    @FXML
    private void handleButtonActionCostumerManagement(ActionEvent event) {
        CostumerManagementView cm = new CostumerManagementView();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(cm.getView()));
        stage.setResizable(false);
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
                    stage.show();
                }
            }
        });
    }

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
                    stage.show();
                }
            }
        });
    }

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
                    stage.show();
                }
            }
        });
    }
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


    @FXML
    public void search() {

        bookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        dvdTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        dvdRegisseur.setCellValueFactory(new PropertyValueFactory<>("regisseur"));

        magazineTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        magazineEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));



        if(!searchField.getText().isEmpty()) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

                    ArrayList<ArrayList<DTO>> allMedias = searchMedia.search(searchField.getText());

                    ArrayList<DTO> bookArrayList= allMedias.get(0);
                    ArrayList<DTO> dvdArrayList = allMedias.get(1);
                    ArrayList<DTO> magazineArrayList = allMedias.get(2);

                    ObservableList<BookDTO> books = FXCollections.observableArrayList();


                    for (int i = 0; i < bookArrayList.size(); i++) {
                        HashMap<String, String> bookResult = bookArrayList.get(i).getAllData();
                        BookDTO tempBook = new BookDTO(Integer.parseInt(bookResult.get("id")), bookResult.get("title"), bookResult.get("publisher"), bookResult.get("author"),
                                bookResult.get("isbn"), bookResult.get("edition"), bookResult.get("pictureURL"), bookResult.get("shelfPos"));
                        Boolean bookfound = false;
                        for(int j = 0; j < books.size(); j++){
                            if(books.get(j).equals(tempBook)){
                                bookfound = true;
                            }
                        }
                        if(!bookfound){
                            books.add(tempBook);
                        }
                    }

                    ObservableList<DvdDTO> dvds = FXCollections.observableArrayList();
                    for (int i = 0; i < dvdArrayList.size(); i++) {
                        HashMap<String, String> dvdResult = dvdArrayList.get(i).getAllData();
                        DvdDTO tempDvd = new DvdDTO(Integer.parseInt(dvdResult.get("id")), dvdResult.get("title"), dvdResult.get("regisseur"),
                                dvdResult.get("pictureURL"), dvdResult.get("shelfPos"));
                        Boolean dvdfound = false;
                        for(int j = 0; j < dvds.size(); j++){
                            if(dvds.get(j).equals(tempDvd)){
                                dvdfound = true;
                            }
                        }
                        if(!dvdfound){
                            dvds.add(tempDvd);
                        }
                    }

                    ObservableList<MagazineDTO> magazines = FXCollections.observableArrayList();
                    for (int i = 0; i < magazineArrayList.size(); i++) {
                        HashMap<String, String> magazineResult = magazineArrayList.get(i).getAllData();
                        MagazineDTO tempMagazine = new MagazineDTO(Integer.parseInt(magazineResult.get("id")), magazineResult.get("title"), magazineResult.get("edition"),
                                magazineResult.get("publisher"), magazineResult.get("pictureURL"), magazineResult.get("shelfPos"));
                        Boolean magazinefound = false;
                        for(int j = 0; j < magazines.size(); j++){
                            if(magazines.get(j).equals(tempMagazine)){
                                magazinefound = true;
                            }
                        }
                        if(!magazinefound){
                            magazines.add(tempMagazine);
                        }
                    }

                    if(books.isEmpty()){
                        tabPane.getSelectionModel().select(1);
                    }
                    if(dvds.isEmpty()){
                        tabPane.getSelectionModel().select(2);
                    }
                    if(magazines.isEmpty()){
                        tabPane.getSelectionModel().select(0);
                    }

                    bookTable.setItems(books);

                    dvdTable.setItems(dvds);

                    magazineTable.setItems(magazines);


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
}

