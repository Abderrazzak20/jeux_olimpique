# Stage 1: Build
FROM node:22.12-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

# Build Angular (production di default)
RUN npm run build

# Stage 2: Serve con Nginx
FROM nginx:alpine

# Copia la build Angular (attenzione: cartella browser!)
COPY --from=build /app/dist/olympic-tickets/browser /usr/share/nginx/html

# Copia il template nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf.template

# Imposta porta di default (utile in locale o se Railway non setta)
ENV PORT=8080

EXPOSE $PORT

# Avvia Nginx sostituendo la variabile PORT
CMD envsubst '$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
