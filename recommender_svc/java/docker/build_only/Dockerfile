FROM gradle:4.8-jdk8 as builder
COPY build.gradle /home/gradle/.
COPY settings.gradle /home/gradle/.
# Download dependencies
RUN gradle assemble -x bootJar
ADD src /home/gradle/src
ADD config /home/gradle/config
WORKDIR /home/gradle
