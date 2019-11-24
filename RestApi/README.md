# Table des Matières

1. Introduction
2. Dépendances
3. Construction initiale du projet
4. Intégration à votre IDE
5. Création d'un squelette de projet au format Modèle-Vue-Contrôleur
6. Fonctionnalités de Springboot utilisés dans ce tutoriel
7. Implémentation de l'API Restful du modèle MVC
8. POSTMAN
9. Récupération du contenu de la base de données fictive
10. POSTMAN - Requête «GET»
11. Implémentation Restful des méthodes HTTP
12. Vérification avec POSTMAN
13. Implémentation de certaines validations
14. Injection de dépendances avec Springboot
15. Pour aller un peu plus loin (tutoriel avancé)
16. Dépannage(Troubleshooting)

# Introduction

Ce tutoriel vous présentera la marche à suivre pour l'utilisation de Springboot afin de construire une application Web, suivant le modèle MVC et les principes de requêtes REST. Cette application utilisera un serveur Tomcat. Pour la simulation d'un client web, le logiciel POSTMAN sera utilisé afin de faire des requêtes à votre application. 

Dans ce tutoriel, vous implémenterez le code «backend» pour les méthodes REST HTTP GET, POST, PUT et DELETE.

Pour terminer, un tutoriel avancé vous présentera une marche à suivre pour joindre une base de données PostgreSQL à votre application Restful en utilisant flyway, le logiciel Docker et SpringBootJDBC. Pour poursuivre avec le tutoriel avancé, référez-vous à «JDBC avec PostgreSQL» disponible dans le dossier «TP2-SpringBoot/JDBC/PostgreSQL/Readme.md».

Ce tutoriel de base pour l'application RestFul selon le modèle MVC vous prendra environ une heure et demi à compléter. Le tutoriel avancé demandera un 45 minutes additionnel. Je vous souhaite un bon apprentissage!

# Dépendances

#### Dépendances requises (tutoriel de base):

 - IDE pour le développement java de votre choix:
    - Eclipse;
    - IntelliJ;
    - NetBeans.
 - Java SDK 8 installé dans votre IDE;
 - Maven;
 - Le Logiciel POSTMAN ( https://www.getpostman.com/downloads).

#### Dépendances requises (tutoriel avancé):

- Docker ( https://www.docker.com/ )
- Flyway
- SpringBoot JDBC

# Construction initiale du projet

SpringBoot fonctionne avec une application web qui permet de créer un projet de base afin de démarrer avec une structure de départ. Visitez l'adresses suivante afin de créer un projet SpringBoot:

 https://start.spring.io/ 

Nous devrons ici insérer certains paramètres de départ. Suivez les instructions suivantes point par point afin de bien démarrer votre application SpringBoot.

1. Project: Maven Project
2. Language: Java
3. Spring Boot: 2.2.1
4. Project Metadata:
   1. Group: SpringbootMVCRestfulTutorial
   2. Artifact: %votreNom%
   3. Options:
      1. Name: Ne pas modifier
      2. Description: Web app MVC Restful PostgreSQL JDBC (ou une autre description de votre choix)
      3. Package name: Ne pas modifier
   4. Packaging: Jar
   5. Java: 8
5. Dependencies: Cochez en section Web «Spring Web»

Appuyez maintenant sur le bouton vert «Generate - Ctrl +». L'application web vous génèrera un projet de départ en .zip. Vous pouvez maintenant fermer l'application web.

# Intégration à votre IDE

Vous devrez maintenant extraire les données du .zip. Si vous avez donné votre nom à l'artifact, le dossier généré portera votre nom. Prenez maintenant le dossier généré et ouvrez le dans l'IDE de choix en tant que projet. Dans ce tutoriel, prenez par contre en compte que j'utiliserai l'IDE Eclipse.

Lors de l'ouverture du projet dans votre IDE, il est possible qu'il y ait 2 choix de projets. Si c'est le cas, ouvrez celui qui est «import as: Maven». Décochez donc l'option qui ne correspond pas à ce critère.

Si tout fonctionne bien, votre projet sera importé à votre IDE et aucune erreur ne sera affichée sur vos dossiers. Votre dossier de projet aura également les petites lettres «m» et «j» juste au-dessus de lui, et la hiérarchie du projet devrait être comme suit:

<img src="images\image-20191117114342901.png" alt="image-20191117114342901" style="zoom:150%;" />

L'application web de SpringBoot crée donc la structure initiale pour lancer un projet utilisant le framework de Spring. Dans le dossier «src\main\java\SpringbootMVCRestfulTutorial\%votreNom%» se trouvera le fichier «%votrenom%Application.java».

<img src="images\image-20191117114738720.png" alt="image-20191117114342901" style="zoom:150%;" />

SpringBoot l'a créé automatiquement. Cette classe est la classe de démarrage du projet Spring. À partir de maintenant, nous devrions être capable de lancer le projet à partir de cette classe. Faites-le test pour être certain que tout est fonctionnel avant de commencer à éditer le projet. Vous devriez obtenir le résultat suivant en console:

<img src="images\image-20191117115017369.png" alt="image-20191117114342901" style="zoom:150%;" />

En lisant les logs affichés à la console, vous confirmerez que le serveur Tomcat est lancé sur le port 8080(http) et que le projet Spring est fonctionnel.

# Création d'un squelette de projet au format Modèle - Vue - Contrôleur

Nous créerons maintenant les packages et classes nécessaires pour mettre en place un projet de format MVC. Le schéma suivant montre le projet qui sera créé pour le but de ce tutoriel:

<img src="images\image-20191117115643167.png" alt="image-20191117115643167" style="zoom:150%;" />

Nous créerons 3 couches de fonctionnalités. Une couche API, qui permettra au client de parler à notre application. Une couche service, qui implémentera les fonctionnalités de traitement des données et des fonctionnalités de l'application et une couche accès des données qui fera les liens nécessaires avec les données.

Pour notre application, un client pourrait prendre la forme d'un navigateur web, autant sur Microsoft Windows, IOS, Android ou Linux. Dans la portion avancée du tutoriel, nous intégrerons une base de données PostgreSQL au projet. Selon mes informations, la base de données de notre choix pourrait y être intégrée également.

## Packages

Nous créerons donc 4 packages au projet:

- api
- dataAccess
- service
- model

Créez donc ces packages dans votre projet afin que la structure représente le modèle d'application proposé ci-dessous:

<img src="images\image-20191117120427552.png" alt="image-20191117114342901" style="zoom:150%;" />

## Classes et Interface

Créez maintenant les classes et l'interface suivante(s) afin de représenter la structure de projet proposée dans l'image ci-dessous:

- Person.java (modèle)
- PersonService.Java (service)
- PersonDataAccessInterface.java (data access interface)
- PersonDataAccessImplementation.java(data access implementation)
- PersonController.java (api)

<img src="images\image-20191117121250458.png" alt="image-20191117114342901" style="zoom:150%;" />

Créez maintenant l'implémentation de base des classes selon les images ci-dessous. Je vous rappelle que nous ne faisons pour l'instant que définir la structure du projet, sans utiliser les fonctionnalités de SpringBoot. Nous y viendrons par la suite. Assurez-vous simplement de suivre l'implémentation proposée pour l'instant et d'importer les classes nécessaires au bon fonctionnement du code.

### Le modèle: Person.java

<img src="images\image-20191117130349933.png" alt="image-20191117114342901" style="zoom:150%;" />

### L'API: PersonController.java

<img src="images\image-20191117130447017.png" alt="image-20191117114342901" style="zoom:150%;" />

### Le service: PersonService.java

<img src="images\image-20191117131013042.png" alt="image-20191117114342901" style="zoom:150%;" />

### Data Access: l'interface PersonDataAccessInterface.java

<img src="images\image-20191117131223077.png" alt="image-20191117114342901" style="zoom:150%;" />

### Data Access: l'implémentation PersonDataAccessImplementation.java

<img src="images\image-20191117131502939.png" alt="image-20191117114342901" style="zoom:150%;" />

# Fonctionnalités de Springboot utilisés dans ce tutoriel

Pour la suite du tutoriel, plusieurs fonctionnalités de SpringBoot seront utilisés. Référez-vous aux explications suivantes pour comprendre leur utilité et leur fonctionnement.

**@Repository**:  Indicates that an annotated class is a "Repository", originally  defined by Domain-Driven Design (Evans, 2003) as "a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects".

**@*Service***: Indicates that an annotated class is a "Service", originally defined by Domain-Driven Design (Evans, 2003) as "an operation offered as an interface that stands alone in the model, with no encapsulated state."  

May also indicate that a class is a "Business Service Facade" (in the Core J2EE patterns sense), or something similar. This annotation is a general-purpose stereotype and individual teams may narrow their semantics and use as appropriate.  

This annotation serves as a specialization of `@Component`, allowing for implementation classes to be autodetected through classpath scanning.

**@*Autowired*: **Marks a constructor, field, setter method, or config method as to be autowired by Spring's dependency injection facilities

**@*Qualifier*:** This annotation may be used on a field or parameter as a qualifier for candidate beans when autowiring. It may also be used to annotate other custom annotations that can then in turn be used as qualifiers.

**@*RestController*:** A convenience annotation that is itself annotated with  `@Controller` and `@ResponseBody`.   Types that carry this annotation are treated as controllers where  `@RequestMapping` methods assume  `@ResponseBody` semantics by default.  @RestController` is processed if an appropriate  `HandlerMapping`-`HandlerAdapter` pair is configured such as the  `RequestMappingHandlerMapping`-`RequestMappingHandlerAdapter` pair which are the default in the MVC Java config and the MVC namespace.

**@*RequestMapping*:** Annotation for mapping web requests onto methods in request-handling classes with flexible method signatures. 

**@*RequestBody*:** Annotation indicating a method parameter should be bound to the body of the web request.  

**@*PostMapping***: Annotation for mapping HTTP `POST` requests onto specific handler methods. 

**@*GetMapping***: Annotation for mapping HTTP `GET` requests onto specific handler methods. 

**@*PutMapping*:** Annotation for mapping HTTP `PUT` requests onto specific handler methods. 

**@*DeleteMapping***: Annotation for mapping HTTP `DELETE` requests onto specific handler methods. 

@***PathVariable***: Annotation which indicates that a method parameter should be bound to a URI template variable. Supported for `RequestMapping` annotated handler methods. 

***@NotBlank:*** The annotated element must not be `null` and must contain at least one non-whitespace character. Accepts `CharSequence`.

***@Valid:***Marks a property, method parameter or method return type for validation cascading.   Constraints defined on the object and its properties are be validated when the property, method parameter or method return type is validated.   This behavior is applied recursively.

***@NonNull:*** A common Spring annotation to declare that annotated elements cannot be `null`. Should be used at parameter, return value, and field level. Method overrides should repeat parent `@NonNull` annotations unless they behave differently. 

# Implémentation de l'API Restful du modèle MVC

SpringBoot configure plusieurs fonctionnalités automatiquement. 

### Accès de données

Nous voudrons que notre couche d'accès de données soit instanciée comme un Bean. Nous voudrons également qu'elle soit utilisée comme un «repository». Nous y ajouterons donc l'annotation @Repository. Assurez-vous d'importer la bibliothèque nécessaire pour chacune des annotations ajoutées tout au long du tutoriel.

<img src="images\image-20191117132520137.png" alt="image-20191117114342901" style="zoom:150%;" />

### Service

Définissons maintenant le service avec SpringBoot en utilisant l'annotation @Service.

<img src="images\image-20191117132707989.png" alt="image-20191117114342901" style="zoom:150%;" />

Nous utiliserons dans le constructeur l'auto injection de dépendances. SpringBoot nous permet de le faire avec l'annotation @Autowired comme suit.

<img src="images\image-20191117132912268.png" alt="image-20191117114342901" style="zoom:150%;" />

Puisque nous pourrions avoir plusieurs implémentations de l'interface «PersonDataAccessInterface», nous aurons besoin de faire un lien entre l'implémentation voulue de cette interface et son utilisation. Nous le ferons en définissant le paramètre du Repository de l'implémentation et en ajoutant l'annotation @Qualifier("") lorsque nous utilisons une instance de l'interface. Voir les images ci-dessous:

<img src="images\image-20191117133702756.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191117133837204.png" alt="image-20191117114342901" style="zoom:150%;" />

### API

L'auto injection de dépendances devra également se faire pour la couche API. Le même principe devra s'appliquer ici en utilisant l'annotation @Autowired.

<img src="images\image-20191117133106230.png" alt="image-20191117114342901" style="zoom:150%;" />

### Application RestFul avec SpringBoot

SpringBoot permet d'implémenter les fonctionnalités «backend» très rapidement. Voici la marche à suivre pour débuter l'implémentation REST en utilisant SpringBoot.

Définir notre API PersonController.java avec l'annotation @RestController.

<img src="images\image-20191117134204579.png" alt="image-20191117114342901" style="zoom:150%;" />

Définir la méthode «addPerson» de l'API comme une méthode POST au niveau de l'implémentation RestFul. Utilisez l'annotation @PostMapping tel que dans l'exemple ci-dessous.

<img src="images\image-20191117134408662.png" alt="image-20191117114342901" style="zoom:150%;" />

Enfin, nous auront besoin de déterminer le chemin de la requête voulu pour notre application. L'annotation @RequestMapping("") nous permettra de définir ce paramètre dans la couche API de notre application tel que l'image ci-dessous le démonntre. Disons pour le but de ce tutoriel que le chemin d'accès voulu sera «api/v1/person».

<img src="images\image-20191117134906716.png" alt="image-20191117114342901" style="zoom:150%;" />

Donc, suivant l'implémentation effectuée, une requête sur le port 8080 avec le chemin d'accès «api/v1/person» accèdera à l'API que nous venons de développée. Si cette requête est une requête POST, elle accèdera à la méthode «addPerson» ci-dessus.

# POSTMAN

Le logiciel Postman nous permettra d'utiliser un client pour interagir avec notre application web RestFul. Postman permet de faire des requêtes HTTP GET, POST, PUT, DELETE et autres, ce qui sera utile pour nous afin de tester notre implémentation. Un lien vers le logiciel Postman est disponible en page 1 de ce tutoriel.

### Principales fonctionnalités de POSTMAN

Nous utiliserons Postman dans ce tutoriel afin de fournir un paquet JSON sur une requête POST à notre application. Voici une marche à suivre dans le logiciel Postman afin de vous permettre l'envoie d'un paquet JSON sur une requête POST à votre application:

- Créer une requête avec le l'onglet +;
- Sélectionnez une requête POST;
- Entrer le chemin d'accès : localhost:8080/api/v1/person;
- Dans la section Body, sélectionner raw puis au lieu de text, prenez JSON;
- Dans l'onglet «Body» créez le paquet JSON avec l’attribut «name = '' James Bond ''».
- <img src="images\image-20191117140603872.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191117140644826.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191117140729188.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191117140854298.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191117141007649.png" alt="image-20191117114342901" style="zoom:150%;" />

N'envoyez pas la requête immédiatement. Simplement pour bien comprendre ce qui se passe, nous venons de configurer un client qui communiquera avec notre application Web selon le modèle REST et les requêtes HTTP. Il fera une requête POST qui envoi un paquet JSON au chemin d'accès  «localhost:8080/api/v1/person ».

La réception de cette requête est configurée dans notre application en utilisant SpringBoot et ses annotations. Notre classe «PersonController.java» est un RestController qui accepte des requêtes sur le chemin d'accès «api/v1/person». La requête POST utilisera la méthode «addPerson» de notre classe «PersonController.java».

### Un dernier détail avant de faire la requête...

Notre méthode «addPerson(Person person)» de la classe «PersonController.java» doit se faire donner un objet «Person». Cependant, notre client lui enverra un paquet JSON avec l'attribut "name" = "James Bond". Nous dirons à notre application, à l'aide de l'annotation @ResquestBody de SpringBoot, que nous voulons que le corps de la requête POST (paquet JSON) soit entré dans le paramètre «Person» de la méthode «addPerson». Le tout se programme comme suit.

<img src="images\image-20191117142004048.png" alt="image-20191117114342901" style="zoom:150%;" />

### Effectuer la première requête à votre application

Lancer tout d'abord votre application java. Assurez-vous que Spring est marche et que le serveur tomcat est en écoute sur le port 8080.

Lancez maintenant la requête déjà défini à l'aide du bouton «Send» de Postman.

<img src="images\image-20191117142354756.png" alt="image-20191117114342901" style="zoom:150%;" />

Assurez-vous de recevoir le code «Status: 200 OK» de la part de Postman.

<img src="images\image-20191117142459321.png" alt="image-20191117114342901" style="zoom:150%;" />

Dans la console java, des logs devraient avoir défilés si la requête a fonctionnée.

<img src="images\image-20191117142601476.png" alt="image-20191117114342901" style="zoom:150%;" />

Voilà! Votre 1ère requête a fonctionnée!

# Récupération du contenu de la base de données fictive

Notre requête POST a maintenant fonctionné, mais nous ne disposons d'aucun pour savoir ce que contient notre base de données. Nous implémenterons donc le code nécessaire afin de récupérer le contenu de notre liste et de l'afficher à l'aide d'une requête GET.

### Ajout d'une méthode à notre interface

#### PersonDataAccessInterface.java

Ajoutons  la méthode «selectAllPeople» à notre interface.

<img src="images\image-20191117143728180.png" alt="image-20191117114342901" style="zoom:150%;" />

Implémentons maintenant la méthode à travers toutes les couches de l'application.

#### PersonController.java

<img src="images\image-20191117143949941.png" alt="image-20191117114342901" style="zoom:150%;" />

L'annotation @GetMapping définit la méthode «getAllPeople()» comme la méthode à utiliser lors d'une requête GET sans paramètre.

#### PersonService.java

<img src="images\image-20191117144143906.png" alt="image-20191117114342901" style="zoom:150%;" />

#### PersonDataAccessImplementation.java

<img src="images\image-20191117144302255.png" alt="image-20191117114342901" style="zoom:150%;" />

# Postman: Requête «GET»

Effectuer maintenant une requête GET avec le logiciel POSTMAN afin de voir le contenu de votre base de données fictive. Assurez-vous tout d'abord d'arrêter tout processus Spring déjà démarré auparavant. Sauvegardez vos modifications dans le code java. Redémarrez votre application Spring qui inclut maintenant l'implémentation de la requête GET.

### Requête GET

Créez maintenant une nouvelle requête en utilisant le l'onglet +. Placez le même url que pour la requête POST vu précédemment. Appuyez sur le bouton SEND. Assurez-vous de recevoir le code «200 ok». Vous devriez avoir ceci dans le logiciel POSTMAN.

<img src="images\image-20191117144956950.png" alt="image-20191117114342901" style="zoom:150%;" />

Votre requête a fonctionné, mais aucune entrée n'existe présentement dans la liste de la classe «PersonDataAccessImplementation.java». À l'aide de la méthode POST vu précédemment, entrez un à un quelques noms dans votre application. J'entrerai les noms de Ben Clark, Leonardo DiCaprio, Jean Lebrun et Jeanne Lablanche.

Après avoir effectué ces 4 requêtes POST, effectuez maintenant une requête GET, toujours à l'url  «localhost:8080/api/v1/person ». Vous devriez obtenir le résultat suivant dans POSTMAN.

<img src="images\image-20191117145500089.png" alt="image-20191117114342901" style="zoom:150%;" />

Notez que notre implémentation de la méthode POST crée un UUID automatiquement (auto-généré). On voit donc ici, pour chacune des personnes présente dans notre liste, un id et un nom, respectant le modèle définit dans la classe «Person.java» du package model.

Si vous utilisez le navigateur web de votre choix, vous devriez être en mesure d'obtenir un résultat identique, mais présenté de façon différente. Voici un essai en utilisant le navigateur Chrome de Google.

<img src="images\image-20191117145825019.png" alt="image-20191117114342901" style="zoom:150%;" />

Notre client, que ce soit POSTMAN ou un navigateur web, fait donc l'interaction avec la couche API de notre application. Celle-ci communique avec le service, qui communique avec la couche accès de données afin de retourner à travers la couche service à l'API les informations demandés, le tout respectant le modèle MVC présenté en début de tutoriel.

# Implémentation RestFul des méthodes HTTP

Ajoutons les méthodes suivantes à notre interface: «deletePersonById» et «updatePersonById» et «selectPersonById».

### PersonDataAccessInterface.java

<img src="images\image-20191117150726509.png" alt="image-20191117114342901" style="zoom:150%;" />

### PersonDataAccessImplementation.java

<img src="images\image-20191117150927778.png" alt="image-20191117114342901" style="zoom:150%;" />

### PersonService.java

<img src="images\image-20191117151145970.png" alt="image-20191117114342901" style="zoom:150%;" />

### PersonController.java

<img src="images\image-20191117151559090.png" alt="image-20191117114342901" style="zoom:150%;" />

Nous ajoutons ici 3 méthodes. Voyons comment SpringBoot nous simplifie la vie dans l'implémentation de nos méthodes HTTP.

Tout d'abord, nous ajoutons une méthode «getPersonById», qui est elle aussi est un @GetMapping. Elle permettra, selon une requête GET qui fournit un ID, de retourner la personne avec l'id qui correspond à la requête. L'annotation @PathVariavle est expliquée dans le paragraphe qui suit.

Nous implémentons également une méthode «deletePersonById» selon l'annotation @DeleteMapping, qui fonctionnement comme les autres annotations HTTP de SpringBoot. Notez le chemin fournit dans l'annotation et le @PathVariable inclut à côté du paramètre UUID. Comme vous vous en doutez peut-être, ceci nous permet de prendre le paramètre fournit dans l'url de la requête et de lui donner le nom «id» (@DeleteMapping(path = "{id}")). Ensuite, l'annotation «@PathVariable("id")» fournit cet information interprétée comme le champ ayant le nom «id» et l'insère dans le paramètre UUID de la méthode. SpringBoot permet donc la conversion rapide d'informations présentent dans la requête url en paramètre des méthodes de notre API REST.

# Vérification avec POSTMAN

Arrêtez tout processus Spring démarrés précédemment. Redémarrez un nouveau processus Spring à partir de votre application java. 

À l'aide de Postman, insérez quelques noms dans la liste de base de donnée fictive. J'insérerai les même noms que dans l'exemple précédent.

Faites maintenant une requête GET afin de ressortir les informations enregistrés dans votre application. Voici mes résultats.

<img src="images\image-20191117152719928.png" alt="image-20191117114342901" style="zoom:150%;" />

Nous allons maintenant créer une requête DELETE avec l'id de la personne que nous voulons supprimer. Je choisirai Ben Clark. Nous devrons placer l'id dans l'url de la requête après le champ «person». Voici donc ma requête.

<img src="images\image-20191117152959895.png" alt="image-20191117114342901" style="zoom:150%;" />

Appuyez sur le bouton SEND et assurez-vous de recevoir le code «200 ok».

Effectuez maintenant une nouvelle requête GET afin de voir si la personne a été supprimé.

<img src="images\image-20191117153125031.png" alt="image-20191117114342901" style="zoom:150%;" />

Le tout a fonctionné. Faisons maintenant l'update du nom «Jeanne Lablanche» pour le nom «Jeanne Labrune». Nous ferons donc une requête PUT en fournissant un paquet JSON qui contiendra un champ «name» différent de l'original. Nous devrons fournir dans l'url de la requête l'id de la personne ciblée. Voici donc ma requête.

<img src="images\image-20191117153509155.png" alt="image-20191117114342901" style="zoom:150%;" />

Appuyez sur le bouton SEND et assurez-vous de recevoir le code «200 ok».

Voyons maintenant si Jeanne qui était blanche est devenue brune avec une notre requête PUT. Faisons à nouveau une requête GET pour voir le contenu de notre base de données.

<img src="images\image-20191117153622619.png" alt="image-20191117114342901" style="zoom:150%;" />

Pour terminer, tentons de faire une requête GET en fournissant l'id de la personne voulue. Je vais tenter de faire ressortir seulement Leonardo DiCaprio à l'aide de son id dans une requête GET à partir du navigateur Chrome, comme suit.

<img src="images\image-20191117153804355.png" alt="image-20191117114342901" style="zoom:150%;" />

Le tout fonctionne à merveille et complète l'implémentation complète de notre API RestFul en utilisant Spring et les fonctionnalités de SpringBoot.

# Implémentation de certaines validations

Nous pouvons également, à l'aide de SpringBoot, s'assurer que certains champs ne seront pas nulles lors de l'insertion ou de la modification de données. Nous utiliserons alors les annotations @NotBlank, @Valid et @NonNull afin d'effectuer ces validations. Le tout permettra de refuser l'insertion nulle lors d'une requête POST, par exemple.

### Person.java

<img src="images\image-20191117154503523.png" alt="image-20191117114342901" style="zoom:150%;" />

### PersonController.java

<img src="images\image-20191117154612025.png" alt="image-20191117114342901" style="zoom:150%;" />

Remarquez ici les méthodes «addPerson» et «updatePersonById».

## Test

Arrêtez tout processus Spring démarrés précédemment. Redémarrez un nouveau processus Spring à partir de votre application java. 

Effectuez maintenant un test avec une requête POST qui contiendrait paquet JSON qui comporte un élément comme suit: «"name": ""». Vous devriez alors obtenir le message d'erreur suivant dans le logiciel POSTMAN.

<img src="images\image-20191117155115055.png" alt="image-20191117114342901" style="zoom:150%;" />

On vous mentionne entre autre un code d'erreur 400 et : "default message": "ne peut pas être vide". On voit donc qu'il a eu une validation pour s'assurer que le champ nom contient des données lors de l'insertion sur une requête POST.

# Injection de dépendances avec SpringBoot

Nous avons discutés précédemment qu'il serait possible d'avoir plusieurs implémentations de notre interface dans notre application. SpringBoot permet de faciliter l'utilisation des interfaces voulues avec l'injection de dépendances. Créons une 2e implémentation de notre interface dans le package «dataAccess» et nommons la «PersonDataAccessNouvelleImplementation.java». Cette classe implémente bien sûr l'interface «PersonDataAccessInterface.java».

Notre nouvelle implémentation sera elle aussi définit comme un @Repository, mais nous lui fourniront le paramètre "nouvelleImplementation" au lieu de "implementationVoulu". Nous implémenterons simplement la méthode «selectAllPeople» pour le but de la démonstration. Voici donc la définition de la nouvelle implémentation.

<img src="images\image-20191117160124693.png" alt="image-20191117114342901" style="zoom:150%;" />

La méthode «selectAllPeople» crée donc une seule personne et lui donne le nom «Ceci est ma nouvelle implémentation et ça fonctionne!». En faisant une requête GET sur cette implémentation, nous devrions donc recevoir une personne avec le nom comme nous l'avons définit dans cet interface.

Pour dire à Spring de faire l'injection de dépendances et modifier notre application, nous avons simplement à aller dans notre classe «PersonService.java» et à modifier l'annotation @Qualifier avec le nom de notre repository comme suit.

<img src="images\image-20191117160458276.png" alt="image-20191117114342901" style="zoom:150%;" />

### Test

Sauvegardez toute modification à votre code d'application java. Arrêtez tout processus Spring démarrés précédemment. Redémarrez un nouveau processus Spring à partir de votre application java. 

Faites maintenant une requête GET avec POSTMAN à l'url «localhost:8080/api/v1/person ». Essayez également à partir de votre navigateur web. Vous serez en mesure de confirmer que votre nouvelle interface est maintenant utilisée lors d'une requête GET sans paramètre en entrée. Voici mes résultats avec POSTMAN et navigateur Chrome.

<img src="images\image-20191117160815719.png" alt="image-20191117114342901" style="zoom:150%;" />

<img src="images\image-20191117160837336.png" alt="image-20191117114342901" style="zoom:150%;" />

# Pour aller un peu plus loin (tutoriel avancé)

Implémentation de base de donnée PostgreSQL en utilisant Docker, flyway et SpringBoot JDBC.

# Dépannage (Troubleshooting)

#### Erreur en ligne 1 du fichier POM.XML:

m2econnector n'est pas installé. Suivre les étapes suivantes:

Pour l'installation de Maven, assurez-vous que votre IDE comprend l'installation du plugin suivant:

- m2e connector for mavenarchiver plugin 0.17.3 disponible au lien suivant: https://download.eclipse.org/m2e-wtp/releases/1.4/  .

Dans le cas où le plugin ne serait pas installé, vous aurez une erreur en ligne 1 de votre fichier POM.XML.

Pour Eclipse, effectuer la marche à suivre suivante:

- Eclipse -­> Help -> install new software;
- Insérez maintenant le lien de download ci-dessus du m2e connector dans la case «Work with»;
- Des options seront maintenant disponibles;
- Sélectionnez M2E Maven Archiver Connector (3rd party);
- Cliquez sur «Finish»;
- L'extension s'installera;
- Une fois terminé, redémarrez Eclipse ou l'IDE de votre choix.

#### Le projet affiche un x rouge d'erreur

Maven a parfois besoin de faire la mise à jour des informations du projet.

Essayez la démarche suivante pour Eclipse:

- Clique droit sur votre projet;
- Maven;
- Update Project...;
- Cliquez sur «ok».

Ceci mettra à jour les liens Maven pour votre projet et résoudra parfois certaines erreurs de synchronisation.
