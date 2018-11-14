# Pattern-and-Frameworks

Den ganzen Projektordner müsste man eigentlich nach dem Clonen/Auschecken in IntelliJ öffenen können

## gemeinsame Backend-Anwendung

ich habe nach diversen Tets nun schonmal im src/main/java/ Verzeichnis ein lernapp package angelegt mit der ersten Klasse: User
Vorlage dafür ist die Struktur aus dem Beispiel von Jens Ehlers, welches ich runtergeladen habe

### Gradle
läuft bei mir, ist wir Maven bloß anders

### Hibernate

benutzt unter resources/ eine persistence.xml, die voller Magie ist und man muss hier unbedingt die Entity Klassen eintragen, sonst funktioniert es nicht.
Ich habe versucht, dass Autodetection geht, aber es funktioniert nicht, also einfach die Klassen eintragen, was solls


## MariaDB

Muss man lokal installiert haben

brew install mariadb

mysql.server start

To connect:
    mysql -uroot

https://mariadb.com/kb/en/library/mariadb-basics/

Man kann dann auch SequelPro nehmen 

## frontend-marileen

Hier sind meine ersten chaotischen Versuche drin was mit Vue zu versuchen - hat nix mit dem Backend zu tun und kann ignoriert werden, ich wollte es nur für mich mit eingecheckt haben.


## Maven Central

Wenn man Gradle (Maven) Module sucht, dann findet man sie hier:

https://mvnrepository.com/

## Routen

http://127.0.0.1:8050/topics
http://127.0.0.1:8050/topics/DB/Wissenfragen
http://127.0.0.1:8050/topics/DB
http://127.0.0.1:8050/questions
http://127.0.0.1:8050/questions/course/BWL

### Bessere Überlegung zu Routen:

/courses
/{coursename}
/{coursename}/{topicname}
/{userId}/{questionsId}

* Fragen anlegen (q, a, t_id, c_id)
* Fragen ändern
* Fragen löschen

* topic anlegen
* topic ändern
    
* course anlegen
* course ändern
