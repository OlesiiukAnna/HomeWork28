package home.model;

public class Image {
    private int id;
    private String description;
    private String base64;

    public Image(String description, String base64) {
        this.description = description;
        this.base64 = base64;
    }

    public Image(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Image(int id, String description, String base64) {
        this.id = id;
        this.description = description;
        this.base64 = base64;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getBase64() {
        return base64;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
