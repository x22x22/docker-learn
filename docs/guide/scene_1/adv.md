# 进阶版

## 前言

可以看出[基础版](./base.md)中会有以下缺点

1. 构建jar包时要依赖本地环境, 本地环境必须安装`JDK 14`, `gradle 6.5`等工具;
1. `order-server`访问数据库的时候需要先查询数据库的IP, 再写入到环境变量中, 不够灵活;

所以我们可以对以上问题进行改进.

## 构建

1. 编写`order-server`的Dockerfile

    ***PS***: Dockerfile保持到`./demo/order-server目录下`

    ```Dockerfile
    # 构建jar阶段
    FROM gradle:6.5.0-jdk14 AS builder # 给buid阶段命名别名为builder
    WORKDIR /build
    COPY ./ /build
    # 将gradle国内源放入镜像, 加速构建过程
    COPY ./init.gradle /home/gradle/.gradle/init.gradle
    RUN gradle :order-server:build -x test

    # 构建order-server运行时镜像阶段
    FROM adoptopenjdk:14.0.1_7-jdk-openj9-0.20.0-bionic
    WORKDIR /app
    ENV TZ="Asia/Shanghai"
    # 从构建jar阶段中将jar产物拷贝到当前阶段容器镜像中
    COPY --from=builder /build/order-server/build/libs/*.jar /app/order-server.jar

    CMD ["/bin/bash", "-c", "java ${JVM_ARGS_BEFORE} -jar /app/order-server.jar ${JVM_ARGS_LATER}"]

    EXPOSE 38080

    ```

## 启动

1. 启动mysql服务, 这里用的是`mariadb:10.5.4`

    * *Line.1*: # 指定容器名称
    * *Line.2*: # 指定外-内映射端口
    * *Line.3*: # 指定数据库ROOT密码
    * *Line.4*: # 指定数据库普通用户名
    * *Line.5*: # 指定数据库普通用户的密码
    * *Line.6*: # 指定需要创建的数据库库(schema)名
    * *Line.7*: # 后台运行
    * *Line.8*: # 镜像名和tag

    ```bash
    docker run --name shopping-mall-mariadb \
      -p 33060:3306 \
      -e MYSQL_ROOT_PASSWORD=x22x22 \
      -e MYSQL_USER=shopping-mall-mariadb \
      -e MYSQL_PASSWORD=shopping-mall-mariadb \
      -e MYSQL_DATABASE=shopping-mall-mariadb \
      -d \
      mariadb:10.5.4
    ```

1. 启动order-server

    * *Line.5*: link到MYSQL容器, 并设置别名为`db`
    * *Line.6*: 设置数据库url, 这里使用的是link中对于MYSQL容器设置的别名

    ```bash
    docker run -it \
      --rm \
      --name order-server \
      -p 38080:38080 \
      --link shopping-mall-mariadb:db \
      -e spring.r2dbc.url=r2dbc:mysql://db:3306/shopping-mall-mariadb \
      order-server:0.0.1-SNAPSHOT
    ```

## 验证

1. 获取容器NAT IP
   1. 如果使用[docker desktop](https://www.docker.com/products/docker-desktop)安装的docker, 可以直接使用127.0.0.1进行容器的访问
   1. 如果使用[docker toolbox](https://github.com/docker/toolbox/releases)安装的docker, 请运行以下命令获得容器的IP

      ```bash
      docker-machine env
      # 回显关键内容
      # DOCKER_HOST=tcp://192.168.99.101:2376
      # 其中192.168.99.101为容器的访问IP
      ```

1. 调用`order-server`服务接口

    * 浏览器中直接打开<http://127.0.0.1:38080/order>

      ***PS***: 如果是[docker toolbox](https://github.com/docker/toolbox/releases)方式安装的, 请将上面的`127.0.0.1`部分修改为`获取容器NAT IP`章节中获取到的IP, 后面需要访问其它容器也是如此, 就不再重复提示.
    * 调用结果:

      ![调用`order-server`服务接口](./assets/images/call_order_server_01.png)

1. 连接MYSQL查询数据

   使用MYSQL连接工具, IP填写为`127.0.0.1`, 端口填写为`33060`, 账号密码为`shopping-mall-mariadb`
