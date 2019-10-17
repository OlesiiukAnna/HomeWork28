package home.dao;

import home.model.Image;

import java.util.List;

public interface Dao {

    boolean addImage(Image image);

    Image getImage(int id);

    List<Image> getAllImages();
}
