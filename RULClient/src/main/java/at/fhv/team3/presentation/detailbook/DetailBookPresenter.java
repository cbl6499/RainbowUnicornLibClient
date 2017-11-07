package at.fhv.team3.presentation.detailbook;

import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.presentation.home.HomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailBookPresenter implements Initializable {

    public void initialize(URL location, ResourceBundle resources) {

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
    }
}
