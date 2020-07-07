# 进阶版

## 前言

可以看出[基础版](./base.md)中会有以下缺点

1. 组件多的情况下, 命令会变的比较多;

所以我们可以对以上问题进行改进.

## 清除环境

如果做过场景1的实验, 请清除场景1中运行的网络和容器, 具体参考[场景1-清除环境](./clean.md)

## 编写docker-compose

```yaml
# 写入到./demo/shopping-mall/docker-compose.yaml下
version: "3.8"
services:
  shopping-mall-mariadb:
    image: mariadb:10.5.4
    container_name: shopping-mall-mariadb
    hostname: shopping-mall-mariadb
    networks:
      shopping-mall:
        aliases:
          - shopping-mall-mariadb
    ports:
      - 33060:3306
    restart: on-failure
    environment:
      - MYSQL_ROOT_PASSWORD=x22x22
      - MYSQL_USER=shopping-mall-mariadb
      - MYSQL_PASSWORD=shopping-mall-mariadb
      - MYSQL_DATABASE=shopping-mall-mariadb
  shopping-mall-redis:
    image: redis:6.0.5-buster
    container_name: shopping-mall-redis
    hostname: shopping-mall-redis
    networks:
      shopping-mall:
        aliases:
          - shopping-mall-redis
    ports:
      - 36379:6379
    restart: on-failure
  order-server:
    image: order-server:0.0.1-SNAPSHOT
    build:
      context: .
      dockerfile: ./order-server/Dockerfile
    container_name: order-server
    hostname: order-server
    networks:
      shopping-mall:
        aliases:
          - order-server
    ports:
      - 38080:38080
    restart: on-failure
    environment:
      spring.r2dbc.url: r2dbc:mysql://shopping-mall-mariadb:3306/shopping-mall-mariadb
    healthcheck:
      test: ["CMD", "curl", "-f", "http://order-server:38080/order"]
      interval: 15s
      timeout: 10s
      retries: 3
      start_period: 60s
    depends_on:
      - shopping-mall-mariadb
  shopping-cart-server:
    image: shopping-cart-server:0.0.1-SNAPSHOT
    build:
      context: .
      dockerfile: ./shopping-cart-server/Dockerfile
    container_name: shopping-cart-server
    hostname: shopping-cart-server
    networks:
      shopping-mall:
        aliases:
          - shopping-cart-server
    ports:
      - 38081:38081
    restart: on-failure
    environment:
      spring.redis.host: shopping-mall-redis
      spring.redis.port: 6379
    healthcheck:
      test: ["CMD", "curl", "-f", "http://shopping-cart-server:38081/goods"]
      interval: 15s
      timeout: 10s
      retries: 3
      start_period: 60s
    depends_on:
      - shopping-mall-redis
  shopping-mall-nginx:
    image: nginx:1.19.0
    container_name: shopping-mall-nginx
    hostname: shopping-mall-nginx
    networks:
      shopping-mall:
        aliases:
          - shopping-mall-nginx
    ports:
      - 20200:20200
    restart: on-failure
    volumes:
      - type: bind
        source: ./nginx/conf.d
        target: /etc/nginx/conf.d

networks:
  shopping-mall:
```

## 启动

```bash
docker-compose up
```

## 验证

参考[场景2-基础版的验证环节](./base.md#验证)
