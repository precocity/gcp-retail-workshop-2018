FROM gradle:4.8-jdk8 as builder
COPY build.gradle /home/gradle/.
COPY settings.gradle /home/gradle/.
# Can gradle just resolve dependencies? 
#RUN mvn dependency:resolve dependency:resolve-plugins
ADD src /home/gradle/src
ADD config /home/gradle/config
WORKDIR /home/gradle
RUN gradle clean build

FROM openjdk:8u171-jdk-stretch
EXPOSE 8080
COPY --from=builder /home/gradle/build/libs/retail-recommender-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]