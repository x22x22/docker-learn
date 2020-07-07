FROM gradle:6.5.0-jdk14 AS builder
WORKDIR /build
COPY ./ /build
COPY ./init.gradle /home/gradle/.gradle/init.gradle
RUN gradle :shopping-cart-server:build -x test

FROM adoptopenjdk:14.0.1_7-jdk-openj9-0.20.0-bionic
WORKDIR /app
ENV TZ="Asia/Shanghai"
COPY --from=builder /build/shopping-cart-server/build/libs/*.jar /app/shopping-cart-server.jar

CMD ["/bin/bash", "-c", "java ${JVM_ARGS_BEFORE} -jar /app/shopping-cart-server.jar ${JVM_ARGS_LATER}"]

EXPOSE 38080
