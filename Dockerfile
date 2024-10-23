FROM openjdk:22
LABEL authors="Rafael Corrêa"
WORKDIR /src
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar outfit-recommendation-0.0.1-SNAPSHOT.jar

#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "outfit-recommendation-0.0.1-SNAPSHOT.jar"]