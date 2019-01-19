# Patterns and Frameworks

Semesterprojektaufgabe WS 2018/19 von Kathrin Köhler und Marileen Stamer

## gemeinsame Backend-Anwendung

stellt Enddpoints für User Registrierung, Daten zu Kursen, Topics, Fragen und Antworten sowie die Infos zu einem User 
und seiner Markierung für verschiedene Fragen. Liefert Bilder.


## Start

##### 1) Maria DB sollte installiert sein, [siehe auch hier](#mariaDB)
##### 2) Datenbank importieren (mitgeliefert)
##### 3) Projekt via gradle Datei in IDE öffnen

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

    Grizzly hostet die Dateien von Marileens und Kathrins Frontend
    Server hostet einfach in bestimmten ordner


 To get started:

    cd things2learn
    npm run dev

  To build & start for production:

    cd things2learn
    npm run build
    npm start


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
