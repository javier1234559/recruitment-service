# Maven build container
FROM eclipse-temurin:17-jdk AS maven_build
# Set the base image to Eclipse Temurin JDK 17 and name it "maven_build".

WORKDIR /tmp/
# Set the working directory inside the container to /tmp/.

COPY .mvn /tmp/.mvn/
COPY mvnw /tmp/
#COPY checkstyle.xml /tmp/
COPY pom.xml /tmp/
COPY src /tmp/src/
# Copy Maven configuration files, Maven wrapper script, POM file, and source code into the container's /tmp/ directory.

RUN ./mvnw package -Dmaven.test.skip=true
# Run Maven build inside the container, excluding test execution.

#pull base image
FROM openjdk:17-jdk-slim
# Set another base image using OpenJDK 17 in a slim variant.

EXPOSE 80
# Expose port 80 from the container to the host machine (informative only).

ENV JAVA_OPTS="-Xms256m -Xmx2048m"
# Set environment variable JAVA_OPTS with specific JVM options.

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
# Specify the entry point to run the Java application with defined JVM options and JAR file.

COPY --from=maven_build /tmp/target/*.jar /app.jar
# Copy the compiled JAR file from the "maven_build" stage to the root directory of the new image, renaming it to app.jar.
