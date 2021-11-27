FROM ubuntu:16.04
WORKDIR /app/
RUN apt-get update && apt-get install -y openalpr openalpr-daemon openalpr-utils libopenalpr-dev
RUN apt-get install -y openjdk-8-jdk
RUN apt-get -y install maven
RUN apt-get -y install git-all
RUN git clone https://github.com/mauriciocordeiro/alpr4j.git
# RUN git clone https://github.com/mauriciocordeiro/openalpr.br.git
# WORKDIR /app/openalpr.br/runtime_data/
# RUN cp -R * /usr/share/openalpr/runtime_data/
WORKDIR /app/alpr4j/
EXPOSE 8080
RUN mvn clean package spring-boot:repackage
ENTRYPOINT ["java","-jar","target/alpr4j.jar"]