package com.jvramenshoppu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Connection connection;

    // Method to set the database connection
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @FXML
    private void loginAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (connection == null) {
            connection = DatabaseConnector.connectToDatabase();
        }

        // Fetch name from database based on username
        String name = getNameFromDatabase(username);

        boolean validLogin = validateCredentials(username, password, name, connection);

        if (validLogin) {
            try {
                switchToMain(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    private String getNameFromDatabase(String username) {
        String name = null;
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT name FROM login WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    private boolean validateCredentials(String username, String password, String name, Connection connection) {
        if (connection == null) {
            return false;
        }
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM login WHERE username = ? AND password = ? AND name = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, name);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void switchToMain(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.setUsername(name);
        Scene scene = new Scene(root);
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
