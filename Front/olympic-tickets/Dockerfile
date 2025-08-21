# Stage 1: Build
FROM node:22.12-alpine AS build

# Imposta la cartella di lavoro
WORKDIR /app

# Copia package.json e package-lock.json
COPY package*.json ./

# Installa le dipendenze
RUN npm ci

# Copia tutto il resto del progetto
COPY . .

# Costruisci l'app Angular
RUN npm run build

# Stage 2: Serve con Nginx
FROM nginx:alpine

# Copia la build Angular nella cartella di Nginx
COPY --from=build /app/dist/olympic-tickets /usr/share/nginx/html

# Copia eventuale configurazione Nginx (opzionale)
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Espone la porta 82
EXPOSE 8082

# Avvia Nginx
CMD ["nginx", "-g", "daemon off;"]
