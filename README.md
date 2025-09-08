Olympic Tickets
Application web pour la gestion et la réservation de billets pour les Jeux Olympiques.
Le projet est basé sur un back-end Spring Boot (Java 17 + MySQL) et un front-end Angular 20.

Avant de lancer le projet, assurez-vous d’avoir installé :

Java 17
Maven 3.9+
Node.js 22.12.0
Angular CLI (npm install -g @angular/cli)
MySQL (ou une base Railway déjà configurée)

Installation & Configuration
Back-end (Spring Boot)

Cloner le dépôt et entrer dans le dossier back-end :
git clone https://github.com/username/olympic-tickets.git
cd olympic-tickets/backend

Configurer la base de données dans src/main/resources/application.properties :
spring.datasource.url=jdbc:mysql://localhost:3306/olympic_tickets
spring.datasource.username=root
spring.datasource.password=VOTRE_MOT_DE_PASSE
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080

Front-end (Angular)
Aller dans le dossier front-end :
cd olympic-tickets/frontend

Installer les dépendances :
npm install

Lancer l’application en mode développement :
npm start
Front disponible sur http://localhost:4200
Déploiement avec Docker
Construire l’image Angular :
docker build -t olympic-frontend .
Lancer le conteneur :
docker run -p 8080:8080 olympic-frontend

Fonctionnalités principales:
Gestion des utilisateurs (Admin / Client)
Réservation de billets
Génération & validation des QR Codes
Sécurisation avec JWT
Tableau de bord administrateur (offres, ventes, réservations,Panel de Ventes)