package com.jvramenshoppu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuModel {

    private Connection connection;

    public MenuModel(Connection connection) {
        this.connection = connection;
    }

    // Method untuk mengambil data menu dari database
    public ObservableList<Menu> getMenuList() {
        ObservableList<Menu> menuList = FXCollections.observableArrayList();
        String query = "SELECT id_menu, nama_menu, kategori_menu, harga_menu FROM menu";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idMenu = resultSet.getInt("id_menu");
                String namaMenu = resultSet.getString("nama_menu");
                String kategoriMenu = resultSet.getString("kategori_menu");
                double hargaMenu = resultSet.getDouble("harga_menu");
                Menu menu = new Menu(idMenu, namaMenu, kategoriMenu, hargaMenu);
                menuList.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }
}
