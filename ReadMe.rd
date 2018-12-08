引入依赖：
<dependency>
    <groupId>leo.dbproxy</groupId>
    <artifactId>dbproxy-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>


数据源配置：
1.Mysql 需要配置一主多从结构，从Master → Slaves Replication 为单向

2.以下配置显示指明slave 为read-only

hikari:
  master:
    jdbc-url: jdbc:mysql://master.host:3306/crush?useUnicode=true&characterEncoding=utf8&useSSL=true&allowMultiQueries=true&verifyServerCertificate=false
    username: root
    password: root
    maximum-pool-size: 20
    pool-name: master
    connection-timeout: 30000
    idle-timeout: 600000
    max-lifetime: 1765000
  slaves:
    - jdbc-url: jdbc:mysql://slave1.host:3306/crush?useUnicode=true&characterEncoding=utf8&useSSL=true&allowMultiQueries=true&verifyServerCertificate=false
      username: root
      password: root
      maximum-pool-size: 80
      pool-name: slave1
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1765000
      read-only: true
    - jdbc-url: jdbc:mysql://slave2.host:3306/crush?useUnicode=true&characterEncoding=utf8&useSSL=true&allowMultiQueries=true&verifyServerCertificate=false
      username: root
      password: root
      maximum-pool-size: 80
      pool-name: slave2
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1765000
      read-only: true


程序入口：
1. exclude 默认数据源装配

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class);
    }
}


服务层代码：
/**
  *不显示指明数据访问方式，默认会根据调用上下文决定
  * 如果上下文没有明确指定@Master/Slave，会根据当前事务状态决定主从访问
  */
public void doSomething() {
    //do some write and query
}
 
 
//指明访问从数据库，有多个从数据库时，统一线程操作序列，会保证访问同一从库
@Slave
public List<ProxyDbTest> findAll() {
    return proxyRepository.findAll();
}
 
//指明访问主数据库
@Master
public void saveSomething() {
 
    //do some write and query
}
