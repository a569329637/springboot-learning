### 测试流程
> * 通过 scheme.sql 创建数据库
> * 访问`localhost:8080/xa/test?a=1&b=1`，返回`success`
> * 访问`localhost:8080/xa/test?a=2&b=1`，返回`failed`
> * 第二次访问的时候已经打印了`ret1 = 1`，说明数据库1已经提交成功了，由于数据库2提交异常，数据库1也回滚了