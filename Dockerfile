# Stage 1 - build the app
FROM maven:3.6-jdk-8
COPY . /build
WORKDIR /build

RUN mvn clean install -DskipTests

#Stage 2 - Final image
FROM openjdk:8-jdk-alpine

COPY --from=0 /build/target/*.war internet-banking.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/internet-banking.war"]
