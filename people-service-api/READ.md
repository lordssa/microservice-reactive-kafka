docker network create shared_network
docker build -t peopleflux .

docker run -d -p 8080:8080 peopleflux --link mongodb:mongo --name spring-boot-mongodb