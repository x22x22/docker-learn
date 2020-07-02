# docker技术实战交流

## 环境准备

### 场景1至场景3

1. 无论是macOS还是windows 10(非家庭版), 可以使用[docker desktop](https://www.docker.com/products/docker-desktop)
1. windows的同学如果是windows 10家庭版, 或者windows 7的, 可能无法安装`docker desktop`.
1. Windows 10家庭版的同学可以向办法升级到专业版, windows 7的同学可以使用[docker toolbox](https://github.com/docker/toolbox/releases)
1. 如果是Windows的同学, 请安装[git for windows](https://git-scm.com/download/win), 并使用`git bash`来操作实验环节的命令
1. 如果使用`git bash`并且同时安装了[docker toolbox](https://github.com/docker/toolbox/releases), 需要在执行`docker`各种命令之前执行`docker-machine env`回显的命令
1. JDK: JDK 14
1. 依赖构建工具: gradle 6.5

### 场景4

1. 完成场景4需要在自己电脑上安装虚拟机, 虚拟机可以选择以下任意一款
    * Windows: hyper-v, virtualbox, vmware workstations
    * MacOS: virtualbox, Paralles Desktop, VMware Fusion
1. 需要在虚拟机上安装centos 7系统, 并使用yum安装好`docker-ce`, 安装命令为:

    ```bash
    yum install -y yum-utils device-mapper-persistent-data lvm2
    wget -O /etc/yum.repos.d/docker-ce.repo https://download.docker.com/linux/centos/docker-ce.repo
    sed -i 's+download.docker.com+mirrors.tuna.tsinghua.edu.cn/docker-ce+' /etc/yum.repos.d/docker-ce.repo
    yum makecache fast

    yum install docker-ce -y

    ```

## 实践环节

* [场景1-自写应用连mysql数据库](scene_1/README.md)
