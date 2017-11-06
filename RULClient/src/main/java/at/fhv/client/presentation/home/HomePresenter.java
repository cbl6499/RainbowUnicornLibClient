package at.fhv.client.presentation.home;

        import at.fhv.client.RMI.RMIMediaSearch;
        import at.fhv.client.domain.Book;
        import at.fhv.client.domain.DTO;
        import at.fhv.client.domain.Dvd;
        import at.fhv.client.domain.Magazine;
        import at.fhv.client.presentation.bibinfo.BibInfoView;
        import at.fhv.client.presentation.costumermanagement.CostumerManagementView;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
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

    }

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> bookTitle;

    @FXML
    private TableColumn<Book, String> bookAuthor;

    @FXML
    private TableColumn<Book, String> bookIsbn;

    @FXML
    private TableView<Dvd> dvdTable;

    @FXML
    private TableColumn<Dvd, String> dvdTitle;

    @FXML
    private TableColumn<Dvd, String> dvdRegisseur;

    @FXML
    private TableView<Magazine> magazineTable;

    @FXML
    private TableColumn<Magazine, String> magazineTitle;

    @FXML
    private TableColumn<Magazine, String> magazineEdition;

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
    public void search(ActionEvent event) {

        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

            if(searchField.getText()!= null) {

                ArrayList<ArrayList<DTO>> allMedias = searchMedia.search(searchField.getText());

                ArrayList<DTO> bookArrayList= allMedias.get(0);
                ArrayList<DTO> dvdArrayList = allMedias.get(1);
                ArrayList<DTO> magazineArrayList = allMedias.get(2);

                bookTitle.setCellValueFactory(new PropertyValueFactory<>("_title"));
                bookAuthor.setCellValueFactory(new PropertyValueFactory<>("_author"));
                bookIsbn.setCellValueFactory(new PropertyValueFactory<>("_isbn"));

                dvdTitle.setCellValueFactory(new PropertyValueFactory<>("_title"));
                dvdRegisseur.setCellValueFactory(new PropertyValueFactory<>("_regisseur"));

                magazineTitle.setCellValueFactory(new PropertyValueFactory<>("_title"));
                magazineEdition.setCellValueFactory(new PropertyValueFactory<>("_edition"));


                ObservableList<Book> books = FXCollections.observableArrayList();

                // buch hashmap iterieren und daten holen
                for (int i = 0; i<=bookArrayList.size(); i++) {
                    HashMap<String, String> bookResult = bookArrayList.get(i).getAllData();
                    books.add(new Book(Integer.parseInt(bookResult.get("id")), bookResult.get("title"), bookResult.get("publisher"), bookResult.get("author"),
                            bookResult.get("isbn"), bookResult.get("edition"), bookResult.get("pictureURL"), bookResult.get("shelfPos")));
                }

                ObservableList<Dvd> dvds = FXCollections.observableArrayList();
                for (int i = 0; i<=dvdArrayList.size(); i++) {
                    HashMap<String, String> dvdResult = dvdArrayList.get(i).getAllData();
                    dvds.add(new Dvd(Integer.parseInt(dvdResult.get("id")), dvdResult.get("title"), dvdResult.get("regisseur"),
                            dvdResult.get("pictureURL"), dvdResult.get("shelfPos")));
                }

                ObservableList<Magazine> magazines = FXCollections.observableArrayList();
                for (int i = 0; i<=magazineArrayList.size(); i++) {
                    HashMap<String, String> magazineResult = magazineArrayList.get(i).getAllData();
                    magazines.add(new Magazine(Integer.parseInt(magazineResult.get("id")), magazineResult.get("title"), magazineResult.get("edition"),
                            magazineResult.get("publisher"), magazineResult.get("pictureURL"), magazineResult.get("shelfPos")));
                }

                bookTable.setItems(books);

                dvdTable.setItems(dvds);

                magazineTable.setItems(magazines);

                bookTable.getColumns().addAll(bookTitle, bookAuthor, bookIsbn);

                dvdTable.getColumns().addAll(dvdTitle, dvdRegisseur);

                magazineTable.getColumns().addAll(magazineTitle, magazineEdition);
            }
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }


    }

}

