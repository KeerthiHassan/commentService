FROM openjdk:8
EXPOSE 3015

ADD target/comment-service.jar comment-service.jar
ENTRYPOINT ["java","-jar","comment-service.jar"]