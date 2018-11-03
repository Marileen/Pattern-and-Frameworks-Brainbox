package lernapp;

import lernapp.model.*;
import lernapp.service.QuestionService;
import lernapp.service.UserService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class App {

    public static void main(String[] args) {

        // HTTP Server starten
        ResourceConfig rc = new ResourceConfig().packages("lernapp");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8050"), rc, true);

        /* Inject services */
        UserService userService = new UserService();
        QuestionService questionService = new QuestionService();

        // Create users
        User marileen = new User("marileen.stamer@stud.fh-luebeck.de","Marileen","Stamer", "123");
        userService.save(marileen);


        // create Courses
        lernapp.model.Course ersterKurs = new lernapp.model.Course(
                "DB"
        );
        questionService.save(ersterKurs);

        // create Topics
        Topic erstesThema = new Topic(
                10,
                "Datenbanken",
                "Alle Fragen dieser Topic drehen sich um Datenbanken",
                ersterKurs
        );
        questionService.save(erstesThema);

        // Create Questions
        Question ersteFrage = new Question(
                1,
                "Was brauchen wir alles für das Projekt?",
                "Mindestens schon mal JPA, Datenbank, Java-Code, zwei Frontends",
                erstesThema
        );
        questionService.save(ersteFrage);

        Question zweiteFrage = new Question(
                2,
                "Was ist der Unterschied zwischen persist und merge?",
                "Merge = Referenz<br> Persist = Objekt selbst wird gespeichert - wie ein create?",
                erstesThema
        );
        questionService.save(zweiteFrage);

        // create LearningState
//        LearningState ersterLS = new LearningState(
//                8,
//                "DB",
//                "URL des Bilds",
//                "Beschreibung des LearningStates"
//        );
//        ersterLS = questionService.save(ersterLS);


        // create User_Question_LS
//        User_Question_LS ersterUQLS = new User_Question_LS(
//                8,
//                9,
//                3
//        );
//        ersterUQLS = questionService.save(ersterUQLS);


        // Close database connection
        // GenericService.EMF.close();
    }
}
