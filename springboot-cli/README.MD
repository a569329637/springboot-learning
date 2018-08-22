## Spring Boot CLI and Groovy demo

### 安装 Spring Boot CLI
```shell
brew tap pivotal/tap 
brew install springboot
```

### 验证安装 Spring Boot CLI
```
spring --version
```

### 创建数据库表
```
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `age` int(10) NOT NULL COMMENT '年龄',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `birthday` varchar(300) DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';
```

### 直接运行
```
cd springboot-cli
spring run .
```

### 通过 jar 包运行
```
spring jar my-app.jar .
java -jar my-app.jar
```
