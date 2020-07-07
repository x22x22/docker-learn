# 再次进阶

## 前言

可以看出[进阶版](./adv.md)中会有以下缺点

1. 官方将废弃`--link`操作方式
1. 产生混乱, 使用`--link`操作方式如果服务多了, 并且如果要双向调用的话, 到处都会充斥着`--link`

所以我们可以对以上问题进行改进.

## 清除环境

如果做过场景1的实验, 请清除场景1中运行的网络和容器, 具体参考[场景1-清除环境](./clean.md)

## 构建

[进阶版](./adv.md#构建)

## 网络

1. 创建自定义桥接网络

    ```bash
    # 创建一个名为shopping-mall的桥接容器网络
    docker network create shopping-mall
    # 查看刚创建的容器网络
    docker network inspect shopping-mall
    ```

## 启动

1. 启动mysql服务, 这里用的是`mariadb:10.5.4`

    * *Line.7*: # 加入`shopping-mall`网络
    * *Line.8*: # 设置别名

    ```bash
    docker run --name shopping-mall-mariadb \
      -p 33060:3306 \
      -e MYSQL_ROOT_PASSWORD=x22x22 \
      -e MYSQL_USER=shopping-mall-mariadb \
      -e MYSQL_PASSWORD=shopping-mall-mariadb \
      -e MYSQL_DATABASE=shopping-mall-mariadb \
      --net shopping-mall \
      --network-alias shopping-mall-mariadb \
      -d \
      mariadb:10.5.4
    ```

1. 确认加入的网络

   ```bash
   docker inspect shopping-mall-mariadb | grep NetworkMode
   # 回显关键内容
   # "NetworkMode": "shopping-mall"
   ```

1. 启动order-server

    * *Line.6*: 设置数据库url, 这里使用的是link中对于MYSQL容器设置的别名

    ```bash
    docker run -it \
      --rm \
      --name order-server \
      -p 38080:38080 \
      -e spring.r2dbc.url=r2dbc:mysql://shopping-mall-mariadb:3306/shopping-mall-mariadb \
      --net shopping-mall \
      order-server:0.0.1-SNAPSHOT
    ```

## 验证

[进阶版](./adv.md#验证)
