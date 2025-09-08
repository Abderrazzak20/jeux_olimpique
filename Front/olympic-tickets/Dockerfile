FROM node:22.12-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

RUN npm run build

FROM nginx:alpine

COPY --from=build /app/dist/olympic-tickets/browser /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf.template

EXPOSE $PORT

CMD envsubst '$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'
