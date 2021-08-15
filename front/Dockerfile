FROM openjdk:11-jre

WORKDIR /app/.
COPY ./web/target/web-0.1.jar /app/front.jar

EXPOSE 8081

CMD [ "java", "-jar", "/app/front.jar" ]
