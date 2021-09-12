FROM ubuntu:20.04

RUN apt-get update

# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get install -y maven && \
    apt-get clean;
    
# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME

# Install NodeJS & Angular
RUN apt-get update && \
    apt-get install -y nodejs && \
    apt-get install -y npm && \
    npm install -g @angular/cli && \
    apt-get clean;

# Install Git
RUN apt-get update && \
    apt-get install -y git && \
    apt-get clean;

EXPOSE 80
EXPOSE 3000
EXPOSE 4000
EXPOSE 4200
EXPOSE 5000
EXPOSE 5001
EXPOSE 5002
EXPOSE 8080
EXPOSE 8081

RUN useradd -m developer
USER developer
RUN echo $HOME
