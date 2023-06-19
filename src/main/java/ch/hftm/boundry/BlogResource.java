package ch.hftm.boundry;

import java.util.List;

import org.jboss.logging.Logger;

import ch.hftm.control.BlogService;
import ch.hftm.entity.Blog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("blogs")       // Unter welchem Web-Pfad ist die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BlogResource {

    @Inject
    BlogService blogService;

    @Inject
    Logger logger;

    @GET             // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    public List<Blog> getEntries() {
        return this.blogService.getBlogs();
    }

    @POST            // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    public void addBlog(Blog blog) {
        this.blogService.addBlog(blog);
    }

    @GET
    @Path("{id}")
    public Blog getBlog(long id) {
        return this.blogService.getBlog(id);
    }
}
