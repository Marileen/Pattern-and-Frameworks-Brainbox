# Patterns and Frameworks

Semesterprojektaufgabe WS 2018/19 von Kathrin Köhler und Marileen Stamer

## gemeinsame Backend-Anwendung

ich habe nach diversen Tets nun schonmal im src/main/java/ Verzeichnis ein lernapp package angelegt mit der ersten Klasse: User
Vorlage dafür ist die Struktur aus dem Beispiel von Jens Ehlers, welches ich runtergeladen habe

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

Hier sind meine ersten chaotischen Versuche drin was mit Vue zu versuchen - hat nix mit dem Backend zu tun und kann ignoriert werden, ich wollte es nur für mich mit eingecheckt haben.


## Maven Central

Wenn man Gradle (Maven) Module sucht, dann findet man sie hier:

https://mvnrepository.com/

## Routen, die schon funktionieren

**Jeweils alle Entitäten:**  
http://127.0.0.1:8050/courses
http://127.0.0.1:8050/topics
http://127.0.0.1:8050/questions

**Topics zu einen bestimmten Kurs**
http://127.0.0.1:8050/topics/DB

**Fragen zu einem Kurs:**
http://127.0.0.1:8050/questions/BWL

**Fragen zu einem Kurs und einem Topic:**   
todo:  /{coursename}/{topicname}

**Zu einem Benutzer die ID einer Frage mit dem Status für den User**    
todo: /{userId}/{questionsId}

### Überlegung zu weiteren Routen:

* Fragen anlegen (q, a, t_id, c_id)
* Fragen ändern
* Fragen löschen

* topic anlegen
* topic ändern
    
* course anlegen
* course ändern
