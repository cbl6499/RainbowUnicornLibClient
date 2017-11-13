package at.fhv.team3.presentation.detailmagazin;

import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.DvdDTO;
import at.fhv.team3.domain.dto.MagazineDTO;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaPresenter;
import at.fhv.team3.presentation.borrowMedia.BorrowMediaView;
import at.fhv.team3.presentation.home.HomePresenter;
import at.fhv.team3.presentation.home.HomeView;
import at.fhv.team3.rmi.interfaces.RMIMediaSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DetailMagazinPresenter {
        ObservableList<BookDTO> _books;
        ObservableList<DvdDTO> _dvds;
        ObservableList<MagazineDTO> _magazines;

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
        void handleDetailMagazineBackButton(ActionEvent event) {
            HomeView hv = new HomeView();
            Scene scene = new Scene(hv.getView());
            Stage stage = (Stage) DetailMagazineBackButton.getScene().getWindow();
            stage.setHeight(DetailMagazineBackButton.getScene().getWindow().getHeight());
            stage.setWidth(DetailMagazineBackButton.getScene().getWindow().getWidth());
            stage.setScene(scene);
            HomePresenter homePresenter = (HomePresenter) hv.getPresenter();
            homePresenter.reload(_books,_dvds,_magazines);
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
                Registry registry = LocateRegistry.getRegistry(1099);
                RMIMediaSearch searchMedia = (RMIMediaSearch) registry.lookup("Search");

                ArrayList<ArrayList<DTO>> allMedias = searchMedia.search(titel.getText());

                ArrayList<DTO> magazineArrayList = allMedias.get(2);


                ObservableList<MagazineDTO> magazines = FXCollections.observableArrayList();
                for (int i = 0; i < magazineArrayList.size(); i++) {
                    HashMap<String, String> magazineResult = magazineArrayList.get(i).getAllData();
                    magazines.add(new MagazineDTO(Integer.parseInt(magazineResult.get("id")), magazineResult.get("title"), magazineResult.get("edition"),
                            magazineResult.get("publisher"), magazineResult.get("pictureURL"), magazineResult.get("shelfPos")));
                }
                detailMagazineTable.setItems(magazines);

            } catch (Exception e) {
                System.out.println("HelloClient exception: " + e.getMessage());
                e.printStackTrace();
            }
        }else{

        }
    }

    public void setLastSearch(ObservableList<BookDTO> books, ObservableList<DvdDTO> dvds,ObservableList<MagazineDTO> magazines){
        _books = books;
        _dvds = dvds;
        _magazines = magazines;
    }

    @FXML
    void clickBorrowMagazine(MouseEvent event) {
        detailMagazineTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    MagazineDTO selectedItem = detailMagazineTable.getSelectionModel().getSelectedItem();
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
                    //borrowMediaPresenter.setInfo(selectedItem);
                    //detailBookPresenter.setLastSearch(_books,_dvds,_magazines);
                }
            }
        });

    }
}
