# Article-SPRING

##  Description
**Article-SPRING** est une application Java développée avec le framework **Spring Boot**, conçue pour gérer un catalogue d'articles.  
Elle permet d'effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer) sur une base de données relationnelle, offrant ainsi une interface simple pour interagir avec les données.

## Fonctionnalités principales
- **Gestion des articles** : ajouter, modifier, supprimer et consulter les informations des articles.
- **Connexion à la base de données** : utilisation de Spring Data JPA pour interagir avec une base relationnelle.
- **Interface RESTful** : expose des endpoints pour interagir avec les articles via HTTP.
- **CRUD complet** : implémentation des opérations de gestion des données.
- **Sécurité** : intégration de Spring Security pour sécuriser les endpoints.

## Architecture & Technologies
- **Langage** : Java (100 %)
- **Framework** : Spring Boot
- **Base de données** : relationnelle (configuration à préciser)
- **Structure** :
  - `src/` → code source principal
  - `pom.xml` → fichier de configuration Maven
  - `.gitignore` → fichiers à ignorer pour le versionnement

## Installation
1. Clonez ou téléchargez le projet :
    ```bash
    git clone https://github.com/khayatti1/Article-SPRING.git
    ```
2. Accédez au répertoire du projet :
    ```bash
    cd Article-SPRING
    ```
3. Compilez le projet avec Maven :
    ```bash
    mvn clean install
    ```
4. Configurez votre base de données dans le fichier `application.properties` ou `application.yml`.
5. Lancez l'application :
    ```bash
    mvn spring-boot:run
    ```

## Utilisation
- L'application expose des endpoints REST pour interagir avec les articles.
- Vous pouvez utiliser un outil comme Postman ou curl pour tester les différents endpoints.
- Les opérations CRUD sont disponibles via les méthodes HTTP correspondantes (GET, POST, PUT, DELETE).

