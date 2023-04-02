package dev.prtjohanson.jdbc20230401;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCDriverManager {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/classicmodels", "root", "p_ssW0rd")) {
            System.out.println("We have a connection!");

            PreparedStatement pst = connection.prepareStatement("SELECT * FROM employees WHERE employeeNumber = ?");
            pst.setInt(1, 1102);

            ResultSet res = pst.executeQuery();

            while (res.next()) {
                System.out.println(res.getString("firstName"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
