FROM gradle:6.5.0-jdk14 AS builder
WORKDIR /build
COPY ./ /build
COPY ./init.gradle /home/gradle/.gradle/init.gradle
RUN gradle :order-server:build -x test

FROM adoptopenjdk:14.0.1_7-jdk-openj9-0.20.0-bionic
WORKDIR /app
ENV TZ="Asia/Shanghai"
COPY --from=builder /build/order-server/build/libs/*.jar /app/order-server.jar

CMD ["/bin/bash", "-c", "java ${JVM_ARGS_BEFORE} -jar /app/order-server.jar ${JVM_ARGS_LATER}"]

EXPOSE 38080
