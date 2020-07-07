# 清除环境

```bash
# 进入./demo/shopping-mall目录操作
docker-compose rm -f
# 删除自定义网络
docker network rm shopping-mall_shopping-mall
# 删除在运行的数据库和order-server容器, -f参数会强制删除正在运行的容器
docker rm -f shopping-mall-mariadb
docker rm -f order-server
# 清理所有悬挂（<none>）镜像
docker image prune
```
