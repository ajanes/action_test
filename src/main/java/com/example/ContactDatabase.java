package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactDatabase {

    private Connection connect() {
        String url = "jdbc:sqlite:contacts.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS contacts (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    phone text NOT NULL\n"
                + ");";

        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addContact(String name, String phone) {
        String sql = "INSERT INTO contacts(name, phone) VALUES(?, ?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getContactByName(String name) {
        String sql = "SELECT phone FROM contacts WHERE name = ?";
        String phone = null;

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                phone = rs.getString("phone");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return phone;
    }

    public static void main(String[] args) {
        ContactDatabase db = new ContactDatabase();
        db.createTable();
        db.addContact("John", "1234567890");
        db.addContact("Jane", "0987654321");

        System.out.println("John's phone: " + db.getContactByName("John"));
        System.out.println("Jane's phone: " + db.getContactByName("Jane"));
    }
}
