package lernapp;

import lernapp.model.*;
import lernapp.service.QuestionService;
import lernapp.service.UserService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.List;

import static java.util.Arrays.asList;

public class App {

    public static void main(String[] args) {

        // HTTP Server starten
        ResourceConfig rc = new ResourceConfig().packages("lernapp");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8050"), rc, true);

        // Inject services
        UserService userService = new UserService();
        QuestionService questionService = new QuestionService();

        // Create users
        User marileen = new User("marileen.stamer@stud.fh-luebeck.de","Marileen","Stamer", "123");
        userService.save(marileen);


        // create Courses
        lernapp.model.Course dbCourse = new lernapp.model.Course(
                "DB"
        );
        lernapp.model.Course bwlCourse = new lernapp.model.Course(
                "BWL"
        );
        lernapp.model.Course itRechtCourse = new lernapp.model.Course(
                "IT Recht"
        );
        lernapp.model.Course cgCourse = new lernapp.model.Course(
                "Computergrafik"
        );

        // save the Courses to the database
        questionService.save( asList(dbCourse, bwlCourse, itRechtCourse, cgCourse) );

        // create Topics for dbCourse
        Topic dbQueries = new Topic(
                "DB Abfragen",
                "Abfragen erstellen zu bestimmten Szenarien (anhand von ER Diagrammen)",
                dbCourse
        );
        Topic erDiagrams = new Topic(
                "ER Diagramme",
                "ER Diagramme zu Klassen erstellen und andersherum",
                dbCourse
        );
        Topic knowledge = new Topic(
                "Wissenfragen",
                "Alle Fragen dieser Topic drehen sich um Datenbanken",
                dbCourse
        );

        // create Topics for bwlCourse
        Topic corporateStrategy = new Topic(
                "Unternehmensstrategie",
                "Grundlegenden Instrumente des strategischen Managements, die Bedeutung der Managementaufgabe und ihre wesentlichen Inhalte bzw. Phasen, Aspekten der Unternehmensumwelt und des Unternehmens selbst.",
                bwlCourse
        );

        // create Topics for itRechtCourse
        Topic mediaLaws = new Topic(
                "Mediengesetze",
                "Mediengesetze nach ihren unterschiedlichen medialen Erscheinungsformen.",
                itRechtCourse
        );

        // create Topics for cgCourse
        Topic rasterGraphics = new Topic(
                "Methoden der Rastergrafik ",
                "Darstellung von geometrischen Objekten mit Anwendungsprogrammen auf Rasterausgabegeräten (Rasterkonvertierung)",
                cgCourse
        );
        Topic transformations2D = new Topic(
                "2D-Transformationen ",
                "Mathematische Grundlagen der elementargeometrischen Transformationen in der Ebene: Translation, Rotation und Skalierung",
                cgCourse
        );

        //save the Topics to the Database
        questionService.save(asList(dbQueries , erDiagrams, knowledge, corporateStrategy, mediaLaws , rasterGraphics, transformations2D));

        // Create Questions
        Question ersteFrage = new Question(
                1,
                "Was brauchen wir alles für das Projekt?",
                "Mindestens schon mal JPA, Datenbank, Java-Code, zwei Frontends",
                knowledge
        );
        questionService.save(ersteFrage);

        Question zweiteFrage = new Question(
                2,
                "Was ist der Unterschied zwischen persist und merge?",
                "Merge = Referenz<br> Persist = Objekt selbst wird gespeichert - wie ein create?",
                knowledge
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
