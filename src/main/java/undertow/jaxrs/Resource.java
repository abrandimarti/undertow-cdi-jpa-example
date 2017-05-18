package undertow.jaxrs;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello")
public class Resource {

    @Inject
    private EntityManager em;

    @Inject
    @Named("property.one")
    private String message;

    @GET
    @Produces("text/plain; charset=UTF-8")
    public String get() {
        System.out.println("Resource.get() " + this.toString());
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        transaction.rollback();
        return "hello world: " + message;
    }
}