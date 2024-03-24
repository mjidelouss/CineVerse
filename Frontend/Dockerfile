FROM node:18.17.0 as build

WORKDIR /app

COPY package*.json ./

RUN npm install -g npm@10.3.0

RUN npm install -g @angular/cli

COPY . .

RUN npm install

RUN ng build --configuration=production

FROM nginx:latest

COPY --from=build app/dist/cinverse-angular /usr/share/nginx/html

EXPOSE 80