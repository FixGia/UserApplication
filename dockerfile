FROM openjdk:19

VOLUME /tmp

EXPOSE 9011

ARG JAR_FILE=target/UserApplication-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]