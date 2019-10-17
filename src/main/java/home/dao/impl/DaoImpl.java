package home.dao.impl;

import home.Const;
import home.dao.Dao;
import home.model.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {

    private Connection connection;

    private final String SQL_INSERT_IMAGE = "INSERT INTO images(description, base64) VALUES(?, ?)";
    private final String SQL_SELECT_IMAGE_BY_ID = "SELECT id, description, base64 FROM images WHERE id = ?";
    private final String SQL_SELECT_ALL_IMAGES = "SELECT id, description FROM images";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DaoImpl() throws SQLException {
        connection = DriverManager.getConnection(Const.JDBC_URL, Const.USER, Const.PASSWORD);
        maybeCreateImagesTable();
    }

    private void maybeCreateImagesTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String request = "CREATE TABLE IF NOT EXISTS images \n" +
                    "(id SERIAL, \n" +
                    "description varchar(100),\n" +
                    "base64 TEXT,\n" +
                    "PRIMARY KEY (id));";
            statement.execute(request);
        }
    }

    public boolean addImage(Image image) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_IMAGE);
            statement.setString(1, image.getDescription());
            statement.setString(2, image.getBase64());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Image getImage(int id) {
        Image image = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_IMAGE_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int imageId = 0;
            while (resultSet.next() && id != imageId) {
                imageId = resultSet.getInt(1);
                String description = resultSet.getString(2);
                String base64 = resultSet.getString(3);
                image = new Image(imageId, description, base64);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return image;
    }

    public List<Image> getAllImages() {
        List<Image> images = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_IMAGES);
            while (resultSet.next()) {
                int imageId = resultSet.getInt(1);
                String description = resultSet.getString(2);
                images.add(new Image(imageId, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }
}
