package lernapp;

import lernapp.model.*;
import lernapp.service.BasicService;
import lernapp.service.LearningStateService;
import lernapp.service.QuestionService;
import lernapp.service.UserService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

import static java.util.Arrays.asList;

public class App {

    public static void main(String[] args) {

        // HTTP Server starten
        ResourceConfig rc = new ResourceConfig().packages("lernapp");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8050"), rc, true);

        // Inject services
        UserService userService = new UserService();
        QuestionService questionService = new QuestionService();
        LearningStateService learningStateService = new LearningStateService();

        // Create users
        User marileen = new User("marileen.stamer@stud.fh-luebeck.de","Marileen","Stamer", "123", true);
        userService.save(marileen);


        // create Courses
        lernapp.model.Course pafCourse = new lernapp.model.Course(
                "Patterns and Frameworks"
        );

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
        questionService.save( asList(pafCourse, dbCourse, bwlCourse, itRechtCourse, cgCourse) );

        // create Topics for pafCourse
        Topic jpa = new Topic(
                "JPA",
                "Beschreibung der JPA und der wichtigsten Annotations",
                pafCourse
        );

        Topic jwt = new Topic(
                "JWT",
                "Komponenten und Einsatz von JWT",
                pafCourse
        );

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
                "Wissensfragen",
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
        questionService.save(asList(jpa, jwt, dbQueries , erDiagrams, knowledge, corporateStrategy, mediaLaws , rasterGraphics, transformations2D));

        // Create Questions
        Question  Frage1 = new Question(
                "Wie lautet die Definition der 1. Normalform?",
                "<p>Die Erste Normalform (1NF) ist dann gegeben, wenn alle Informationen in einer Tabelle <strong>atomar</strong> vorliegen.</p><p>Das bedeutet, dass jede Information innerhalb einer Tabelle eine eigene Tabellenspalte bekommt und zusammenhängende Informationen, wie zum Beispiel die Postleitzahl (PLZ) und der Ort, nicht in einer Tabellenspalte vorliegen dürfen.</p>",
                knowledge
        );

        Question  Frage2 = new Question(
                "Was ist ein Fremdschlüssel?",
                "Der Fremdschlüssel kann Bestandteil einer Tabelle in einer relationalen Datenbank sein. Dabei handelt es sich um eine Schlüsselspalte, die auf einen Primärschlüssel einer anderen oder aber derselben Tabelle verweist. Es kann sich dabei um einen einfachen oder zusammengesetzten Schlüssel handeln. Das hängt davon ab, wie der Primärschlüssel der referenzierten Tabelle aufgebaut ist.",
                knowledge
        );

        Question  Frage3 = new Question(
                "Nennen und erklären Sie Ihnen bekannte Unternehmenskennzahlen.",
                "<p>Wirtschaftlichkeit (Produktivität, Wirtschaftlichkeit)</p><p>Rentabilität (Erfolg, Eigenkapitalrentabilität, Gesamtkapitalrentabilität, Umsatzrentabilität)</p><p>Liquidität (Liquidität ersten Grades, Liquidität zweiten Grades, Liquidität dritten Grades)</p>",
                corporateStrategy
        );

        Question  Frage4 = new Question(
                "Was bedeutet \"Wirtschaften\"?",
                "Inhalt des Wirtschaftens ist der rational gesteuerte Einsatz knapper Güter zur Befriedigung von Bedürfnissen. Das Wirtschaften wird notwendig, weil die Güter, die zur Befriedigung der menschlichen Bedürfnisse wie Nahrung und Kleider benötigt werden, in der Regel knapp sind.",
                corporateStrategy
        );


        Question  Frage5 = new Question(
                "Welche Urheberrechte gibt es?",
                "Urheberpersönlichkeitsrecht (Buch Seite 323 §12), Verwertungsrechte (Buch Seite 324 §15), Sonstige Rechte (Buch Seite 327 §25)",
                mediaLaws
        );

        Question  Frage6 = new Question(
                "Test lorem ipsum?",
                "antwort lorem ipsum",
                mediaLaws
        );

        Question  Frage7 = new Question(
                "Telemediengesetz  -  Was bedeutet  Providerhaftung? ",
                "Bis vor wenigen Jahren war die Haftung der Provider für rechtswidrige Inhalte auf Internetseiten, die von ihnen betrieben oder technisch betreut werden, juristisch umstritten. Die Frage der Verantwortlichkeit ist mittlerweile in Deutschland durch das Telemediengesetz – TMG (§§ 7 ff. TMG) geklärt, welches das Teledienstegesetz – TDG (§ 8 TDG) und den Mediendienstestaatsvertrag – MDStV (§ 6 MDStV) im Jahr 2007 abgelöst hat. Die näheren Voraussetzungen der Providerhaftung regeln die §§ 7-10 TMG. Die Regelung gilt für Zivilrecht, Strafrecht und Öffentliches Recht gleichermaßen.",
                mediaLaws
        );

        Question  Frage8 = new Question(
                "Was bedeutet Aliasing und wo taucht es auf?",
                "Aliasing bezeichnet das Auftreten von Überfaltungsfehlern in Abtastsystemen. Bei der Ausgabe von Geraden auf Rasterausgabegeräten entsteht als Folge von Aliasing ein störender treppenförmiger Verlauf.",
                rasterGraphics
        );

        Question  Frage9 = new Question(
                "Was versteht man unter der treibenden Komponente?",
                "Wird verwendet bei der Rasterkonvertierung. Darunter ist die Komponente zu verstehen, die bei jedem Schritt um eins inkrementiert wird. Die Berechnung der jeweils anderen Komponente erfolgt in Abhängigkeit von der treibenden Komponente.",
                rasterGraphics
        );

        Question  Frage10 = new Question(
                "Beschreibe kurz den Bresenham Geraden Algorithmus",
                "Ihm liegt als Entscheidungskriterium die minimale Entfernung der idealen Gerade zum Pixelmittelpunkt zu Grunde.",
                rasterGraphics
        );

        Question  Frage11 = new Question(
                "Geben Sie eine Matrix an, die eine 2D-Spiegelung an der Winkelhalbierenden im 2. Quadranten in homogenen Koordinaten beschreibt",
                "Hier würde man wohl eher eine Grafik einfügen",
                transformations2D
        );

        Question Frage12 = new Question(
                "Was ist die JPA?",
                "JPA ist die Java Persistence API, also eine Schnittstelle, die die Übertragung von Objekten in eine Datenbank vereinfacht.",
                jpa
        );

        Question Frage13 = new Question(
                "Was ist Hibernate?",
                "Hibernate ist ein ORMapper, der die semantische Lücke zwischen objektorientiertem Code und dem relationalen " +
                       "Model in der Datenbank schliesst.",
                jpa
        );

        Question Frage14 = new Question(
                "Was ist JWT, woraus besteht es und für was kann es eingesetzt werden?",
                "Das Java Web Token (JWT) kann für die Authentifizierung zwischen Client und Server genutzt werden und " +
                        "besteht aus Header, Payload und Signature.",
                jpa
        );

        //save Questions
        questionService.save( asList(Frage1, Frage2, Frage3, Frage4, Frage5, Frage6, Frage7, Frage8, Frage9, Frage10, Frage11, Frage12, Frage13, Frage14) );

        // create LearningState
        LearningState lsGut = new LearningState(
                "Gut",
                "URL des Bilds",
                "Ich kann die Frage gut beantworten. Der Inhalt ist also erfolgreich erlernt."
        );

        learningStateService.save(lsGut);

        LearningState lsMittel = new LearningState(
                "Mittel",
                "URL des Bilds",
                "Ich kann die Frage halbwegs oder teilweise beantworten. Der Inhalt muss noch gefestigt werden."
        );
        learningStateService.save(lsMittel);

        LearningState lsNicht = new LearningState(
                "Noch nicht",
                "URL des Bilds",
                "Ich kann mit der Frage noch nicht viel anfangen. Damit muss ich mich auf jeden Fall noch mehr beschäftigen"
        );
        learningStateService.save(lsNicht);


        // create UserQuestionLS
        UserQuestionLS ersterUQLS = new UserQuestionLS(
                marileen,
                Frage11,
                lsNicht
        );
        learningStateService.save(ersterUQLS);


        // Close database connection
        // GenericService.EMF.close();
    }
}
