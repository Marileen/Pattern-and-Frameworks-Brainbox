# Patterns and Frameworks

Semesterprojektaufgabe WS 2018/19 von Kathrin Köhler und Marileen Stamer

## gemeinsame Backend-Anwendung

stellt Enddpoints für User Registrierung, Daten zu Kursen, Topics, Fragen und Antworten sowie die Infos zu einem User 
und seiner Markierung für verschiedene Fragen. Liefert Bilder.


## Start

### Backend
#### Voraussetzungen
* MariaDB sollte installiert sein und laufen auf Port 3306
* es muss eine Datenbank namens lernapp erstellt werden, in die der mitgelieferte Datenbank dump paf_lernapp_Koehler_stamer.sql importiert werden kann
* es muss der DB-Benutzer root vorhanden sein, Passwort: null8fuenfzehn
entwickelt wurde mit der Java-Version 10

#### Einrichten der Anwendung
Import des mitgelieferten Datenbank dumps paf_lernapp_Koehler_stamer.sql in die o. g. Datenbank
Projekt muss per Gradle-Datei in der IDE geöffnet / importiert werden
ggf. muss Hibernate als JPA Provider in der IDE eingerichtet werden

Die Anwendung, die den Webserver startet, liegt im Package ‘lernapp’ und wird von der Methode main in der App.java Klasse gestartet.


Der Webserver läuft unter http://localhost:8050/
Alle benötigten Medien werden im Package resources/media mitgeliefert.
Frontends
Marileen:	erstellt mit Vue.js
Kathrin: 	erstellt mit jQuery

Browser: beide Anwendungen laufen auf Google Chrome 

Beide Frontend-Anwendungen werden ebenfalls vom Grizzly Webserver bereitgestellt.


 
Die Anwendung von Marileen Stamer wird unter
http://localhost:8050/mstamer/
aufgerufen.
Die Quelldateien liegen im Projekt unter frontend-marileen/things2learn
 
Die Anwendung von Kathrin Koehler wird unter
http://localhost:8050/kkoehler/
aufgerufen.
Die Quelldateien liegen im Projekt unter frontend-kathrin


Ein bereits angelegter Benutzer für die Lernapp kann sich mit den folgenden Zugangsdaten einloggen:
User name:	greg@gmx.de
Password:	123
 

#### Weitere Hinweise:
 
Für die Entwicklung des Frontend-Projekt „Things2Learn“ von Marileen Stamer, gelten die Voraussetzungen
 
* Node und npm müssen installiert sein
 
Das Projekt wird wie folgt im Entwicklungsmodus gestartet:
 
1) Wechsel in das Verzeichnis frontend-marileen/things2learn
2) Ausführen des Befehls npm install
3) Ausführen des Befehls npm run dev

Das dist-Verzeichnis (welches als docroot vom Grizzly Webserver verwendet wird) kann mit den o.g. Voraussetzungen erzeugt werden, durch das Ausführen des Befehls npm run build


## Komponenten

### Gradle

Als Build-System haben wir **gradle** verwendet

### Hibernate

Als ORM-Framework haben wir **Hibernate** eingesetzt.   
Hierfür wird unter resources/ eine persistence.xml verwendet.   
Bei Marileen auf dem Mac müssen hier unbedingt die Entity Klassen eintragen sein, sonst funktioniert es nicht.
Mit Autodetection geht es nicht.

### MariaDB
<a name="mariaDB" id="mariaDB"></a>

Sollte lokal installiert sein

Mini-Anleitung dazu:   
1) brew install mariadb
2) mysql.server start

To connect:
    mysql -uroot

Quelle: 
https://mariadb.com/kb/en/library/mariadb-basics/


## frontend-marileen

    http://localhost:8050/mstamer/

 Für die Entwicklung :

    cd things2learn
    npm run dev

  To build & start for production:

    cd things2learn
    npm run build


## frontend-kathrin

    http://localhost:8050/kkoehler/


## Maven Central

Wenn man Gradle (Maven) Module sucht, dann findet man sie hier:

https://mvnrepository.com/

## Endpoints

### GET

**Jeweils alle Entitäten:**  
http://127.0.0.1:8050/courses   
http://127.0.0.1:8050/topics    
http://127.0.0.1:8050/questions

**Topics zu einen bestimmten Kurs**     
http://127.0.0.1:8050/topics/DB

**Fragen zu einem Kurs:**   
http://127.0.0.1:8050/questions/BWL

**Fragen zu einem Kurs und einem Topic:**       
http://127.0.0.1:8050/questions/DB/Wissensfragen

**Learningstate zu einem User und einer Question abrufen**
http://127.0.0.1:8050/state/1/11   
state/{userId}/{questionId}

**get marked Questions for User and Learning State**
http://127.0.0.1:8050/state/0/3    
state/{userId}/{stateId}

### POST

**User registrieren**
http://127.0.0.1:8050/user/register

Beispiel:   
{
  "email": "test@test.de",
  "firstname": "Uschi",
  "lastname": "",
  "password": "467"
}

**Setzen eines Status für User, Question und Status**
http://127.0.0.1:8050/state/set

**Fragen anlegen**  
http://127.0.0.1:8050/questions

<pre>
{ "question" : "Testfrage 1?",
  "answer": Testantwort1,
  "topic" : {
     "topicID": 3,
     "topicName": "Wissenfragen",
     "topicDescription": "Alle Fragen dieser Topic drehen sich um Datenbanken"
  }
}
</pre>


### Überlegung zu weiteren Routen:

* Fragen ändern
* Fragen löschen

* topic anlegen
* topic ändern
    
* course anlegen
* course ändern
