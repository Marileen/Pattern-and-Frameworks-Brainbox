package lernapp;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
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
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8050"), rc);

        // Serve App from Marileen Stamer
        final StaticHttpHandler things2learnHandler = new StaticHttpHandler("frontend-marileen/things2learn/dist");
        things2learnHandler.setFileCacheEnabled(false); // change to true in the deploy time
        server.getServerConfiguration().addHttpHandler(things2learnHandler, "/mstamer");

        // Serve App from Kathrin Koehler
        final StaticHttpHandler MedieninformatikLernappHandler = new StaticHttpHandler("frontend-kathrin");
        MedieninformatikLernappHandler.setFileCacheEnabled(false); // change to true in the deploy time
        server.getServerConfiguration().addHttpHandler(MedieninformatikLernappHandler, "/kkoehler");

        try {
            server.start();
        } catch (Exception e) {
            //
        }

    }
}
