## RocketMQ

### 启动
> * 启动 Name Server 服务：nohup sh mqnamesrv > mqnamesrv.log 2>&1 &
> * 启动 Broker 服务：nohup sh mqbroker -n localhost:9876 > mqbroker.log 2>&1 &
> * 启动消费者：sh tools.sh org.apache.rocketmq.example.quickstart.Consumer
> * 启动生产者：sh tools.sh org.apache.rocketmq.example.quickstart.Producer
> * 关闭 Broker 服务：sh mqshutdown broker
> * 关闭 Name Server 服务：sh mqshutdown namesrv

### Topic 主题
> * 生产者发送消息和消费者在拉取消息的第一类别

### Tag 标签
> * 生产者发送消息和消费者在拉取消息的第二类别
 
### message keys 
> * 每个消息在业务层面的唯一标识码，幂等的标识

### GroupName 组名
> * 需要由应用保证唯一
> * 在生产者中，发送普通消息没有作用，但发送事务消息时，服务器会会查Group下的任一Producer
> * 在消费者中，实现消息消费的负载均衡和消息容错

### rocketmq 怎么保证队列完全顺序消费
> * https://www.zhihu.com/question/30195969
> * 将同一批消息顺序投递到同一个队列里，消费者从同一队列里获取消息时FIFO

### Push 和 Pull 模式
> * Push 模式，consumer把轮询过程封装了,并注册MessageListener监听器,取到消息后,唤醒MessageListener的consumeMessage()来消费,对用户而言,感觉消息是被推送过来的
> * Pull 模式，取消息的过程需要用户自己写,首先通过打算消费的Topic拿到MessageQueue的集合,遍历MessageQueue集合,然后针对每个MessageQueue批量取消息,一次取完后,记录该队列下一次要取的开始offset,直到取完了,再换另一个MessageQueue

### Send Result 定义
> * SEND_OK：消息发送成功
> * FLUSH_DISK_TIMEOUT：消息发送成功，但是服务器刷盘超时，消息已经进入服务器队列，只有此时服务器宕机，消息才会丢失
> * FLUSH_SLAVE_TIMEOUT：消息发送成功，但是服务器同步到Slave时超时，消息已经进入服务器队列，只有此时服务器宕机，消息才会丢失
> * SLAVE_NOT_AVAILABLE：消息发送成功，但是此时slave不可用，消息已经进入服务器队列，只有此时服务器宕机，消息才会丢失

### 最佳实践

#### producer
> * Topic：一个应用尽可能用一个Topic，消息子类型用tags来标识，tags可以由应用自由设置。只有发送消息设置了tags，消费方在订阅消息时，才可以利用tags 在broker做消息过滤
> * Keys：每个消息在业务层面的唯一标识码，要设置到 keys 字段，方便将来定位消息丢失问题。服务器会为每个消息创建索引(哈希索引)，应用可以通过 topic，key来查询这条消息内容，以及消息被谁消费。由于是哈希索引，请务必保证key 尽可能唯一，这样可以避免潜在的哈希冲突。
> * 日志：消息发送成功或者失败，要打印消息日志，务必要打印 send result 和key 字段
> * send：send消息方法，只要不抛异常，就代表发送成功。但是发送成功会有多个状态，「Send Result 定义」

#### consumer
> * 幂等：RocketMQ使用的消息原语是At Least Once，所以consumer可能多次收到同一个消息，此时务必做好幂等
> * 日志：消费时记录日志，以便后续定位问题
> * 批量消费：尽量使用批量方式消费方式，可以很大程度上提高消费吞吐量
    
