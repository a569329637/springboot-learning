### java

#### 表结构

```
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `age` int(10) NOT NULL COMMENT '年龄',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `birthday` varchar(300) DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户';
```

#### 插入数据

```
INSERT INTO user(`age`, `name`, `birthday`) VALUES (11, '用户1','2018-08-20');
```