## lizi-Market微服务项目开发

#### BC2模式 + 前后端分离

```java
//后台主要负责对商品的crud，以及管理分类等操作
//前台主要面对用户对商品的检索，购买，下单，物流，评论，退货等功能
```

#### 项目架构图

```java
//说明: 还是采用单个数据库存储数据去掉了Seata【16Gd电脑也跑不了几个微服务】
//微服务调用的链路过长服务响应时间慢，新增了rabbtimq来进行异步处理
```

![image-20220527222311147](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220527222311147.png)

#### 后端技术

1. SpringBoot + MyBatisPlus +  SpringCloud 【Cloud Alibaba】+ Redis + MySQL + JWT + Maven + Nginx + RabbitMq

   ```java
   //使用springBoot + MyBatisPlus快速开发应用
   //使用nacos用作服务注册与发现中心
   //使用openfeign实现RPC远程服务调用
   //使用sentinel实现服务的降级，限流
   //使用springcloud gateway当作网关实现动态路由
   //使用redis用来缓存前台首页热点数据和设置登录验证码的过期时间以及购物车数据
   //使用mysql持久化存储数据
   //使用JWT解决SSO单点登录问题
   //使用Nginx反向代理
   //使用RabbitMq异步处理
   ```

#### 前端技术

1. vue + axios + element-ui

   ```javascript
   //使用vue封装的axios来快速开发前后交换接口
   //使用饿了吗ui框架添加组件
   ```

#### 

### 数据库表设计

```java
//第一次设计表不是很合理, 导致开发使用了大量的vo
```

![image-20220527221424220](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220527221424220.png)



![image-20220527221434160](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220527221434160.png)



![image-20220527221454023](C:\Users\FQH\AppData\Roaming\Typora\typora-user-images\image-20220527221454023.png)
