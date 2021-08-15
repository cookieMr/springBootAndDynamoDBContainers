#!/bin/sh

# A sleep to give time for other containers to start up and be healty
sleep 30

curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2008,\"title\":\"Iron Man\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2008,\"title\":\"The Incredible Hulk\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2010,\"title\":\"Iron Man 2\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2011,\"title\":\"Thor\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2011,\"title\":\"Captain America: The First Avenger\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2012,\"title\":\"Marvel''s The Avengers\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2013,\"title\":\"Iron Man 3\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2013,\"title\":\"Thor: The Dark World\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2014,\"title\":\"Captain America: The Winter Soldier\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2014,\"title\":\"Guardians of the Galaxy\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2015,\"title\":\"Avengers: Age of Ultron\"}"
curl -X POST "http://docker-java-front:8081/api/movies" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"releaseYear\":2015,\"title\":\"Ant-Man\"}"

sleep 3

curl -X GET "http://docker-java-front:8081/api/movies" -H "accept: application/json"
