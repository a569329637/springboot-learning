1. 下载flume
```bash
wget http://mirrors.tuna.tsinghua.edu.cn/apache/flume/1.8.0/apache-flume-1.8.0-bin.tar.gz
tar -zxvf apache-flume-1.8.0-bin.tar.gz
```

2. 复制配置和jar包
```bash
cp springboot-flume-1.0-SNAPSHOT.jar /root/apache-flume-1.8.0-bin/lib/
cp flume-config.properties /root/apache-flume-1.8.0-bin/conf/
```

3. 启动
```bash
bin/flume-ng agent --conf conf --conf-file conf/flume-config.properties --name agent -Dflume.root.logger=INFO,LOGFILE,console

nohup /root/apache-flume-1.8.0-bin/bin/flume-ng agent --conf /root/apache-flume-1.8.0-bin/conf --conf-file /root/apache-flume-1.8.0-bin/conf/flume-config.properties --name agent -Dflume.root.logger=INFO,LOGFILE >/dev/null 2>&1 &
```

4. 生产测试文件
```
vim logs/data.txt

dklfjsdklfjsdkf
Hello
world
sdjkflsdjkfjs
```

5. 测试
```bash
touch logs/data.txt
bin/flume-ng avro-client -c conf -H 127.0.0.1 -p 4141 -F logs/data.txt
```
