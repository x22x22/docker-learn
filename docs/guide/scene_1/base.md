# 基础版

## 构建

1. 构建jar包

   ```bash
   # 进入./demo/order-server目录下
   gradle :order-server:build -x test
   ```

1. 编写`order-server`的Dockerfile

    ***PS***: Dockerfile保持到`./demo/order-server目录下`

    ```Dockerfile
    # 引用指定基础镜像
    FROM adoptopenjdk:14.0.1_7-jdk-openj9-0.20.0-bionic
    # 设置工作目录
    WORKDIR /app
    # 设置默认环境变量, 这里设置的是系统内市区为上海时区
    ENV TZ="Asia/Shanghai"
    # 将容器外的本地存储jar包拷贝到容器镜像内
    COPY ./order-server/build/order-server/build/libs/*.jar /app/order-server.jar

    # 指定容器启动后运行的命令
    CMD ["/bin/bash", "-c", "java ${JVM_ARGS_BEFORE} -jar /app/order-server.jar ${JVM_ARGS_LATER}"]

    # 声明容器暴露的端口
    EXPOSE 38080
    ```

1. 构建`order-server`镜像

    * *Line.3*: # 指定Dockerfile文件路径(所以Dockerfile文件名不一定是Dockerfile)
    * *Line.4*: # 指定构建镜像后的镜像名和tag
    * *Line.5*: # 容器外的上下文路径

    ```bash
    # 进入./demo/order-server目录下
    docker build \
      -f ./order-server/Dockerfile \
      -t order-server:0.0.1-SNAPSHOT \
      .
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

    * *Line.1*: 开启交互模式和tty
    * *Line.2*: 容器停止后自动删除容器
    * *Line.6*: 设置数据库url, 这里的数据库IP为`获取mysql服务的内部ip`步骤中获取到的IP

    ```bash
    docker run -it \
      --rm \
      --name order-server \
      -p 38080:38080 \
      -e spring.r2dbc.url=r2dbc:mysql://172.17.0.3:33060/shopping-mall-mariadb \
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
