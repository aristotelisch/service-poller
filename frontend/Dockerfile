FROM node:14.17.1 as build-deps
WORKDIR /usr/src/app
COPY package.json yarn.lock ./
RUN npm install
COPY . ./
RUN npm run build

FROM nginx:1.20.1-alpine
COPY --from=build-deps /usr/src/app/build /usr/share/nginx/html
COPY --from=build-deps /usr/src/app/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
WORKDIR /usr/share/nginx/html
