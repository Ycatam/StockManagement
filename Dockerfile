# the first stage of our build will use a maven 4.0.0 parent image
FROM maven:3.8.6-jdk-11 AS MAVEN_BUILD

# copy the pom and src code to the container
COPY ./ ./

# package our application code
RUN mvn clean package

# the second stage of our build will use open jdk 11 on alpine 3.9
FROM openjdk:11

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /docker-multi-stage-build-quotation-management/target/quotation-management-0.0.1-SNAPSHOT.jar /quotation-management.jar

# port expose
EXPOSE 8081

# set the startup command to execute the jar
CMD ["java", "-jar", "/quotation-management.jar"]