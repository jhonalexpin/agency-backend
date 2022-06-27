# agency-backend

Deploying locally Spring Boot Application and MongoDB as Containers Using Docker and Docker Compose

**Steps & Commands**

- [x] pull mongo image from docker hub **`docker pull mongo:latest`**
- [x] run mongo image **`docker run -d -p 27017:27017 --name mongodb mongo:latest`**
- [x] dockerize spring boot application **`docker build -t agency-backend:1.1 .`**
- [x] run spring boot docker image and link that container to mongo container 
   **`docker run -p 8081:8080 --name agency-backend --link mongodb:mongo -d agency-backend:1.0`**
- [x] check docker running containers  **`docker ps`** it should display two container ids
- [x] check logs of spring boot image **`docker logs agency-backend`**
- [x] if all good access your api
```bash
curl --location --request GET 'http://localhost:8081/agency/all' 
```
- [x] login to mongo terminal to verify records **`docker exec -it mongodb bash`**
- type mongo and enter
- show dbs
- use test
- show collections
- db.agency.find().pretty()

**Use Docker Compose**

- [x] Kill running container:
```
docker rm <containerId>
```

#### docker-compose.yml

```yaml
version: "3"
services:
  mongodb:
    image: mongo:latest
    container_name: "mongodb"
    ports:
      - 27017:27017

  agency-backend:
    image: agency-backend:1.1
    container_name: agency-backend
    ports:
      - 8081:8080
    links:
      - mongodb
    restart: on-failure
    depends_on:
      - mongodb
```
- [x] navigate to root folder project:
```
agency-backend/ and run docker-compose up
```
