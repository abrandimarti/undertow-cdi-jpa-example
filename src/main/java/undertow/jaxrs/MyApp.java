package undertow.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class MyApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(Resource.class);
        return classes;
    }
}