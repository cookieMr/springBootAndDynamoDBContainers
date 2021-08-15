FROM openjdk:11-jre

WORKDIR /app/.
COPY ./web/target/web-0.1.jar /app/backend.jar

EXPOSE 8082

CMD [ "java", "-jar", "/app/backend.jar" ]
