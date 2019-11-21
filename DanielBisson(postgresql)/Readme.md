# Table des Matières

1. Introduction
2. Dépendances
3. Situation logicielle initiale
4. Application complète proposée
5. Démarrage d'une instance PostgreSQL DB
6. Connexion initiale à la base de données
7. Ajout de dépendances au fichier POM.XML
8. Création d'une source de données
9. Utilisation de Flyway
10. Interaction en invite de commande avec le contenant dans Docker
11. Démarrage de l'application java
12. Génération d'entrées dans la table «person»
13. Implémentation de notre couche d'accès aux données
14. Démonstration finale
15. Conclusion
16. Quelques fonctionnalités de SpringBoot
17. Dépannage (Troubleshooting)

# Introduction

Cette portion avancée du tutoriel vous permettra de compléter l'implémentation du modèle proposé avec une base de données PostgreSQL en utilisant le logiciel Docker et les dépendances de flyway et de java JDBC en utilisant SpringBoot.

Pour toute information concernant les fonctionalités SpringBoot, référez-vous à la section «Quelques fonctionnalités de SpringBoot», pour en avoir la définition.

Cette portion du tutoriel devrait vous prendre environ 45 minutes à apprendre et à implémenter.

Encore une fois, je vous souhaite un bon apprentissage!

# Dépendances

#### Dépendances requises

- Docker ( https://www.docker.com/ )
- Postgresql DB ( https://www.postgresql.org/  )
- Flyway
- SpringBoot JDBC

# Situation Logicielle Initiale

Pour l'implémentation de ce tutoriel avancé, je prend pour acquis que votre application est maintenant pleinement implémenté et fonctionnelle selon le tutoriel SpringBoot de base. Votre application est donc une application web, RestFul qui respecte le modèle MVC. Elle respecte donc le schémas suivant:

<img src="images\image-20191120132524670.png" alt="image-20191117114342901" style="zoom:150%;" />

Vous avez une couche API qui est accessible par un client web de votre choix. Vous avez aussi une couche service et une couche d'accès aux données. Votre modèle est une personne qui comporte un id et un nom comme attributs. Votre base de données est présentement en mémoire vive et n'est que conservée durant l'exécution de l'application.

# Application complète proposée

Le schémas suivant illustre la solution finale proposée pour l'application.

<img src="images\image-20191120132430562.png" alt="image-20191117114342901" style="zoom:150%;" />

Des clients tels que POSTMAN, une application Android ou une application web pourront communiquer avec votre application depuis sa couche API. Pour la persistance des données, nous aurions le choix d'implémenter la base de données de notre choix (MySQL, PostgreSQL, mongoDB, etc.). Dans ce tutoriel, nous utiliserons Docker afin d'utiliser la base de données PostgreSQL (représentée par la figure de l'éléphant dans l'image ci-dessus).

# Démarrage d'une instance PostgreSQL DB

Assurez-vous tout d'abord d'avoir installé le logiciel Docker sur votre ordinateur personnel. De plus, assurez-vous que Docker fait partie de vos variables d'environnement, afin de permettre son utilisation en invite de commande via la commande «docker».

Le lien suivant vous permet d'obtenir de plus amples informations sur l'utilisation de Postgres DB avec le logiciel Docker:

 https://hub.docker.com/_/postgres 

Ouvrez une fenêtre de commande sur votre ordinateur. Entrez la commande suivante:

*docker run --name tp2postgresdb -e POSTGRES_PASSWORD=monmotdepasse -d -p 5432:5432 postgres:alpine*

Ceci donnera la commande à Docker de créer votre instance de base de donnée PostgreSQL avec les attributs suivant:

Nom: tp2postgresdb

Mot de passe: monmotdepasse

Port d'utilisation: 5432 (par défaut pour Postgres)

-d : veut dire «detach mode»

Mode d'utilisation: alpine (version légère de la base de donnée)

Pour montrer le contenant de la base de donnée, tapez la commande «docker ps». Pour montrer le port qu'utilise votre base de données, tapez la commande «docker port tp2postgresdb».

Si le tout fonctionne bien, vous obtiendrez le résultat suivant:

<img src="images\image-20191120135412938.png" alt="image-20191117114342901" style="zoom:150%;" />

Nous exposons donc le port 5432 sur «localhost» pour l'utilisation de notre base de données.

# Connexion initiale à la base de données

Nous utiliserons la 2e implémentation de l'interface «PersonDataAccessInterface.java» pour la connexion et l'implémentation de l'interaction avec notre base de données. Simplement pour vous le rappeler, il s'agit de la classe «PersonDataAccessNouvelleImplementation.java» dans le package «dataAccess».

Nous utiliserons un fichier «application.yml» que nous insèrerons dans le dossier «%votreApplication%/src/main/ressources» afin d'insérer les détails de connexion à la base de données.

Commencez par supprimer le fichier «application.properties». Créez maintenant le fichier «application.yml» tel que les images ci-dessous le démontrent. Assurez-vous de respecter l'indentation dans le fichier «application.yml», lorsque vous le coderez.

<img src="images\image-20191120140320513.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191120183334371.png" alt="image-20191117114342901" style="zoom:150%;" />

Sur la ligne «jdbc-url», nous définissons que le nom de notre base de données dans notre contenant «tp2postgresdb» sera «tp2database». Ceci sera important par la suite.

# Ajout de dépendances au fichier POM.XML

Nous aurons maintenant besoin d'ajouter certaines dépendances au fichier «POM.XML», situé dans le dossier racine de votre projet.

Insérez les dépendances suivantes dans le tag xml "dependancies":

1. spring-boot-starter-jdbc
2. postgresql
3. flyway-core

Référez-vous à l'image suivante au besoin.

<img src="images\image-20191120141125451.png" alt="image-20191117114342901" style="zoom:150%;" />

# Création d'une source de données

Ajoutez maintenant un package dans «%votreApplication%/src/main/java» et nommez le «datasource». Ajoutez la classe « PostgresDataSource.java » au package .

### PostgresDataSource.java

La classe «PostgresDataSource.java» sera utilisée afin d'effectuer des configurations.  Elle prendra donc l'annotation @Configuration, qui détermine également qu'une de ses méthodes sera instanciée comme un «Bean». On l'instanciera d'ailleurs comme un «Bean» avec l'annotation @Bean. Notez également l'annotation «@ConfigurationProperties("app.datasource")» qui fait référence à notre fichier de connexion «application.yml», où «app.datasource» est définit. La classe sera donc sera implémentée comme suit.

<img src="images\image-20191120142135650.png" alt="image-20191117114342901" style="zoom:150%;" />

# Utilisation de Flyway

Nous devrons maintenant créer un dossier dans «%votreApplication"/src/main/ressources» nommé «db». À l'intérieur de «db», nous créerons un autre dossier nommé «migration». À l'intérieur du dossier «migration», créez un fichier nommé «V1PersonTable.sql». Il est très important ici de prendre exactement ce nom de fichier. Notez les 2 «underscore» de suite dans le nom du fichier. 

Vous aurez donc une structure telle que l'image ci-dessous et votre fichier «V1__PersonTable.sql» sera implémenté tel que le démontre l'image suivante.

<img src="images\image-20191120143957153.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191120144124465.png" alt="image-20191117114342901" style="zoom:150%;" />

Ceci créera donc notre table «person» dans notre base de données.

# Interaction en invite de commande avec le contenant dans Docker

### Exécuter Docker

Retournez maintenant en invite de commande et tapez la commande suivante:

*docker exec -it 76284e4f9e92 bin/bash*

«76284e4f9e92»  est l'id qui réfère à notre contenant. Il était visible après avoir fait la commande «docker ps» vu précédemment.

Une fois cette commande entrée, nous serons à l'intérieur de notre contenant.

Tapez maintenant la commande «psql». Une erreur sera affichée vous disant que «root» n'existe pas. Cette étape est simplement pour comprendre ce qui se passe lors de la création de notre base de données. Nous la répéterons plus tard également.

### Se placer dans le contenant

Nous entrerons maintenant dans notre contenant avec notre nom d'utilisateur (voir «application.yml»). Tapez la commande suivante:

*psql -U postgres*

Pour afficher la liste des bases de données dans votre contenant, tapez «\l». Pour l'instant, la table comportera 3 entrées, qui sont les entrées par défauts.

<img src="images\image-20191120150023821.png" alt="image-20191117114342901" style="zoom:150%;" />

### Création d'une base de données

Nous allons maintenant créer une nouvelle base de données. Puisque vous êtes dans votre contenant, vous pouvez maintenant exécuter des commandes SQL. Tapes la commande SQL suivante:

*CREATE DATABASE tp2database;*

«tp2database» est le nom de base de donnée provenant du fichier «application.yml» créé précédemment. Tapez «*\l*» à nouveau dans l'invite de commande. Vous verrez que vous avez maintenant créer votre base de données.

Si vous tapez à nouveau la commande «\l», vous pourrez vérifier la création de votre base de données.

<img src="images\image-20191120150102508.png" alt="image-20191117114342901" style="zoom:150%;" />

### Connexion à la base de données

Tapez la commande «*\c tp2database*». Vous vous connecterez alors à la base de données.

<img src="images\image-20191120182738042.png" alt="image-20191117114342901" style="zoom:150%;" />

Avec les commandes «*\d*» et «*\dt*», vous serez en mesure de confirmer que vous base de données est vide de tout éléments (\d) et qu'elle ne possède présentement aucune table (\dt).

<img src="images\image-20191120183019832.png" alt="image-20191117114342901" style="zoom:150%;" />

# Démarrage de l'application java

Nous allons maintenant démarrer notre application java. Vous pouvez laisser l'invite de commande ouvert pour l'instant, nous y reviendrons plus tard.

Assurez-vous donc qu'aucun autre processus n'est en cours d'exécution pour l'application présente. Démarrez votre application. Vous devriez obtenir le résultat suivant dans l'affichage de la console.

<img src="images\image-20191120183827715.png" alt="image-20191117114342901" style="zoom:150%;" />

On verra des logs pour nous mentionner entre autre les choses suivantes:

1. HikariPool-1 - Start completed;
2. Database: jdbc:postgresql://localhost:5432/tp2database (PostgreSQL 12.0);
3. Successfully applied 1 migration to schema "public" (execution time 00:00.035s);
4. Tomcat started on port(s): 8080 (http) with context path '';
5. Started DanielBissonApplication in 4.146 seconds (JVM running for 4.719);

Le tout confirme que le hikaripool est fonctionnel, que la base de données jdbc est à localhost au port 5432, que la migration a fonctionné (version 1 - PersonTable), que le serveur tomcat est en écoute au port 8080 et que l'application a bien démarrée.

Précédemment, nous avons créé le fichier «V1__PersonTable.sql». Ce fichier commandait la création d'une table person. Allons maintenant voir si le tout a fonctionné. Retournons en invite de commande.

Tapez la commande «\d». Vous observerez alors que la base de donnée comporte 2 tables. Une pour «flyway», que nous n'allons pas utiliser et une autre qui porte le nom de «person».

<img src="images\image-20191120184834063.png" alt="image-20191117114342901" style="zoom:150%;" />

Tapez la commande «\d person» afin de décrire la table «person». Vous obtiendrez alors les attributs de la table en question, tels que nous l'avons décrit dans notre application java.

<img src="images\image-20191120185013864.png" alt="image-20191117114342901" style="zoom:150%;" />

Avec la commande SQL «*SELECT * FROM person;*», nous sommes en mesure de vérifier que la tabler est présentement vide.

Nous allons maintenant générer des entrées dans notre table.

# Génération d'entrées dans la table «person»

Nous devrons d'abord installer une extension en invite de commande qui nous permettra de générer des UUID pour la création de nos entrées dans la table «person».

Tapez la commande suivante:

*CREATE EXTENSION "uuid-ossp";*

«CREATE EXTENSION» devrait apparaître si le tout a bien fonctionné. Générez maintenant un UUID avec la commande:

*SELECT uuid_generate_v4();*

Un UUID devrait être autogénéré et apparaître à l'écran.

<img src="images\image-20191120185849239.png" alt="image-20191117114342901" style="zoom:150%;" />

Nous allons maintenant générer des personnes dans notre table avec la commande SQL:

*INSERT INTO person (id, name) VALUES (uuid_generate_v4(), 'Nom Voulu');*

Insérez donc quelques personnes de votre choix. Vous devriez être capable d'obtenir le résultat suivant en faisant afficher la table «person».

<img src="images\image-20191120190341875.png" alt="image-20191117114342901" style="zoom:150%;" />

# Implémentation de notre couche d'accès aux données

Nous avons maintenant une base de données et quelques personnes présentes dans notre table «person». Nous avons maintenant besoin d'implémenter le code java nécessaire pour accéder à notre base de données. Fermez tout d'abord le processus Spring en cours d'exécution.

Ouvrez la classe «PersonDataAccessNouvelleImplementation.java».

Puisque le but de ce tutoriel est d'apprendre les fonctionnalités de SpringBoot, implémentez directement la classe comme suit:

### PersonDataAccessNouvelleImplementation.java

<img src="images\image-20191120191500581.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191120191606375.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191120191714300.png" alt="image-20191117114342901" style="zoom:150%;" />

Les points importants seront les suivants:

1. Assurez-vous de faire le bon «import» pour la classe JDBCTemplate;
2. Noubliez pas que le service fait le lien avec l'implémentation de l'interface via @Qualifier("nouvelleImplementation") et que le tout est relié à l'annotation @Repository("nouvelleImplementation") de votre classe PersonDataAccessNouvelleImplementation.java.
3. Le constructeur sera @Autowired (injection de dépendances)
4. Nous implémenterons seulement les méthodes selectAllPeople() et selectPersonById() pour le but de la démonstration;

# Démonstration finale

Sauvegardez vos changements et redémarrez maintenant votre application java.

Nous avons donc un serveur tomcat en écoute de clients sur le port 8080. Notre application comporte trois couches (API, SERVICE, DATA ACCESS) et est connectée à une base de données PostgreSQL sur le port 5432. Nous avons une base de données nommée «tp2database» qui comporte la table «person» qui comporte maintenant quelques entrées.

Avec le navigateur web de votre choix, faites maintenant la requête suivante afin de voir toutes les entrées de la table «person».

 *http://localhost:8080/api/v1/person* 

Vous devriez obtenir un résultat semblable à celui-ci, ce qui démontre que votre application est maintenant complètement fonctionnelle avec un client web.

<img src="images\image-20191120192736459.png" alt="image-20191117114342901" style="zoom:150%;" />

Postman donnera un résultat semblable.

<img src="images\image-20191120193126348.png" alt="image-20191117114342901" style="zoom:150%;" />

Vous pourriez également aller chercher une personne avec son identifiant avec la requête suivante:

 *http://localhost:8080/api/v1/person/%placerIDVoulu%*

En voici un exemple:

<img src="images\image-20191120193459887.png" alt="image-20191117114342901" style="zoom:150%;" />

# Conclusion

Pour conclure, SpringBoot offre une vaste gamme d'options de création d'applications et d'utilitaires pour faciliter l'utilisation et la configuration de Spring.

Il utilise des annotations afin de créer automatiquement des liens qui facilitent l'implémentation lors de la création d'une application.

Il permet entre autre, somme présenté dans ce tutoriel, de créer une application web Restful selon le modèle MVC avec une base de données fonctionnelle en moins de 2 heures.

Ceci termine la présentation des fonctionnalités pour le but du tutoriel SpringBoot. En espérant que le tout vous aura plut.

# Quelques fonctionnalités de SpringBoot

***@Configuration:*** Indicates that a class declares one or more `@Bean` methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.

***@ConfigurationProperties:*** Annotation for externalized configuration. Add this to a class definition or a  `@Bean` method in a `@Configuration` class if you want to bind and validate some external Properties (e.g. from a .properties file).

***@Bean:*** Indicates that a method produces a bean to be managed by the Spring container. 

***@Autowired:*** Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities.

# Dépannage (Troubleshooting)

### **Commandes SQL**

N'oubliez pas le «;» à la fin de chaque commandes. De plus, lors de la création d'une entrée avec la commande 

*INSERT INTO person (id, name) VALUES (uuid_generate_v4(), 'Nom Voulu');*

utilisez toujours le guillemet simple(') et non pas le guillemet double (").

### **JDBCTemplate**

2 importations sont possibles avec la classe JDBCTemplate. Utilisez 

org.springframework.jdbc.core.JdbcTemplate;

### **X rouges sur les dossiers de projet**

Sauvegardez d'abord vos modifications.

Maven a parfois besoin de faire la mise à jour des informations du projet.

Essayez la démarche suivante pour Eclipse:

- Clique droit sur votre projet;
- Maven;
- Update Project...;
- Cliquez sur «ok».

Ceci mettra à jour les liens Maven pour votre projet et résoudra parfois certaines erreurs de synchronisation.