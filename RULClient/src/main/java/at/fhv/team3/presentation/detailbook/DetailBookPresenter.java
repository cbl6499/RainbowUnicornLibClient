package at.fhv.team3.presentation.detailbook;

import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.presentation.home.HomeView;
import at.fhv.team3.rmi.interfaces.RMIMediaSearch;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DetailBookPresenter implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {
        detailBookTable.getColumns().clear();
        detailBookTable.getColumns().addAll(bookEdition,bookShelfPos,bookStatus,bookAktion);
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
    private TableColumn<BookDTO, String> bookAktion;



    @FXML
    private void handleDetailBookBackButton() {
        HomeView hv = new HomeView();
        Scene scene = new Scene(hv.getView());
        Stage stage = (Stage) DetailBookBackButton.getScene().getWindow();
        stage.setHeight(DetailBookBackButton.getScene().getWindow().getHeight());
        stage.setWidth(DetailBookBackButton.getScene().getWindow().getWidth());
        stage.setScene(scene);
        stage.show();
    }

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
        bookEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
        bookShelfPos.setCellValueFactory(new PropertyValueFactory<>("shelfPos"));
        bookStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        bookAktion.setCellValueFactory(new PropertyValueFactory<>("aktion"));
        if(!isbn.getText().isEmpty()) {
            try {
                Registry registry = LocateRegistry.getRegistry(1099);
                RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

                ArrayList<ArrayList<DTO>> allMedias = searchMedia.search(isbn.getText());

                ArrayList<DTO> bookArrayList= allMedias.get(0);


                ObservableList<BookDTO> books = FXCollections.observableArrayList();

                // buch hashmap iterieren und daten holen
                System.out.println(bookArrayList.size());
                for (int i = 0; i < bookArrayList.size(); i++) {
                    HashMap<String, String> bookResult = bookArrayList.get(i).getAllData();
                    books.add(new BookDTO(Integer.parseInt(bookResult.get("id")), bookResult.get("title"), bookResult.get("publisher"), bookResult.get("author"),
                            bookResult.get("isbn"), bookResult.get("edition"), bookResult.get("pictureURL"), bookResult.get("shelfPos")));
                }
                detailBookTable.setItems(books);

            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{

        }
    }
}
