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
COPY --from=build /app/dist/olympic-tickets/browser /usr/share/nginx/html


# Copia il template di Nginx (invece del file statico)
COPY nginx.conf /etc/nginx/conf.d/default.conf.template

# Espone la porta dinamica di Railway
EXPOSE $PORT

# Avvia Nginx sostituendo $PORT dinamicamente
CMD envsubst '$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
