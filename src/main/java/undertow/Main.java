package undertow;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import undertow.jaxrs.MyApp;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final UndertowJaxrsServer server = new UndertowJaxrsServer();

    // print logback's internal status
    static {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }

    // stop server when shuting down JVM
    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOGGER.info("running ShutdownHook...");
                server.stop();
            }
        });
    }

    public static void main(String[] args) {
        Undertow.Builder serverBuilder = Undertow.builder() //
                .addHttpListener(8081, "0.0.0.0");
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(MyApp.class.getName());
        deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");

        DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/rest") //
                .setClassLoader(getMainClassLoader()) //
                .setResourceManager(new ClassPathResourceManager(getMainClassLoader())) //
                .setContextPath("/myApp") //
                .setDeploymentName("My Application") //
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        server.deploy(deploymentInfo);
        server.start(serverBuilder);
    }

    private static ClassLoader getMainClassLoader() {
        return Main.class.getClassLoader();
    }

}
