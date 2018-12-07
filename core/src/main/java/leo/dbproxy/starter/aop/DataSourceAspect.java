package leo.dbproxy.starter.aop;

import leo.dbproxy.starter.ProxyLookupKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DataSourceAspect {


    @Pointcut("@annotation(leo.dbproxy.starter.annotation.Master) ")
    public void dbProxyMaster() {
        log.debug("dbProxyMaster");
    }

    @Pointcut("@annotation(leo.dbproxy.starter.annotation.Slave) ")
    public void dbProxySlave() {
        log.debug("dbProxySlave");
    }

    @Before("dbProxyMaster()")
    public void doBeforeMaster() {
        ProxyLookupKey newKey = ProxyLookupKey.builder().isForceMaster(true).build();
        ProxyAccessContext.push(newKey);
    }

    @Before("dbProxySlave()")
    public void doBeforeSlave() {
        ProxyLookupKey prevKey = ProxyAccessContext.peek();
        ProxyLookupKey newKey = null;
        if (null != prevKey && prevKey.isForceMaster()) {
            newKey = ProxyLookupKey.builder().isForceMaster(true).build();
        } else {
            newKey = ProxyLookupKey.builder().isForceMaster(false).build();
        }
        ProxyAccessContext.push(newKey);

    }

    @After("dbProxySlave()")
    public void doAfter() {
        ProxyAccessContext.pop();
    }

}
