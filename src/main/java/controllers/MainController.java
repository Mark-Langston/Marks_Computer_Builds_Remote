package controllers;

import com.example.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.ComputerBuild;
import utils.DatabaseUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainController {

    @FXML
    private TableView<ComputerBuild> tableView;
    @FXML
    private TableColumn<ComputerBuild, String> titleColumn;
    @FXML
    private TableColumn<ComputerBuild, String> caseTypeColumn;
    @FXML
    private TableColumn<ComputerBuild, String> motherboardColumn;
    @FXML
    private TableColumn<ComputerBuild, String> cpuColumn;
    @FXML
    private TableColumn<ComputerBuild, String> cpuCoolerColumn;
    @FXML
    private TableColumn<ComputerBuild, String> ramColumn;
    @FXML
    private TableColumn<ComputerBuild, String> gpuColumn;
    @FXML
    private TableColumn<ComputerBuild, String> powerSupplyColumn;
    @FXML
    private TableColumn<ComputerBuild, String> massStorageColumn;

    private MainApp mainApp;

    private ObservableList<ComputerBuild> computerBuildsData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize the table columns.
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        caseTypeColumn.setCellValueFactory(new PropertyValueFactory<>("caseType"));
        motherboardColumn.setCellValueFactory(new PropertyValueFactory<>("motherboard"));
        cpuColumn.setCellValueFactory(new PropertyValueFactory<>("cpu"));
        cpuCoolerColumn.setCellValueFactory(new PropertyValueFactory<>("cpuCooler"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("ram"));
        gpuColumn.setCellValueFactory(new PropertyValueFactory<>("gpu"));
        powerSupplyColumn.setCellValueFactory(new PropertyValueFactory<>("powerSupply"));
        massStorageColumn.setCellValueFactory(new PropertyValueFactory<>("massStorage"));

        // Load data from the database.
        loadComputerBuilds();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void loadComputerBuilds() {
        try {
            List<ComputerBuild> builds = DatabaseUtil.getAllComputerBuilds();
            computerBuildsData.setAll(builds);
            tableView.setItems(computerBuildsData);
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Could not load data from the database.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        ComputerBuild tempBuild = new ComputerBuild();
        boolean okClicked = showAddEditDialog(tempBuild);
        if (okClicked) {
            try {
                DatabaseUtil.insertComputerBuild(tempBuild);
                computerBuildsData.add(tempBuild);
            } catch (SQLException e) {
                showErrorAlert("Database Error", "Could not save the new build to the database.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleEdit() {
        ComputerBuild selectedBuild = tableView.getSelectionModel().getSelectedItem();
        if (selectedBuild != null) {
            boolean okClicked = showAddEditDialog(selectedBuild);
            if (okClicked) {
                try {
                    DatabaseUtil.updateComputerBuild(selectedBuild);
                    loadComputerBuilds(); // Refresh the table view
                } catch (SQLException e) {
                    showErrorAlert("Database Error", "Could not update the build in the database.");
                    e.printStackTrace();
                }
            }
        } else {
            showWarningAlert("No Selection", "Please select a build in the table.");
        }
    }

    @FXML
    private void handleRemove() {
        ComputerBuild selectedBuild = tableView.getSelectionModel().getSelectedItem();
        if (selectedBuild != null) {
            try {
                DatabaseUtil.deleteComputerBuild(selectedBuild.getTitle());
                computerBuildsData.remove(selectedBuild);
            } catch (SQLException e) {
                showErrorAlert("Database Error", "Could not delete the selected build.");
                e.printStackTrace();
            }
        } else {
            showWarningAlert("No Selection", "Please select a build in the table.");
        }
    }

    private boolean showAddEditDialog(ComputerBuild computerBuild) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AddEditDialog.fxml"));
            GridPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Build");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setComputerBuild(computerBuild);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showWarningAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
