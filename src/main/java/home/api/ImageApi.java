package home.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import home.dao.impl.DaoImpl;
import home.model.Image;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Path("/")
public class ImageApi {
    private DaoImpl dao = new DaoImpl();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ImageApi() throws SQLException {
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(@FormParam("description") String description,
                                  @FormParam("base64") String base64) {
        Image newImage = new Image(description, base64);
        dao.addImage(newImage);
        LinkedList<Image> images = new LinkedList<>(dao.getAllImages());
        int result = images.getLast().getId();
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getImageById(@PathParam("id") int id) {
        Image image = dao.getImage(id);
        if (image != null) {
            byte[] array = Base64.getDecoder().decode(image.getBase64());
            return Response
                    .status(Response.Status.OK)
                    .entity(new ByteArrayInputStream(array))
                    .build();
        }
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Image with such id not found")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<Image> images = dao.getAllImages();
        String result = gson.toJson(images);
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
    }
}
