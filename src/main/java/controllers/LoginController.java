package controllers;

import com.example.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.DatabaseUtil;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Print username and password for debugging
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Check if user exists and validate password
        if (DatabaseUtil.validateUser(username, password)) {
            mainApp.showMainScreen(); // Switch to the main screen on successful login
        } else {
            // Show error message
            showErrorAlert("Invalid login credentials", "Please check your username and password and try again.");
        }
    }

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}