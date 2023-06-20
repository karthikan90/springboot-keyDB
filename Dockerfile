FROM openjdk:8-alpine
add target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]