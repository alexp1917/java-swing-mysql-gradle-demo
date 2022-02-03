package org.example.swing_demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataLayer {
    private static final String CREATE_TABLE = "create table if not exists data(id int auto_increment primary key, value varchar(100));";
    private static final String INSERT_DATA = "insert into data(value) values ('abcd'), ('1234');";
    private static final String SELECT_DATA = "select * from data;";
    private final Connection connection;

    public DataLayer() {
        this(true);
    }

    public DataLayer(boolean addTestData) {
        // docker run -it --rm -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "root";
        String database = "app_db";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.createStatement().execute("create database if not exists " + database);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to the database: " + url, e);
        }

        url += database;

        try {
            connection = DriverManager.getConnection(url, username, password);
            if (!addTestData)
                return;
            connection.createStatement().execute(CREATE_TABLE);
            connection.createStatement().execute(INSERT_DATA);
            System.out.println("Successfully set up table and inserted test data");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    connection.close();
                    System.out.println("closed database connection successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }));
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to the database: " + url, e);
        }
    }

    public static void main(String[] args) {
        DataLayer dataLayer = new DataLayer();
        try {
            List<DataValue> dataValues = dataLayer.selectAllDataValues();
            System.out.println("got " + dataValues.size() + " dataValues");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DataValue> selectAllDataValues() throws SQLException {
        List<DataValue> list = new ArrayList<>();
        try (ResultSet s = connection.createStatement().executeQuery(SELECT_DATA)) {
            while (s.next()) {
                DataValue dataValue = new DataValue();
                dataValue.setId(s.getInt(1));
                dataValue.setValue(s.getString(2));
                list.add(dataValue);
            }
        }
        return list;
    }

    public static class DataValue {
        private int id;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
