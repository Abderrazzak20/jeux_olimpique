🏅 Olympic Tickets

Application web pour la gestion et la réservation de billets pour les Jeux Olympiques.
Le projet est basé sur un backend Spring Boot (Java 17 + MySQL) et un frontend Angular 20.

✅ Prérequis

Avant de lancer le projet, assurez-vous d’avoir installé :

Java 17

Maven 3.9+

Node.js 22.12.0

Angular CLI (npm install -g @angular/cli)

MySQL

⚙️ Installation & Configuration
🔹 Backend (Spring Boot)

Cloner le dépôt et entrer dans le dossier backend :

git clone https://github.com/Abderrazzak20/jeux_olimpique.git
cd jeux_olimpique/back


Configuration de la base MySQL avec Railway et Spring Boot

Créer la base

Va sur Railway
.

Crée un service MySQL.

Récupérer les informations
Dans l’onglet Variables de Railway, note :

MYSQLHOST 

MYSQLPORT 

MYSQLDATABASE 

MYSQLUSER 

MYSQLPASSWORD 

Construire l’URL JDBC: jdbc:mysql://MYSQLHOST:MYSQLPORT/MYSQLDATABASE?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC

Configurer src/main/resources/application.properties

spring.datasource.url=${DB_URL}

spring.datasource.username=${DB_USERNAME}

spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=create

spring.jpa.show-sql=true

server.port=8080

🔹 Frontend (Angular)

Aller dans le dossier frontend :

cd jeux_olimpique/olympic-tickets/frontend


Installer les dépendances :

npm install


Lancer l’application:

npm start


Frontend disponible sur http://localhost:4200

 Déploiement avec Docker & Railway

Un Dockerfile et un fichier nginx.conf sont déjà fournis dans frontend/olympic-tickets/.

Ce Dockerfile :

Compile l’application Angular

Sert les fichiers statiques via Nginx

Utilise la variable d’environnement $PORT imposée par Railway

Déploiement sur Railway
Étapes :

Pousser votre projet sur GitHub

Aller sur Railway

Créer un nouveau projet et connecter le repo GitHub

Définir le dossier pour le frontend et le backend dans les paramètres de Railway

Ajouter vos variables d’environnement :

SPRING_DATASOURCE_URL

SPRING_DATASOURCE_USERNAME

SPRING_DATASOURCE_PASSWORD

Railway déploiera automatiquement le frontend et le backend 

Fonctionnalités principales

Gestion des utilisateurs (Admin / Client)

Réservation de billets

Génération et validation des QR Codes

Sécurisation avec JWT

Tableau de bord administrateur :

Gestion des offres

Suivi des ventes

Liste des réservations

Panel de ventes
