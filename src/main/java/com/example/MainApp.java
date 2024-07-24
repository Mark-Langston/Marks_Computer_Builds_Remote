package com.example;

import controllers.LoginController;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MainApp extends Application {

    private Stage primaryStage;
    private static String REMOTE_URL;
    private static String REMOTE_USER;
    private static String REMOTE_PASSWORD;

    @Override
    public void start(Stage primaryStage) {
        // Load PostgreSQL driver and configuration
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected.");
            loadDatabaseConfig();
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        // Initialize database connection (optional, for testing)
        try (Connection connection = DriverManager.getConnection(REMOTE_URL, REMOTE_USER, REMOTE_PASSWORD)) {
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
            return;
        }

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Mark's Computer Builds");

        showLoginScreen();
    }

    private static void loadDatabaseConfig() {
        Properties properties = new Properties();
        try (InputStream input = MainApp.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
            REMOTE_URL = properties.getProperty("remote.db.url");
            REMOTE_USER = properties.getProperty("remote.db.user");
            REMOTE_PASSWORD = properties.getProperty("remote.db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            MainController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
