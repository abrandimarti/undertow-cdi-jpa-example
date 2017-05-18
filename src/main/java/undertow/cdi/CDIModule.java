package undertow.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CDIModule {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("jpa-example");

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public void closeEM(@Disposes EntityManager manager) {
        manager.close();
    }

    @Produces
    @Named("property.one")
    public String message() {
        return "property.one.value";
    }
}
