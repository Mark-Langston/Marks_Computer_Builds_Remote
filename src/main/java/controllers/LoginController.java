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

    private MainApp mainApp;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate input fields
        if (username.isEmpty() || password.isEmpty()) {
            showErrorAlert("Input Error", "Username and password cannot be empty.");
            return;
        }

        try {
            // Check if user exists and validate password
            if (DatabaseUtil.validateUser(username, password)) {
                mainApp.showMainScreen(); // Switch to the main screen on successful login
            } else {
                showErrorAlert("Invalid login credentials", "Please check your username and password and try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred during login. Please try again.");
        }
    }

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
