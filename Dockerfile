FROM maven:latest as build
WORKDIR /back
COPY . /back/.
RUN mvn -f /back/pom.xml clean package -DskipTests

FROM amazoncorretto:17.0.12 as develop-runtime
WORKDIR /back
COPY --from=build /back/target/*.jar back/back.jar
EXPOSE 8088
CMD ["java", "-jar", "back/back.jar"]