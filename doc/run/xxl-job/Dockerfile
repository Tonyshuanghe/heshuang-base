FROM openjdk:8-jre-slim
##设置默认语言环境
ENV LANG=C.UTF-8

ENV TimeZone=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TimeZone /etc/localtime && echo $TimeZone > /etc/timezone

ARG SERVER_PORT
ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_HOST
ARG DB_PORT
ARG DB_DATABASE
ARG MAIL_USERNAME
ARG MAIL_PASSWORD
ARG MAIL_FROM
ARG XXL_JOB_TOKEN


ENV SERVER_PORT=${SERVER_PORT} \
    DB_USERNAME=${DB_USERNAME} \
    DB_PASSWORD=${DB_PASSWORD} \
    DB_HOST=${DB_HOST} \
    DB_PORT=${DB_PORT} \
    DB_DATABASE=${DB_DATABASE} \
    MAIL_USERNAME=${MAIL_USERNAME} \
    MAIL_PASSWORD=${MAIL_PASSWORD} \
    MAIL_FROM=${MAIL_FROM}\
    XXL_JOB_TOKEN=${XXL_JOB_TOKEN}

ADD xxl-job-admin-*.jar /app.jar

ENV JAVA_OPTS="-Xms1024m -Xmx1024m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/soft/dump"

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /app.jar "]