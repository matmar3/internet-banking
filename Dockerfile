# Stage 1 - build the app
FROM maven:3.6-jdk-8
COPY . /build
WORKDIR /build

RUN mvn clean install -DskipTests
