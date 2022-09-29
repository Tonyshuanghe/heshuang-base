## 微服务项目  
[参考YC FrameWork](https://framework.youcongtech.com/#/)
### 技术选型
- JDK版本：1.8；
- 项目依赖管理：Maven；
- 版本控制工具：Git；
- 分布式框架:SpringCloud+SpringCloud Alibaba；
- 微服务基础框架：SpringBoot；
- 接口鉴权框架：Sa-Token；
- 缓存：Redis；
- 关系型数据库：MySQL；
- 接口文档自动生成框架：Knife4j；
- 持久层框架：MyBatis-Plus；
- 第三方工具类框架：Hutool；
- 数据库连接池：Druid；
- 限流熔断：Sentinel；
- 日志框架：Lombok；
- Excel处理:Apache Poi；
- 分布式定时任务：Xxl-Job；
- 服务链路追踪：SpringCloud Sleuth+Zipkin；
- 消息队列：RabbitMQ；
- 服务注册与配置中心:Nacos；
- 分布式事务:Seata；
- 分库分表：mycat；
### 微服务项目描述
- heshuang-api 所有fegin的service存放位置  
- heshuang-auth 权限模块（TODO:登录用户信息缓存）  
- heshuang-redis-support redis支持包括（spring-redis-cache spring-redis-listener satoken缓存）   
- heshuang-cloud-knife4f 分布式接口文档 
- heshuang-cloud-security 分布式权限支持
- heshuang-common-core 核心包包括各种utils和entity
- heshuang-common-hadoop 大数据支持包
- heshuang-common-knife4f 单节点接口文档支持
- heshuang-common-mp mybatis-plus支持
- heshuang-dict-support 数据字典支持核心注解为DictCode
- heshuang-log-support 支持spel表达式
- heshuang-gateway 网关模块  
- heshuang-admin 后台管理模块（示例项目 可依据此项目进行扩展）
### 使用中间件(项目根目录/doc/run中包含所有中间件)
minio，naocs，rabbitmq，redis，seata，xxl-job，zipkin
### 待优化 
- 自动化部署方式  
- word (可选技术 [poi-tl](https://github.com/Sayi/poi-tl)) pdf (可选技术 [x-easypdf](https://gitee.com/dromara/x-easypdf>) excel([easypoi](https://gitee.com/lemur/easypoi)) ) 导入导出模块  
- 优化数据字典模块  heshuang-dict-support  
- openfegin第一次较慢解决    
- 各个模块的enable配置管理  
- 支付模块  （可选技术 [JPay](https://github.com/Javen205/JPay))  
- 日志收集 (可选技术 [ERK 安装](https://github.com/deviantony/docker-elk) [ERK 集成java](https://gitee.com/developers-youcong/yc-framework/tree/main/yc-common/yc-common-logstash))
- 微服务监控 (可选技术 spring-boot-admin或者prometheus+grafana)
### 扩展
- 大数据方向  (可选技术 [Hadoop 安装](https://youcongtech.com/2021/12/31/Hadoop%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA/))
- 爬虫方向   (可选技术 [webmagic](https://github.com/code4craft/webmagic/))

