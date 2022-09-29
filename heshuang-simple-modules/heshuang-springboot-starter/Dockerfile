FROM openjdk:14
MAINTAINER heshuang <hh19940831959@gmail.com>

COPY ./target/springboot-start.jar /opt/work/app.jar

ENV JAVA_OPTS="" \
    SERVER_PORT=8080 \
    LANG=en_US.UTF-8 \
    LANGUAGE=en_US.UTF-8

WORKDIR /opt/work

EXPOSE 8080


ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar xxx.jar