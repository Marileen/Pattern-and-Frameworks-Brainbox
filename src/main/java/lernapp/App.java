package lernapp;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class App {

    /**
     * Hauptprogramm
     */

    public static void main(String[] args) {

        // HTTP Server starten
        ResourceConfig rc = new ResourceConfig().packages("lernapp");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8050"), rc, true);

    }
}
