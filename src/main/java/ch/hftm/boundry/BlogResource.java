package ch.hftm.boundry;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import ch.hftm.control.BlogService;
import ch.hftm.entity.Blog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("blogs")       // Unter welchem Web-Pfad ist die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BlogResource {
    static final String BLOG_OVERVIEW = "01 Blog Overview";
    static final String BLOG_DETAIL = "02 Blog Detail";

    @Inject
    BlogService blogService;

    @Inject
    Logger logger;

    @GET  
    @Tag(name=BLOG_OVERVIEW)
    public List<Blog> getEntries(@QueryParam("search") String searchString) {
        if(searchString != null && !searchString.isBlank())
            return this.blogService.getBlogs(searchString);
        return this.blogService.getBlogs();
    }

    @POST
    @Tag(name=BLOG_DETAIL)
    @Operation(description = "Add new Blog-Post.")
    @APIResponses({ @APIResponse(responseCode = "500", description = "Could not create Blog"), 
        @APIResponse(responseCode = "201", description = "Blog created")
    })
    public Response addBlog(Blog blog) {
        try {
            this.blogService.addBlog(blog);
            return Response.status(201).entity(blog).build(); 
        } catch (Exception e) {
            return Response.status(500).entity(blog).header("Error", e).build(); 
        }
    }

    @GET
    @Path("{id}")
    @Tag(name=BLOG_DETAIL)
    @APIResponses({ @APIResponse(responseCode = "404", description = "Blog not found"), 
        @APIResponse(responseCode = "200", description = "Blog found")
    })
    public Blog getBlog(long id) {
        return this.blogService.getBlog(id).orElseThrow(() -> new NotFoundException("Blog with id " + id + " not found"));
    }

    @DELETE
    @Path("{id}")
    @Tag(name=BLOG_DETAIL)
    @APIResponses({ @APIResponse(responseCode = "404", description = "Blog not found"),
        @APIResponse(responseCode = "204", description = "Blog deleted") 
    })
    public void deleteBlog(long id) {
        Blog blog = this.blogService.getBlog(id).orElseThrow(() -> new NotFoundException("Blog with id " + id + " not found"));
        this.blogService.removeBlog(blog);
    }

    @PUT
    @Path("{id}/likedByMe")
    @Tag(name=BLOG_DETAIL)
    @APIResponses({ @APIResponse(responseCode = "404", description = "Blog not found"),
        @APIResponse(responseCode = "201", description = "Blog updated") 
    })
    public Response updateLikedByMe(long id, @Context UriInfo uriInfo) {
        Blog blog = this.blogService.getBlog(id).orElseThrow(() -> new NotFoundException("Blog with id " + id + " not found"));
        this.blogService.updateLikedByMe(blog);

        var uri = uriInfo.getAbsolutePathBuilder().path(blog.getId().toString()).build();
        return Response.created(uri).entity(blog).build();
    }
}
