FROM maven:3.8.1-adoptopenjdk-8
WORKDIR /app/check4j/
COPY . . 
RUN mvn clean package spring-boot:repackage
EXPOSE 8081
ENTRYPOINT ["java","-jar","target/check4j.jar"]