package at.fhv.team3.presentation.detailmagazin;

import at.fhv.team3.domain.dto.BookDTO;
import at.fhv.team3.domain.dto.DTO;
import at.fhv.team3.domain.dto.MagazineDTO;
import at.fhv.team3.presentation.home.HomeView;
import at.fhv.team3.rmi.interfaces.RMIMediaSearch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailMagazinPresenter {

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
        if(magazine.getPictureURL() != null){
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

}
