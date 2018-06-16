FROM gradle:4.8-jdk8 as builder
COPY recommender_svc/java/build.gradle /home/gradle/.
COPY recommender_svc/java/settings.gradle /home/gradle/.
# Download dependencies
RUN gradle assemble -x bootJar
ADD recommender_svc/java/src /home/gradle/src
ADD recommender_svc/java/config /home/gradle/config
WORKDIR /home/gradle
