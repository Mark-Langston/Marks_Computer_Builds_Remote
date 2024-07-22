package controllers;

import com.example.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private ObservableList<ComputerBuild> computerBuilds = FXCollections.observableArrayList();
    private MainApp mainApp;

    @FXML
    public void initialize() {
        // Set up cell value factories for the columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        caseTypeColumn.setCellValueFactory(cellData -> cellData.getValue().caseTypeProperty());
        motherboardColumn.setCellValueFactory(cellData -> cellData.getValue().motherboardProperty());
        cpuColumn.setCellValueFactory(cellData -> cellData.getValue().cpuProperty());
        cpuCoolerColumn.setCellValueFactory(cellData -> cellData.getValue().cpuCoolerProperty());
        ramColumn.setCellValueFactory(cellData -> cellData.getValue().ramProperty());
        gpuColumn.setCellValueFactory(cellData -> cellData.getValue().gpuProperty());
        powerSupplyColumn.setCellValueFactory(cellData -> cellData.getValue().powerSupplyProperty());
        massStorageColumn.setCellValueFactory(cellData -> cellData.getValue().massStorageProperty());

        // Set the items in the TableView
        tableView.setItems(computerBuilds);

        // Load the data from the database
        loadComputerBuilds();
    }

    private void loadComputerBuilds() {
        try {
            List<ComputerBuild> builds = DatabaseUtil.getAllComputerBuilds();
            computerBuilds.setAll(builds);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load computer builds from the database.");
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void handleAdd() {
        System.out.println("Handle Add called");
        showAddEditDialog(null, "Add Computer Build");
    }

    @FXML
    private void handleEdit() {
        ComputerBuild selectedBuild = tableView.getSelectionModel().getSelectedItem();
        if (selectedBuild != null) {
            System.out.println("Handle Edit called");
            showAddEditDialog(selectedBuild, "Edit Computer Build");
        } else {
            showAlert("No Selection", "Please select a computer build to edit.");
        }
    }

    private void showAddEditDialog(ComputerBuild computerBuild, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddEditDialog.fxml"));
            Parent root = loader.load();

            AddEditDialogController controller = loader.getController();
            controller.setDialogStage(new Stage());
            controller.setComputerBuild(computerBuild);

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            dialogStage.setScene(new Scene(root));
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                ComputerBuild updatedBuild = controller.getComputerBuild();
                if (computerBuild == null) {
                    // Adding new build
                    System.out.println("Adding new build");
                    computerBuilds.add(updatedBuild);
                    saveComputerBuild(updatedBuild);
                } else {
                    // Editing existing build
                    System.out.println("Editing existing build");
                    tableView.refresh();
                    updateComputerBuild(updatedBuild);
                }
            } else {
                System.out.println("Dialog cancelled or no OK clicked");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open the add/edit dialog.");
        }
    }

    @FXML
    private void handleRemove() {
        ComputerBuild selectedBuild = tableView.getSelectionModel().getSelectedItem();
        if (selectedBuild != null) {
            // Remove from ObservableList
            computerBuilds.remove(selectedBuild);

            // Delete from database
            deleteComputerBuild(selectedBuild);
        } else {
            showAlert("No Selection", "Please select a computer build to remove.");
        }
    }

    private void saveComputerBuild(ComputerBuild computerBuild) {
        try {
            System.out.println("Saving new build");
            DatabaseUtil.insertComputerBuild(computerBuild);
            showAlert("Success", "Computer build added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to save the computer build.");
        }
    }

    private void updateComputerBuild(ComputerBuild computerBuild) {
        try {
            System.out.println("Updating build");
            DatabaseUtil.updateComputerBuild(computerBuild);
            showAlert("Success", "Computer build updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to update the computer build.");
        }
    }

    private void deleteComputerBuild(ComputerBuild computerBuild) {
        try {
            System.out.println("Deleting build");
            DatabaseUtil.deleteComputerBuild(computerBuild.getId()); // Call the method to delete from database
            showAlert("Success", "Computer build removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to remove the computer build.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}