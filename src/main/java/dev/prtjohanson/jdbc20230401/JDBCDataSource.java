package dev.prtjohanson.jdbc20230401;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCDataSource {
    public static void main(String[] args) {
        try {
            DataSource ds = createDatasource();
            PreparedStatement ps = ds.getConnection().prepareStatement("SELECT * FROM employees");
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                System.out.println(r.getString("firstName"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static DataSource createDatasource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/classicmodels");
        ds.setUsername("root");
        ds.setPassword("p_ssW0rd");
        return ds;
    }
}
