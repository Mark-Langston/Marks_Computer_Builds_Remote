package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.ComputerBuild;

public class AddEditDialogController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField caseTypeField;
    @FXML
    private TextField motherboardField;
    @FXML
    private TextField cpuField;
    @FXML
    private TextField cpuCoolerField;
    @FXML
    private TextField ramField;
    @FXML
    private TextField gpuField;
    @FXML
    private TextField powerSupplyField;
    @FXML
    private TextField massStorageField;

    private Stage dialogStage;
    private boolean okClicked = false;
    private ComputerBuild computerBuild;

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            if (computerBuild == null) {
                computerBuild = new ComputerBuild(
                        titleField.getText(),
                        caseTypeField.getText(),
                        motherboardField.getText(),
                        cpuField.getText(),
                        cpuCoolerField.getText(),
                        ramField.getText(),
                        gpuField.getText(),
                        powerSupplyField.getText(),
                        massStorageField.getText()
                );
            } else {
                computerBuild.setTitle(titleField.getText());
                computerBuild.setCaseType(caseTypeField.getText());
                computerBuild.setMotherboard(motherboardField.getText());
                computerBuild.setCpu(cpuField.getText());
                computerBuild.setCpuCooler(cpuCoolerField.getText());
                computerBuild.setRam(ramField.getText());
                computerBuild.setGpu(gpuField.getText());
                computerBuild.setPowerSupply(powerSupplyField.getText());
                computerBuild.setMassStorage(massStorageField.getText());
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public ComputerBuild getComputerBuild() {
        return computerBuild;
    }

    public void setComputerBuild(ComputerBuild computerBuild) {
        this.computerBuild = computerBuild;
        if (computerBuild != null) {
            titleField.setText(computerBuild.getTitle());
            caseTypeField.setText(computerBuild.getCaseType());
            motherboardField.setText(computerBuild.getMotherboard());
            cpuField.setText(computerBuild.getCpu());
            cpuCoolerField.setText(computerBuild.getCpuCooler());
            ramField.setText(computerBuild.getRam());
            gpuField.setText(computerBuild.getGpu());
            powerSupplyField.setText(computerBuild.getPowerSupply());
            massStorageField.setText(computerBuild.getMassStorage());
        }
    }

    private boolean isInputValid() {
        // Implement validation logic here
        return true;
    }
}