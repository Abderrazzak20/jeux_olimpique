# Stage 1: Build
FROM node:22.12-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Stage 2: Serve con Nginx
FROM nginx:alpine
COPY --from=build /app/dist/olympic-tickets /usr/share/nginx/html

# Espone la porta 8082 (solo informativa per Docker)
EXPOSE 8082

# Avvia Nginx su porta 8082 usando sed per modificare il default.conf al volo
CMD ["sh", "-c", "sed -i 's/listen 80;/listen 8082;/' /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
