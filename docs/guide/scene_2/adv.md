# 进阶版

## 前言

可以看出[基础版](./base.md)中会有以下缺点

1. 组件多的情况下, 命令会变的比较多;

所以我们可以对以上问题进行改进.

## 清除环境

如果做过场景1的实验, 请清除场景1中运行的网络和容器, 具体参考[场景1-清除环境](./clean.md)

## 编写docker-compose

## 启动

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
