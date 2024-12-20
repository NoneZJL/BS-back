# BS 商品比价网站后端启动方式

**本项目推荐使用 IDEA 运行，因为 IDEA 的环境配置比较方便**

## 1 仓库克隆

**首先进入你在克隆前端仓库的时候创建的整体的文件夹，在这个大文件夹下打开终端**

```shell
git clone https://github.com/NoneZJL/BS-back.git
```

## 2 项目运行

**首先进入刚刚克隆的仓库**

```shell
cd BS-back
```

**接着进行 maven 依赖的安装**

1. 如果你使用的是 IDEA，那么可以直接打开 pom.xml 文件，并且点击右上角的回环标志进行 maven 依赖的安装
2. 如果你没有使用 IDEA，那么就使用命令行进行安装

```shell
mvn clean install
```

**修改配置文件并且进行数据库预安装**

* 修改 /src/main/resource/application.yml 中的部分内容

```yaml
datasource:
  driver-class-name: com.mysql.cj.jdbc.Driver
  username: root
  url: jdbc:mysql://localhost:3306/price_compare?allowPublicKeyRetrieval=true&useSSL=false
  password: "password" # 将这里的密码修改为自己的数据库的 root 用户的密码
```

* 打开本地的 MySQL，建立数据库

```sql
CREATE DATABSE IF NOT EXISTS price_compare;
```

**最后就可以运行项目**

1. 如果你使用的是 IDEA，那么可以直接点击右上角的朝向右侧的三角(需要确保此时显示的运行内容是整个后端项目)运行程序
2. 如果你没有使用 IDEA，那么就使用命令行进行启动

```shell
mvn spring-boot:run
```

至此，项目就已经成功启动了

> 注意：如果您在获取商品的过程中有爬虫无法正常使用，请首先下载您的edge浏览器版本对应的msedgedriver，并且将该文件放置在您的edge浏览器的可执行文件的同级目录下，然后再运行代码，此时代码中的爬虫应当就能够正常运行了。