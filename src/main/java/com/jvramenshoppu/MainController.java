package com.jvramenshoppu;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Label welcomeLabel;

    private Connection connection;

    private String name;

    public void setUsername(String name) {
        this.name = name;
        updateWelcomeMessage();
    }

    private void updateWelcomeMessage() {
        welcomeLabel.setText("Welcome, " + name);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @FXML
    public void logoutAction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
            System.out.println("Logout action completed");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (SQLException e) {
            System.err.println("Error disconnecting from the database:");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error switching scene to login page:");
            e.printStackTrace();
        }
    }
}