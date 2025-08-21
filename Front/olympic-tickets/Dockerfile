FROM nginx:alpine

# Copia build Angular
COPY --from=build /app/dist/olympic-tickets /usr/share/nginx/html

# Copia template nginx
COPY nginx/default.conf.template /etc/nginx/conf.d/default.conf.template

# Espone la porta dinamica
EXPOSE $PORT

# Sostituisci la porta dinamicamente e avvia Nginx
CMD envsubst '$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
